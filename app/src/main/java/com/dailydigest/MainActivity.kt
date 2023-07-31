package com.dailydigest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dailydigest.databinding.ActivityMainBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    // var declaration
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

/*    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
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
            displayName = "Jane Q. User"
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    Toast.makeText(this, "User profile updated successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "User profile update failed.", task.exception)
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
                        Log.d(TAG, "User email address updated.")
                        Toast.makeText(this, "User email address updated successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w(TAG, "User email address update failed.", task.exception)
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
                        Log.d(TAG, "User password updated.")
                        Toast.makeText(this, "User password updated successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w(TAG, "User password update failed.", task.exception)
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
        Toast.makeText(this, "User signed out successfully.", Toast.LENGTH_SHORT).show()
        installSplashScreen()
        startActivity(Intent(this, LoginSignUpActivity::class.java))
    }

    private fun deleteUser() {
        val user = Firebase.auth.currentUser
        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                    Toast.makeText(this, "User account deleted successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "User account not deleted.", task.exception)
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
                    Log.d(TAG, "User re-authenticated.")
                    Toast.makeText(this, "User re-authenticated successfully.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(TAG, "User re-authentication failed.", task.exception)
                    Toast.makeText(this,"Something went wrong, please try again later.", Toast.LENGTH_SHORT).show()
                }
            }
    }*/
}