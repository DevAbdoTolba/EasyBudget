# Setting Up Your Firebase Authentication

## 1. Create Your Firebase Project

1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or select your existing project
3. Follow the setup wizard to create your project

## 2. Configure Android App in Firebase

1. In your Firebase project dashboard, click the Android icon (</>) to add an Android app
2. Use package name: `com.benoitletondor.easybudgetapp`
3. App nickname: "EasyBudget" (optional)
4. Debug signing certificate SHA-1: Generate using the following command:
   ```
   keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
   ```
   Copy the SHA-1 fingerprint and paste it into Firebase console
5. Click "Register app"
6. Download the `google-services.json` file
7. Replace the existing file at `Android/EasyBudget/app/google-services.json` with your downloaded file

## 3. Enable Google Authentication

1. In your Firebase project, go to the Authentication section
2. Click "Get started" if not already set up
3. Select the "Sign-in method" tab
4. Click on Google in the list of providers
5. Enable Google authentication by toggling the switch
6. Configure your OAuth consent screen if needed
7. Save your changes

## 4. Testing Your Authentication

You can use the included Firebase Auth Test activity to verify that authentication is working correctly:

1. Build and run the app on your device or emulator
2. Long-press the app icon on your device to reveal app shortcuts
3. Select "Auth Test" from the shortcuts
4. OR navigate to Menu > Firebase Auth Test (if available)
5. Click "Sign In" and follow the Google sign-in flow

If authentication is successful, you'll see your Google account information displayed.

## 5. Debugging Tips

If authentication is not working:

1. Check Logcat for error messages (filter for tag "FirebaseAuthTest")
2. Verify that your SHA-1 fingerprint in Firebase project settings matches your development environment
3. Make sure you've enabled the Google sign-in provider in Firebase Authentication
4. Check internet connectivity
5. Ensure the `google-services.json` file is correctly placed in the app directory
6. Rebuild the project after making any changes

## 6. Implementing in Your Own Firebase Project

Once you've successfully tested authentication with the test activity, the app will use your Firebase authentication for all login functionality. The implementation is handled by the `FirebaseAuth` class in the app.

## 7. Security Rules

Don't forget to configure your Firestore security rules to protect your data. Basic rules might look like:

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## 8. Production Considerations

Before releasing to production:
1. Add your app's release SHA-1 certificate to Firebase project settings
2. Configure proper security rules for your database
3. Consider adding additional authentication methods if needed
