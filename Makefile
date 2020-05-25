
JSOUP=./jsoup-1.13.1.jar

all: compile run

compile:
	javac -cp $(JSOUP):output:. Main.java
run:
	java -cp $(JSOUP):output:. Main
runToFile:
	java Main > test.ll
clean:
	rm -f *.class
	rm -rf output