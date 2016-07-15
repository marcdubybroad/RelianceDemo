# RelianceDemo

**Reliance Point Demo REST Server**

**To build and run:**
 - Make sure you have Java 8: *java -version*
 - Make sure you have [Gradle](https://gradle.org/downloads)
 - Set you `JAVA_HOME` path to you Java 8 location
 - Set your `GRADLE_HOME` to your Gradle location
 - Add `JAVA_HOME/bin` and `GRADLE_HOME/bin` to your executable path
 - Clone repository: *git clone https://github.com/marcdubybroad/RelianceDemo.git*
 - Switch to project directory: *cd RelianceDemo*
 - Modify the application and unit test configuration files (see Configuration section below):
   - Change the `server.out.results.root.directory` setting to the absolute path of a directory with write privileges
   - Change the `reliance.point.script` setting to the absolute path of the mock script
     - A sample mock script is provided at `src/main/python/mockScript.py`
 - Build: *gradle build*
   - This will download all the dependent librarues from the internet the first time
 - Run tests: *gradle test*
 - To run the RRST server from the command line:
   - Switch to the project directory
   - Build the project
   - Issue the command 'java -jar build/libs/reliancedemo-0.1.jar'

**Configuration:**
 - The file containing the application configurations are:
   - For the application: `src/main/resources/application.properties`
   - For the unit tests: `src/test/resources/application.properties`
 - The application configuration parameters are:
   - `server.port`: to set the REST server port number
   - `server.out.results.root.directory`: to set the directory where the input and output files will be created
   - `reliance.point.script`: to set the script that will call be called to calculate the burden results
     - Note: the script command format is expected to be: `script` `input` `output`
 - The file containing the application build parameters is `build.gradle`; the JAR version and base name can be set there.

**Testing**
- Samplejson payload for a POST request to the server is located at:
  - https://github.com/marcdubybroad/RelianceDemo/blob/master/src/test/resources/intelFiles/burdenInputPayload.json
