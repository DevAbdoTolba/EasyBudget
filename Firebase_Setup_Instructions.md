# Firebase Setup Instructions

To use your own Firebase authentication for login in this app, follow these steps:

## Step 1: Create or Access Your Firebase Project
1. Go to the [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or select your existing project

## Step 2: Set Up Android App
1. In your Firebase project, click the Android icon (</>) to add an Android app
2. Enter package name: `com.benoitletondor.easybudgetapp`
3. Nickname: `EasyBudget` (or any name you prefer)
4. Register the app

## Step 3: Download Configuration File
1. Download the `google-services.json` file
2. Replace the existing file at `Android/EasyBudget/app/google-services.json` with your downloaded file

## Step 4: Set Up Firebase Authentication
1. In the Firebase Console, go to "Authentication"
2. Click "Get started"
3. Enable "Google" as a sign-in method
   - Click on Google
   - Enable it (toggle the switch)
   - Select a project support email
   - Save

## Step 5: Add SHA-1 Certificate Fingerprint
For Google Sign-In to work, you need to add your app's SHA-1 fingerprint:
1. Generate your SHA-1 using this command:
   ```
   keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
   ```
2. In Firebase Console, go to Project settings > Your Android app > Add fingerprint
3. Add the SHA-1 value from the keytool command

## Step 6: Rebuild and Run the App
After making these changes, rebuild and run the app. You should now be using your Firebase authentication for login.
