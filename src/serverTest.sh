set -e
touch com/groupone/serverClient/preventError.class
rm com/groupone/serverClient/*.class
javac com/groupone/serverClient/*.java
java com.groupone.serverClient.Server 10200
# jar cfm ServerTest.jar serverManifest.txt com/groupone/serverClient/*.class
# java -jar ServerTest.jar
