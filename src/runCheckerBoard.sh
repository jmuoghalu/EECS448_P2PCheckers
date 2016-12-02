set -e
touch com/groupone/p2pgame/preventError.class
rm com/groupone/p2pgame/*.class
javac com/groupone/p2pgame/*.java
# java com.groupone.p2pgame.GameDriver
jar cfm Checkers.jar checkersManifest.txt com/groupone/p2pgame/*.class
java -jar Checkers.jar
