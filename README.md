# [CDS SPA](https://github.com/FEISystems/cds)

## Overall Directory Structure

A high level view of our project structure:

```
CDS/

  |- karma/
  |- |- <karma configuration files>
  |- protractor/
  |- |- < protractor configuration files>
  |- src/main/java/
  |- |  |    |     |- < java files>
  |- src/main/resource/
  |  |   |   |        | META-INF/
  |  |   |   |        |   | spring/
  |  |   |   |        |   |- <spring configuration>
  |  |   |   |        | sqlscripts/
  |  |   |   |        |   |- <sql scripts>
  |  |   |   |        |- log4j.properties < log configuration>
  |- src/main/webapp/
  |  |   |   |  - source/
  |  |   |   |      |   |- app/
  |  |   |   |      |   |  |- <app logic>
  |  |   |   |      |   |- assets/
  |  |   |   |      |   |  |- <static files>
  |  |   |   |      |   |- common/
  |  |   |   |      |   |  |- <reusable code>
  |  |   |   |      |   |- less/
  |  |   |   |      |   |  |  - main.less
  |  |   |   |  - vendor/
  |  |   |   |      |   |- angular-bootstrap/
  |  |   |   |      |   |- bootstrap/
  |  |   |   |      |   |- placeholders/
  |  |   |   |  - WEB-INF/
  |  |   |   |      |   - < java class files, properties files etc.>
  |- src/main/test/
  |- |  |   |   |- <java test files>
  |- .bowerrc - < path to store bower libraries>
  |- .jshintrc - <JShint configuration files>
  |- bower.json -< bower configuration file>
  |- build.config.js - < configuration file for the build>
  |- Gruntfile.js - <Grunt file>
  |- settings.gradle - <gradle project configuration files>
  |- gradle.properties - < configuration file for MySql>
  |- gradle-qa.properties - < configuration file for MySql>
  |- package.json - <Nodejs dependencies files>
```

What follows is a brief description of each entry, but most directories contain
their own `README.md` file with additional documentation, so browse around to
learn more.

- `karma/` - karma unit test configuration.
- `protractor/` - protractor e2e test configurations.
- `src/` - our application sources. Java and Angularjs code
- `src/main/java` - Java source code
- `src/main/resources` - Java recources
- `src/main/webapp/source` - Angularjs javascript code. This is the frontend application.
   <b>This folder will be excluded when generating the war </b>
- `src/main/webapp/vendor` - All the bower dependencies will be installed here.
   <b> This folder will be excluded when generating the war. </b>
- `.bowerrc` - the Bower configuration file. This tells Bower to install components
   into the `src/main/webapp/vendor` directory.
- `bower.json` - this is our project configuration for Bower and it contains the
   list of Bower dependencies we need. Anything added as dependencies here will need to be
   manually added to `build.config.js` to be picked up by the build system.
- `build.config.js` - our customizable build settings; see "The Build System"
   below.
- `Gruntfile.js` - our frontend build script; see "The Build System" below.
- `package.json` - metadata about the app, used by NPM and our build script. Our
   NPM dependencies are listed here.

## Quick Start.

Download, install and configure the following software that will be needed to run the
application:

[Gradle](http://www.gradle.org/downloads), [GIT] (http://git-scm.com/downloads),
[MySql](http://dev.mysql.com/downloads/), [Node.js](http://nodejs.org/download/) and
(<b> Update the local database credential in the file src\main\environment\local\database.properties </b>)


Run the following commands:

Install grunt-cli karma and bower

```
npm -g install grunt-cli karma bower

```

Clone the project from Github

```
git clone https://github.com/FEISystems/cds.git

```

Change directory into the CDS folder and install the node and bower dependencies

```
cd cds
npm install
bower install

```

After updating this file <b>src\main\environment\local\database.properties</b> with the right credentials,
 changing the Hibernate configuration to generate the database tables by updating the hibernate properties

```
<property name="hibernate.hbm2ddl.auto" value="validate"/>

```

to

```
<property name="hibernate.hbm2ddl.auto" value="create"/>

```

And run this gradle task to start the application

```
gradle tomcatrunwar

```

or

```
gradle tomcatrun

```

Open a different terminal when the application is alreadiy runnning and populate all the database tables by running the gradle task below. Where the -PenvironmentName can be dev, qa, production. If not specified, the development configuration (dev)
will be used

```
gradle runSql -PenvironmentName=dev

```

To stop the application at any time just enter the following command in the terminal where the application is runing

```
Crtl-C

```

### Debug backend java code.

To debug the application in the backend, user the Gradle tomcat plugin task tomcatRunWar/tomcatRun as below..

```
gradle tomcatRunWar -Dorg.gradle.jvmargs="-XX:MaxPermSize=256M -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9999"

```

Then configure the IDE (Eclipse/STS) for debugging by navigating to


```
Run --> DebugConfiguration --> Remote Java Application --> Configure host and port

```

For the port, specify the value above value 9999 or the port you entered above. With this in place the application will stop at the
break points you set.


### Run the application.

The applciation can be started in three ways:

- <b>Debug mode</b>: run the following command

```
gradle debugTomcatRunWar

```

This will generate the debug project in `src/main/webapp/source/target/debug/`, copy the generated debug contents
into the `src/main/webapp/` folder and generate the war file excluding `src/main/webapp/source/` and `src/main/webapp/vendor/`
folders.

- <b>Dist mode</b>: run the following command:

```
gradle distTomcatRunWar

```

This will generate the dist project in `src/main/webapp/source/target/dist/`, copy the generated dist contents
into the `src/main/webapp/` folder and generate the war file excluding `src/main/webapp/source/` and `src/main/webapp/vendor/`
folders.


- <b>CI mode (Continuos integration)</b>: run the following command

```
gradle CITomcatRunWar

```

This will generate the dist project in `src/main/webapp/source/target/dist/`, copy the generated dist contents
into the `src/main/webapp/` folder and generate the war file excluding `src/main/webapp/source/` and `src/main/webapp/vendor/`
folders. The main different between Dist and CI is that CI run karm in CI mode.


### Hot deployment of the frontend Angularjs code.

To hot deploy the frontend code during development, run the app using tomcatrun as below.

```
gradle tomcatRun -Dorg.gradle.jvmargs="-XX:MaxPermSize=256M -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9999"

```

Then build the frontend using Grunt task below and refresh the browser to get the updated changes.

```
grunt build-debug

```



## Grunt - Gradle tasks

- `webdriverManagerUpdate` - ChromeDriver and Selenium Standalone Server
- `protractorInstall` - Installs Protractor for e2e test
- `protractorEnd2End` - Run integration test with protractor
- `bowerInstall` - Installs packages for frontend web project
- `npmInstall` - Installs all Node.js dependencies defined in package.json
- `jshintAll` - Lint all the JS files
- `grunt` - Runs all the default tasks
- `buildAll` - This task run all the build
- `buildCI` - This run all the task for the CI
- `buildDist` - The `build` task gets your app ready to run for distribution
- `buildDebug` - The `build` task gets your app ready to run for development and testing
- `compile` - Runs recess:compile, copy:compile_assets, ngmin, concat:compile_js, uglify, index:compile
- `karmaWatch` - Runs karma in watch mode for unit testing
- `karmaUnit` - Runs karma for unit testing
- `cleanTarget` - Delete the target folder
- `FEJenkins` -Test all the frontend tasks for the pipeline
- `debugTomcatRunWar` - Generate the angular debug project which is used to create the war and run it on tomcat.
- `distTomcatRunWar` - Generate the angular dist project which is used to create the war and run it on tomcat.
- `CITomcatRunWar` - Run buildCI task and generate the angular dist project which is used to create the war and run it on tomcat.
- `CIWar` - Run buildCI task and generate the angular dist project which is used to create the war/artifact to be deployed/archived.

## Other gradle tasks

- `sonarRunner` - Analyse root prorject and it subproject with sonar runner.
- `artifactoryPublish` - Deploy artifact and generated build-info metadata to artifactory using project configuration.

## Unit Test

### How to run backend Unit test (JUnit) 
	
	```
    gradle test

    ```
	
### How to run frontend Unit test (Karma)

	```
    grunt karma:unit

    ```

    or using gradle

    ```
    gradle karmaUnit

    ```
	
## Integration Test

	
### How to run backend integration test (Mokito)

	```
    gradle integrationTestTomcat -Penv=dev

    ```

    Where environment can be "dev" for developement. If not specified it is assumed to be local.
	
### How to run frontend integration test (Protractor)

	```
    protractor protractor/config-filename

    ```
	
    Where config-filename can be debugConfig.js, ciConfig.js, distConfig.js