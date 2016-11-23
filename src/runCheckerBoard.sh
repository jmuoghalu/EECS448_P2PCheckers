set -e
javac com/groupone/p2pgame/*.java
# java com.groupone.p2pgame.CheckerBoard
jar cfm Checkers.jar manifest.txt com/groupone/p2pgame/*.class
java -jar Checkers.jar


