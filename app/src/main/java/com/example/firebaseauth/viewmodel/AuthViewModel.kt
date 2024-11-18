import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.firebaseauth.login.UserModel
import com.example.firebaseauth.viewmodel.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    val authStatus: MutableLiveData<AuthState.AuthResult> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isAuthenticated: Boolean
        get() = FirebaseAuth.getInstance().currentUser != null

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

    // Updated login function
    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading // Indicate loading state

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        _authState.value = AuthState.Authenticated(user) // Update to authenticated
                    } else {
                        _authState.value = AuthState.Unauthenticated // Handle null user case
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
        confirmPassword: String,
        navController: NavController
    ) {
        if (!validateAccount(email, password, username, confirmPassword)) {
            authStatus.value = AuthState.AuthResult.Failure("Validation failed")
            return
        }

        isLoading.value = true

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
                            contactNumber = contactNumber
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
        val currentUser: FirebaseUser? = auth.currentUser
        currentUser?.let {
            val userRef = db.collection("users").document(it.uid)
            userRef.set(user)
                .addOnSuccessListener {
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
            _authState.value = AuthState.Unauthenticated
            authStatus.value = AuthState.AuthResult.LoggedOut
        } catch (e: Exception) {
            errorMessage.value = "Error logging out: ${e.message}"
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
