/*
 *   Copyright 2025
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.benoitletondor.easybudgetapp.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.benoitletondor.easybudgetapp.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * A test activity to verify Firebase Authentication configuration
 * This is for testing purposes only - remove after verifying auth works
 */
class FirebaseAuthTestActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "FirebaseAuthTest"
    }
    
    private lateinit var statusTextView: TextView
    
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            updateUI(user)
            Toast.makeText(this, "Sign in successful!", Toast.LENGTH_SHORT).show()
            
            // Print token for debugging
            user?.getIdToken(false)?.addOnSuccessListener { tokenResult ->
                Log.d(TAG, "Token: ${tokenResult.token}")
            }
        } else {
            // Sign in failed
            updateUI(null)
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_auth_test)
        
        statusTextView = findViewById(R.id.text_auth_status)
        val signInButton = findViewById<Button>(R.id.button_sign_in)
        val signOutButton = findViewById<Button>(R.id.button_sign_out)
        
        signInButton.setOnClickListener { signIn() }
        signOutButton.setOnClickListener { signOut() }
        
        // Check if user is signed in
        val currentUser = FirebaseAuth.getInstance().currentUser
        updateUI(currentUser)
    }
    
    private fun signIn() {
        // Choose authentication providers
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        
        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        
        signInLauncher.launch(signInIntent)
    }
    
    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                updateUI(null)
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            }
    }
    
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            statusTextView.text = getString(R.string.signed_in_fmt, user.displayName, user.email)
            Log.d(TAG, "User ID: ${user.uid}")
            Log.d(TAG, "Email: ${user.email}")
            
            // Print additional information that might be useful
            Log.d(TAG, "Display Name: ${user.displayName}")
            Log.d(TAG, "Phone Number: ${user.phoneNumber}")
            Log.d(TAG, "Photo URL: ${user.photoUrl}")
            Log.d(TAG, "Provider ID: ${user.providerId}")
            
            // Get token for debugging
            user.getIdToken(false).addOnSuccessListener { tokenResult ->
                Log.d(TAG, "Token: ${tokenResult.token}")
            }
        } else {
            statusTextView.text = getString(R.string.signed_out)
        }
    }
}
