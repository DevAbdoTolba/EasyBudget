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
import androidx.appcompat.app.AppCompatActivity

/**
 * A simple launcher activity that immediately redirects to the Firebase Auth Test activity.
 * This allows us to have a dedicated launcher icon for the auth test.
 */
class FirebaseAuthTestLauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Start the Firebase Auth Test Activity
        val intent = Intent(this, FirebaseAuthTestActivity::class.java)
        startActivity(intent)
        
        // Finish this activity so it doesn't stay in the stack
        finish()
    }
}
