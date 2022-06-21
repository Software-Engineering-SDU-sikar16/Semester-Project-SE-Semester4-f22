## How to run with OSGi in IntelliJ

- In file -> project structure, set SDK to 1.8
- Add new run configuration from top right
- Choose maven and give it a name
- Choose the project directory
- Write `install pax:provision` in the command line parameter
- Now ya gucci to run

## How to add a new a component:

- Copy pasta an existing component (enemy or map)
- Rename folders and filer
- Rename `pom.xml`
- Add the module name to projects `pom.xml`
- Fix Meta-inf.xml files if needed
- Add service-components to `core/src/main/resources/meta-inf/core.xml`

