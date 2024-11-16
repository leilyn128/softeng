import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.firebaseauth.model.UserModel
import com.example.firebaseauth.viewmodel.AuthState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    val authStatus: MutableLiveData<AuthState.AuthResult> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        val user = auth.currentUser
        if (user != null) {
            _authState.value = AuthState.Authenticated(user)
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _authState.value = if (user != null) {
                        AuthState.Authenticated(user)
                    } else {
                        AuthState.Unauthenticated
                    }
                } else {
                    _authState.value = AuthState.Error("Authentication failed: ${task.exception?.message}")
                }
            }
    }

    fun validateAccount(email: String, password: String, username: String, confirmPassword: String): Boolean {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.value = "Invalid email format."
            return false
        }
        if (password.length < 6) {
            errorMessage.value = "Password must be at least 6 characters."
            return false
        }
        if (password != confirmPassword) {
            errorMessage.value = "Passwords do not match."
            return false
        }
        if (username.isBlank()) {
            errorMessage.value = "Username cannot be empty."
            return false
        }
        return true
    }

    // Register a new account (sign-up)
    fun registerAccount(
        email: String,
        password: String,
        username: String,
        accountType: String,
        employeeID: String,
        address: String,
        contactNumber: String,
        dateOfBirth: String, // Passed as String
        confirmPassword: String,
        navController: NavController
    ) {
        if (!validateAccount(email, password, username, confirmPassword)) {
            authStatus.value = AuthState.AuthResult.Failure("Validation failed")
            return
        }

        isLoading.value = true

        // Convert dateOfBirth String to Timestamp
        val dateOfBirthTimestamp = convertToTimestamp(dateOfBirth)

        // Create user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        // Account created successfully, save additional user data to Firestore
                        val userModel = UserModel(
                            employeeID = employeeID,
                            username = username,
                            accountType = accountType,
                            address = address,
                            contactNumber = contactNumber,
                            dateOfBirth = dateOfBirthTimestamp // Use Timestamp here
                        )
                        saveUserToFirestore(userModel)
                    }
                } else {
                    errorMessage.value = "Registration failed: ${task.exception?.message}"
                    authStatus.value = AuthState.AuthResult.Failure(task.exception?.message)
                }
            }
    }

    private fun saveUserToFirestore(user: UserModel) {
        val currentUser: FirebaseUser? = auth.currentUser  // Get the current authenticated user from FirebaseAuth
        currentUser?.let {
            val userRef = db.collection("users").document(it.uid)
            userRef.set(user)
                .addOnSuccessListener {
                    // Pass the FirebaseUser to AuthResult.Success
                    authStatus.value = AuthState.AuthResult.Success(currentUser)


                }

                .addOnFailureListener { exception ->
                    errorMessage.value = "Error saving data: ${exception.message}"
                    authStatus.value = AuthState.AuthResult.Failure(exception.message)
                }
        }
    }


    fun signOut() {
        try {
            auth.signOut()
            _authState.value = AuthState.Unauthenticated // Set the state to unauthenticated
            authStatus.value = AuthState.AuthResult.LoggedOut
        } catch (e: Exception) {
            errorMessage.value = "Error logging out: ${e.message}"
        }
    }



    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    private fun convertToTimestamp(dateOfBirth: String): Timestamp {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US) // Use your preferred date format
        val date: Date = format.parse(dateOfBirth)
        return Timestamp(date)
    }
}

