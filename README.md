# Keyboard Generator g42.5

This project creates an efficient layout for custom keyboards. It is divided into three phases:

- First you decide the characters that will form the keyboard, this will be called Alphabet.
- Then you choose from what will the layout be base on, from a text or a list of words and their frequencies. This will create a TransitionMatrix.
- Finally, with an Alphabet and a TransitionMatrix it creates the Keyboard. For that we have two algorithms available that decide the best character distribution.

## Created by

- Adrià Martínez
- Arnau Gomez
- Arnau Tomas
- Roger Canal

## Directories

/KEYBOARD_GENERATOR

- main directory
  \
  / EXE\
  &ensp;
  / CLASSES\
  &ensp; &nbsp;&nbsp;&nbsp;
  / Domain\
  &ensp; &nbsp;&nbsp;&nbsp;
  / Drivers\
  &ensp; &nbsp;&nbsp;&nbsp;
  / inputs\
  &ensp; &nbsp;&nbsp;&nbsp;
  / outputs\
  &ensp; &nbsp;&nbsp;&nbsp;
  / Presentation
- Compiled classes, inputs for execution.
  \
  / src\
  &ensp;
  / Domain\
  &ensp; &nbsp;&nbsp;&nbsp;
  / Drivers\
  &ensp; &nbsp;&nbsp;&nbsp;
  / Presentation\
  &ensp; &nbsp;&nbsp;&nbsp;
  / Testers
  &ensp; &nbsp;&nbsp;&nbsp;
- Source for all the code, subdirectory for each layer, Testers and Drivers.

## Compilation & Execution

Inside of KEYBOARD_GENERATOR/ we have a Makefile with these options:

- classes: compiles all the classes (Domain, Presentation) + Drivers.
- clean: removes all the .classes and outputs created.
- executeDriverTest: run the driver to manually test the functions!
- executeDriver[Alphabet - Transition - Keyboard]-[standard - errors - large]: executes the DriverTest with pre-created input and saves the output.
- executeDriverTestAll: runs all the scenarios with their outputs

Doing 'make classes' we get the .class binary inside the /EXE/CLASSES/ directory.
Once we have all compiled, we can test the different executions precreated with 'make executeDriver[...]'

Inside /DATA/TestsInputOutput there are 2 folders for the input and output of the testers in which:

- input\*.txt can be used in the execution of the binary.
- output\*.txt are the output for each case.

## Documentation

You will  find a JavaDocs implemented inside the DOCU/Javadocs folder, in which you can find the files generated.
Executing allclasses-index.html you will find an extended documentation of each class, it's attributes and it's functions.

You will find a text file called classesForGroupMember.txt in the directoy DOCU, in which there is stated the Author/Main Responsible of each class.

You will find the OLD description of every use case in the file in DOCU directory called UseCases_old.pdf.
Additionally, you will find the NEW description of every use case in the file in DOCU directory called UseCases_new.pdf.
Then, you can encounter in the Canvis_Entregues.pdf file in tge DOCU directory, the DIFFERENCES between this and the last delivery in: the UseCases, the beviour of the project and the ClassDiagram.

You will also find a .pdf file called PresentationTest.pdf in the directory DOCU, in which you can see the expected actions and errors you can do while executing the NEW UseCases in the Presentation layer.

Finally you will find the User Manual of the project in a file called User_Manual.pdf in the DOCU directory.


## Input files for upload option

While using our app if you would like to upload a file it has to be a file with a .txt etension. We have created some example files in order to provide you some tests and also to serve as an example of what the application would expect as a good input.
You can find those files in the DATA/UploadFiles directory.

- Alphabet example: 	 alphabet_example.txt
- FrequencyList example: frequencyList_example.txt
- WordList example: 	 wordList_example.txt
- Text example: 		 lorem_ipsum.txt

## Notes

The creation of big Keyboards can take a few minutes. For inputKeyboard-large option, is expected to last for about 1 minute.
