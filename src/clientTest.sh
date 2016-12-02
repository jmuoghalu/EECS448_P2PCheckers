set -e
touch com/groupone/serverClient/preventError.class
rm com/groupone/serverClient/*.class
javac com/groupone/serverClient/*.java
# java com.groupone.serverClient.Client
jar cfm ClientTest.jar clientManifest.txt com/groupone/serverClient/*.class
java -jar ClientTest.jar
