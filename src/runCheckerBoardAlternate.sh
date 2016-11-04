set -e
javac com/groupone/p2pgame/CheckerBoardAlternate.java
# java com.groupone.p2pgame.CheckerBoardAlternate
jar cfm Checkers.jar manifest.txt com/groupone/p2pgame/*.class
java -jar Checkers.jar


