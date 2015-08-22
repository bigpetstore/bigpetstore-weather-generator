BigPetStore Weather Generator
=============================

Library for generating weather on a zipcode-by-zipcode basis.

Building and Testing
--------------------
We use the Gradle build system for the BPS data generator so you'll need
to install Gradle on your system.
Once that's done, you can use gradle to run the included unit tests
and build the data generator jar.

To build:
    
    $ gradle build

This will create several directories and a jar located at:
    
    build/libs/bigpetstore-weather-generator-0.9.0-SNAPSHOT.jar

Building automatically runs the included unit tests.  If you would prefer
to just run the unit tests, you can do so by:

    $ gradle test

To clean up the build files, run:

    $ gradle clean

To install a jar into your local maven repository:

    $ gradle install
