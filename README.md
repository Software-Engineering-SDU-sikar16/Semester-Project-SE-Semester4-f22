# Semester-Project-SE-Semester4-f22

## How to run AsteroidsNetbeansModules
Some path changes are needed before we can run it.

First we have to change the path in the SilentUpdate Bundle.properties file:
Go to AsteroidsNetbeansModules/SilentUpdate/src/main/resources/org/netbeans/modules/autoupdate/silentupdate/resources/
Change the path for org_netbeans_modules_autoupdate_silentupdate_update_center to where the netbeans_site/updates.xml is.


In Core/src/main/java/dk/sdu/mmmi/cbse/core/main/Game.java:
change the map path to where map.tmx is.


With Netbeans: Clean and build TowerDefenseNetbeansModules-app and then run it.

## How to run protect-the-dog
Just run DesktopLauncher