# LocationReminders-Udacity-Project-4
Completing this project is a requirement to graduate from the Udacity Android Kotlin Developer.
In this project, you will find a TODO list app with location reminders that remind the user to do something when the user
is at a specific location. The app will require the user to create an account and login to set and access reminders.

## Getting Started
* Clone or download the project
* To enable Firebase Authentication: 
    Go to the authentication tab at the Firebase console and enable Email/Password and Google Sign-in methods. 
    Download google-services.json and add it to the app.
*To enable Google Maps:
    Go to APIs & Services at the Google console.
    Select your project and go to APIs & Credentials.
    Create a new api key and restrict it for android apps.
    Add your package name and SHA-1 signing-certificate fingerprint.
    Enable Maps SDK for Android from API restrictions and Save.
    Copy the api key and create a string with the following name and atributes, the value oh the string should be your API key:
    name="google_maps_key" templateMergeStrategy="preserve" translatable="false"
      
## Built With
This project is built with Android Studio and Kotlin making a good use of:
* MVVM
* Live Data
* Room
* Data Binding
* Google Maps
* GPS hardware integration
* Navigation
* Repository Pattern

