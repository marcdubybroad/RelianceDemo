# RelianceDemo

**Reliance Point Demo REST Server**

**To build and run:**
 - Make sure you have Java 8: *java -version*
 - Make sure you have [Gradle](https://gradle.org/downloads)
 - Clone repository: *git clone https://github.com/marcdubybroad/RelianceDemo.git*
 - Switch to project directory: *cd RelianceDemo*
 - Build: *gradle build*
 - Run tests: *gradle test*
 - To run the RRST server from the command line:
   - Switch to the project directory
   - Build the project
   - Issue the command 'java -jar build/libs/reliancedemo-0.0.1-SNAPSHOT.jar'

**Configuration:**
 - The file containing the configurations are:
   - For the application: src/main/resources/application.properties
   - For the unit tests: src/test/resources/application.properties
 - The configuration parameters are:
   - `server.port`: to set the REST server port number
   - `server.out.results.root.directory`: to set the directory where the input and output files will be created
   - `reliance.point.script`: to set the script that will call be called to calculate the burden results
     - Note: the script command format is expected to be: <script> <input> <output>
