
outClass = ./EXE/CLASSES

srcClass = 	./src/Domain/*.java \
			./src/Presentation/*.java \
			./src/Persistence/*.java \
			./src/Exceptions/*.java \
			./src/Model/*.java

exeClass = EXE/CLASSES/Domain/* \
            EXE/CLASSES/Presentation/* \
            EXE/CLASSES/Persistence/* \
            EXE/CLASSES/Drivers/* \
            EXE/CLASSES/Exceptions/* \
            EXE/CLASSES/Model/*

classPath = ./EXE/CLASSES/

in-outputPath = ./DATA/

default: classes

classes:
	javac -cp $(classPath) -d $(outClass) $(srcClass) ./src/Drivers/*.java

executeDriverAlphabet-standard:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputAlphabet-standard.txt > $(in-outputPath)outputs/outputAlphabet-standard.txt

executeDriverAlphabet-errors:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputAlphabet-standard.txt > $(in-outputPath)outputs/outputAlphabet-standard.txt

executeDriverAlphabet-large:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputAlphabet-standard.txt > ./EXE/CLASSES/outputs/outputAlphabet-standard.txt

executeDriverTransition-standard:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputTransition-standard.txt > ./EXE/CLASSES/outputs/outputTransition-standard.txt

executeDriverTransition-errors:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputTransition-errors.txt > ./EXE/CLASSES/outputs/outputTransition-errors.txt

executeDriverTransition-large:
	java -cp $(classPath) Drivers.DriverTest < .$(in-outputPath)inputs/inputTransition-large.txt > ./EXE/CLASSES/outputs/outputTransition-large.txt

executeDriverKeyboard-standard:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputKeyboard-standard.txt > ./EXE/CLASSES/outputs/outputKeyboard-standard.txt

executeDriverKeyboard-errors:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputKeyboard-errors.txt > .$(in-outputPath)outputs/outputKeyboard-errors.txt

executeDriverKeyboard-large:
	java -cp $(classPath) Drivers.DriverTest < $(in-outputPath)inputs/inputKeyboard-large.txt > .$(in-outputPath)outputs/outputKeyboard-large.txt

executeDriverTestAll: executeDriverAlphabet-standard executeDriverAlphabet-errors executeDriverAlphabet-large executeDriverTransition-standard executeDriverTransition-errors executeDriverTransition-large executeDriverKeyboard-standard executeDriverKeyboard-errors executeDriverKeyboard-large

executeDriverTest:
	java -cp $(classPath) Drivers.DriverTest

clean:
	rm -rf $(exeClass)