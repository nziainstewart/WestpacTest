Test Setup Instructions:

1. Files to download and install (if required):

- Java 8 SDK (jdk-8u181-windows-x64.exe)
- Eclipse Neon, using 'Eclipse IDE for Java Developers' option (https://www.eclipse.org/neon/)
- Selenium Client & WebDriver Language Bindings 3.14.0 (https://www.seleniumhq.org/download/)
	- unzip the archive file to a local directory
- Chromedriver 2.42 (http://chromedriver.chromium.org/downloads) 
	- create a directory 'c:\chromedriver' and place 'chromedriver.exe' in it.

2. Importing Eclipse Project from Repository:

Open Eclipse and accept default workspace
Click on File > Import and select Git > Projects from Git
Select Clone URI
Enter 'https://github.com/nziainstewart/WestpacTest.git' in the URI field
Click Next and verify 'master' is selected
Click Next and choose a local directory to store the files
Click Next and verify that 'Import existing Eclipse projects' is selected
Click Next and verify the 'TechnicalTest' files are selected
Click Finish
Right click on project name 'TechnicalTest' and select Build Path > Configure Build Path
Change to Libraries tab
Click on Add External JARs
Select local directory where you expanded the selenium zip file
Select all jars in that directory
Click on OK

3. Running the test in Eclipse:

Right click on the project name 'TechnicalTest' 
Select Run As > Java Application
Tests will run and report a PASS or FAIL for each test in the console window.

4. Additional - running the test from .jar file:

Open a command prompt and browse to the local repository folder. You can find this by right clicking on the project name in Eclipse, select Properties and view the main 'Resource' tab - the directory is shown as 'Location'
Change directory to jar folder
Type 'java -jar westpac_selenium_tests.jar'

Tests will run and report a PASS or FAIL for each test in the console window.

