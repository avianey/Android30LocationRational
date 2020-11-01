# Android30LocationRational

Illustrate problem related to location checking when using com.android.tools.build:gradle:4.1.0

* create an emulator running under Android 30,29,28, ...
* install the app on emulator using `installRelease` task
* open the app => locations are granted by default !!!

If app is installed using using the regular run button from Android Studio (|>), app is installed without permissions granted (you need to uninstall app from device first)...

Notes :
 * same problem with real device (Samsung Galaxy S10E android 29)
 * problem is the same with `targetSdkVersion` and `compileSdkVersion` 29 or 30
 * reverting to `classpath "com.android.tools.build:gradle:4.0.2"` solves the issue (both with `targetSdkVersion` and `compileSdkVersion` 29 or 30)
