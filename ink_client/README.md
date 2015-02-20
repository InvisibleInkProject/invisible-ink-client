# InvisibleInk - Android app

### Used software

Used Java libraries:

* [Android Asynchronous Http Client](http://loopj.com/android-async-http/) (v.1.4.4)
    * JavaDoc: http://loopj.com/android-async-http/doc/
    * Source: libs/android-async-http/library/src/main/java/
    
## Notes

### How to run this application in Eclipse

Preparation#

* Open the "Android SDK Manager"
    * Install "Extras > Google Play services" and "Android 4.X (API XX) > Google APIs"
* Open Eclipse
    * Import ("File > Import > Android > Existing Android Code Into Workspace") the
   "google-play-services_lib" as a copy in your workspace. You find the library in
   "MY-ADT-BUNDLE/sdk/extras/google/google_play_services/libproject"

Our application

* Go to the project properties and choose Android form the left menu
    * Select under "Project Build Target" a version (e.g. 4.3) of "Google APIs"
    * Add under "Library" the "google-play-services_lib" from your workspace
* Still in the project properties select "Java Build Path" form the left menu
    * Choose the tab "Order and Export" and make sure, that everthing is selected


### LogCat tip

LogCat filter
```
tag:^(?!.*(dalvikvm|libEGL|GooglePlayServicesUtil|EGL_genymotion|OpenGLRenderer)).*$
tag:^(?!.*(AwContents|chromium|dalvikvm|EGL_genymotion|eglCodecCommon|IInputConnectionWrapper|libEGL|OpenGLRenderer)).*$
```
