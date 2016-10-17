# test-automation 

This project is the solution for the technical test for PSL

### To run the project
This project has included a maven wrapper. So, to run the test, just execute following command:

    mvnw.cmd test

For a linux based environment, execute:

    ./mvnw test

### Configuration

The base url for the project and the Web Driver to use can be configured by modifying the `src/test/resources/test.properties` file

* To change base url for another one, just modify `baseUrl` property by the desired one. Default is `http://automatizacion.herokuapp.com/hsalazar/`
* To change the webdriver to run the test, modify `driver` property. Accepted values are 'chrome', 'opera' or 'firefox'. Default is **firefox**

**Note:** Make sure you have the Web driver server added to your path, download links and configuration guide:

* [Chrome Driver](https://sites.google.com/a/chromium.org/chromedriver/downloads)
* [Opera Driver](https://github.com/operasoftware/operachromiumdriver)
* [Firefox Driver](https://github.com/SeleniumHQ/selenium/wiki/FirefoxDriver)
