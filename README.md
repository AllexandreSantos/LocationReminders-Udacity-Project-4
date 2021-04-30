# LocationReminders-Udacity-Project-4
Completing this project is a requirement to graduate from the Udacity Android Kotlin Developer.
In this project, you will find a TODO list app with location reminders that remind the user to do something when the user
is at a specific location. The app will require the user to create an account and login to set and access reminders. This project uses a rich architecture and Google Maps API

## Getting Started
* Clone or download the project
* To enable Firebase Authentication: 
    Go to the authentication tab at the Firebase console and enable Email/Password and Google Sign-in methods. 
    Download google-services.json and add it to the app.
* To enable Google Maps:
    Go to APIs & Services at the Google console.
    Select your project and go to APIs & Credentials.
    Create a new api key and restrict it for android apps.
    Add your package name and SHA-1 signing-certificate fingerprint.
    Enable Maps SDK for Android from API restrictions and Save.
    Copy the api key and create a string with the following name and atributes, the value oh the string should be your API key:
    name="google_maps_key" templateMergeStrategy="preserve" translatable="false"
* If you have any doubts on how to run this project you can contact me ;)
      
## Built With
This project is built with Android Studio and Kotlin making a good use of:
* MVVM
* Unit Tests
* Integration Tests
* Live Data
* Room
* Data Binding
* Google Maps
* GPS hardware integration
* Navigation
* Repository Pattern

## Screenshots
![image](https://raw.githubusercontent.com/AllexandreSantos/LocationReminders-Udacity-Project-4/main-api-removed/screenshots/screenshot1.png)
![image](https://raw.githubusercontent.com/AllexandreSantos/LocationReminders-Udacity-Project-4/main-api-removed/screenshots/screenshot2.png)
![image](https://raw.githubusercontent.com/AllexandreSantos/LocationReminders-Udacity-Project-4/main-api-removed/screenshots/screenshot3.png)


