package com.dailydigest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dailydigest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private val tag = "MainActivity"

    // var declaration
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }
    
/*    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // UserData is signed in
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // No user is signed in
            startActivity(Intent(this, LoginSignUpActivity::class.java))
        }
    }

    private fun getUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            val emailVerified = it.isEmailVerified

            val uid = it.uid
        }
    }

    private fun getProviderData() {
        val user = Firebase.auth.currentUser
        user?.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl
            }
        }
    }

    private fun updateProfile() {
        val user = Firebase.auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = "Jane Q. UserData"
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "UserData profile updated.")
                    Toast.makeText(this, "UserData profile updated successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "UserData profile update failed.", task.exception)
                    Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateEmail() {
        val user = Firebase.auth.currentUser
        user!!.let {
            user.updateEmail("user@example.com")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "UserData email address updated.")
                        Toast.makeText(this, "UserData email address updated successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w(TAG, "UserData email address update failed.", task.exception)
                        Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun updatePassword() {
        val user = Firebase.auth.currentUser
        val newPassword = "SOME-SECURE-PASSWORD"
        user!!.let {
            user.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "UserData password updated.")
                        Toast.makeText(this, "UserData password updated successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w(TAG, "UserData password update failed.", task.exception)
                        Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun sendEmailVerification() {
        val user = Firebase.auth.currentUser
        user!!.let {
            user.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                        Toast.makeText(this, "Email sent successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w(TAG, "Email not sent.", task.exception)
                        Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun sendPasswordReset() {
        val auth = Firebase.auth
        val emailAddress = "user@example.com"

        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(this, "Email sent successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "Email not sent.", task.exception)
                    Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signOutUser() {
        Firebase.auth.signOut()
        Toast.makeText(this, "UserData signed out successfully.", Toast.LENGTH_SHORT).show()
        installSplashScreen()
        startActivity(Intent(this, LoginSignUpActivity::class.java))
    }

    private fun deleteUser() {
        val user = Firebase.auth.currentUser
        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "UserData account deleted.")
                    Toast.makeText(this, "UserData account deleted successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "UserData account not deleted.", task.exception)
                    Toast.makeText(this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reauthenticate() {
        val user = Firebase.auth.currentUser
        val credential = EmailAuthProvider
            .getCredential("user@example.com", "password1234")

        user!!.reauthenticate(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "UserData re-authenticated.")
                    Toast.makeText(this, "UserData re-authenticated successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "UserData re-authentication failed.", task.exception)
                    Toast.makeText(this,"Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                }
            }
    }*/
}