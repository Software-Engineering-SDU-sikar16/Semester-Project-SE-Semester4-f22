# Semester-Project-SE-Semester4-f22

## How to run TowerDefenseNetbeansModules
Some path changes are needed before we can run it.

First we have to change the path in the SilentUpdate Bundle.properties file:
Go to TowerDefenseNetbeansModules/SilentUpdate/src/main/resources/org/netbeans/modules/autoupdate/silentupdate/resources/
Change the path for org_netbeans_modules_autoupdate_silentupdate_update_center to where the netbeans_site/updates.xml is.


In Core/src/main/java/dk/sdu/mmmi/cbse/core/main/Game.java:
change the map path to where map.tmx is.


With Netbeans: Clean and build TowerDefenseNetbeansModules-app and then run it.

## How to run protect-the-dog
Just run DesktopLauncher

## How to run with OSGi in IntelliJ
- Add new run configuration from top right.
- Choose maven and give it a name
- Choose the working directory 
- Write `install pas:provision` in the command line parameter
- Now ya gucci

### OSGi commands
Creating a project
```
mvn org.ops4j:maven-pax-plugin:create-project -DgroupId=td -DartifactId=TowerDefenseOsgi -Dversion=1.0-SNAPSHOT
```

Creating a module
```
mvn pax:create-bundle -Dpackage=Core -Dname=core-bundle -Dversion=1.0-SNAPSHOT
```

```
mvn pax:wrap-jar -DgroupId=td -DartifactId=TowerDefenseOsgi -Dversion=1.0-SNAPSHOT
```