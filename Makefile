
JSOUP=./jsoup-1.13.1.jar

all: compile run

compile:
	javac -cp $(JSOUP):output:. GPWDownloader.java
run:
	java -cp $(JSOUP):output:. GPWDownloader
runToFile:
	java Main > test.ll