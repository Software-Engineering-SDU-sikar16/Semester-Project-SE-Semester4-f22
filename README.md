# Semester-Project-SE-Semester4-f22

## How to run with Netbeans modules
First we have to change the path in a file:
Go to AsteroidsNetbeansModules/SilentUpdate/src/main/resources/org/netbeans/modules/autoupdate/silentupdate/resources/

In Bundle.properties we have to update the path of org_netbeans_modules_autoupdate_silentupdate_update_center to where AsteroidsNetbeansModules/netbeans_site/updates.xml is. 

With Netbeans: Clean and build AsteroidsNetbeansModules-app and then run it.

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