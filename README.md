# Semester-Project-SE-Semester4-f22

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