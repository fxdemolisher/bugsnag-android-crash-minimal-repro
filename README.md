# Background

With the changes in bugsnag-android library (4.15.0) we discovered a crash when connectivity is fully lost while a request is in flight. This repo is meant to hold a minimal reproduction case of this issue.

# Structure

This application is a simple Android application scaffold with a single activity. When the activity starts it spins up a thread that will issue a request to httpstat.us that is meant to be at least 3 seconds in flight. If the activity is paused this thread will attempt to shut itself down.

# Steps to reproduce the Bugsnag crash

1. Clone this repo
2. In MainApplication.kt, add a valid Bugsnag API key
3. Run the app on a physical device
4. Disable mobile data on device, but keep wifi on for now
5. While the status of the request is "Starting request", turn off Wifi via notification drawer

Expected:
Request fails and after 5 seconds it is attepted again

Actual:
The entire app is closed, resuling in a WIN DEATH notification in logcat but no stack traces or any other clues as to why this is happening.

NOTE: Disabling detectAnrs in the bugsnag configuration in MainApplication prevents the crash. This leads me to believe the issue is with the newer ANR detector code in the Bugsnag library. 

# Misc

Included is a Logcat output for a test run of this app as note8.logcat.txt
