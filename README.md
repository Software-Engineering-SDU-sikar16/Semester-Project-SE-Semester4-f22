# Semester-Project-SE-Semester4-f22

## Tiled for creating maps
https://www.mapeditor.org/


## How to run protect-the-dog
- Settings -> Build, Execution, Deployment -> Gradle
- Change Gradle JVM to 1.8
- Go to Project Structure and change Project SDK to 1.8
- run DesktopLauncher

## How to run with OSGi in IntelliJ
- 
- In file -> project structure, set SDK to 1.8
- Add new run configuration from top right.
- Choose maven and give it a name
- Choose the project directory 
- Write `install pax:provision` in the command line parameter
- Now ya gucci to run
