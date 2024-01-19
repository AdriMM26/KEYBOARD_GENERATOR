package Drivers;

import java.io.IOException;
import java.util.Scanner;

import Domain.*;
import Exceptions.*;

public class DriverTest {
    private DomainController domainCtrl;
    private Scanner scan;

    public DriverTest() throws IOException, NotContainsKey {
        this.domainCtrl = new DomainController();
        this.scan = new Scanner(System.in);
        this.scan.useDelimiter("\\n");
    }

    public void testCreateAlphabet() throws IOException, ContainsKey, IncorrectAlphabetType {
        System.out.println("Enter the identificator of the ALPHABET that you want\n");
        String alphabetKey = this.scan.next();
        while (alphabetKey.length() == 0) {
            System.out.println("The identificator cannot be void");
            System.out.println("Enter the identificator of the ALPHABET that you want\n");
            alphabetKey = this.scan.next();
        }

        System.out.println("Enter the number of elements for the ALPHABET || if it's -1 == GET FROM FILE\n");
        int numElements = this.scan.nextInt();
        char[] alphabetElem = new char[0];
        if (numElements == -1) {
            System.out.println("Enter the PATH FROM FILE\n");
            String path = this.scan.next();
            alphabetElem = this.domainCtrl.getCharactersFromFile(path);
        } else {
            while (numElements <= 0) {
                System.out.println("The number of elements must be higher than 0. Try again");
                System.out.println("Enter the number of elements for the ALPHABET\n");
                numElements = this.scan.nextInt();
            }

            System.out.println("Enter each character of the ALPHABET (one by one with an ENTER after each one)\n");
            alphabetElem = new char[numElements];
            for (int i = 0; i < numElements; ++i) {
                alphabetElem[i] = this.scan.next().charAt(0);
            }
        }
        try {
            this.domainCtrl.createAlphabet(alphabetKey, alphabetElem);
        } catch (ContainsKey err) {
            System.out.println(err);
            return;
        } catch (IncorrectType err) {
            System.out.println(err);
            return;
        }
        System.out.println("ALPHABET CREATED! next command\n");
    }

    public void testGetAlphabet() throws NotContainsKey {
        System.out.println("Enter the identificator of the Alphabet");
        String alphabetKey = this.scan.next();
        Alphabet alpha = new Alphabet();
        try {
            alpha = this.domainCtrl.getAlphabet(alphabetKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        }
        System.out.println("key: " + alpha.getKey());
        char[] elems = alpha.getElem();
        System.out.println("elements:");
        for (char elem : elems) {
            System.out.print(" " + elem);
        }
        System.out.println();
    }

    public void testListAlphabet() {
        Alphabet[] listAlpha = this.domainCtrl.listAlphabets();

        boolean existsAlphabets = false;
        for (Alphabet alphabet : listAlpha) {
            existsAlphabets = true;
            System.out.println("Key: " + alphabet.getKey());
        }

        if (!existsAlphabets)
            System.out.println("There aren't any Alphabets created at the moment");
    }

    private void testModifyAlphabet() throws IOException, NotContainsKey, AlphabetUsed, IncorrectAlphabetType {
        System.out.println("Enter the identificator of the Alphabet");
        String alphabetKey = this.scan.next();
        Alphabet alpha = new Alphabet();
        try {
            alpha = this.domainCtrl.getAlphabet(alphabetKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        }

        String caseIn = "0";
        boolean exit = false;
        while (!exit) {
            switch (caseIn) {
                case "0":
                    System.out.println("Options to modify an Alphabet:");
                    System.out.println("    1. addElements");
                    System.out.println("    2. delElements");
                    System.out.println("   -1. EXIT\n");
                    caseIn = this.scan.next();
                    break;
                case "1":
                    System.out.println("How many characters would you like to add?");
                    int n = this.scan.nextInt();
                    while (n <= 0) {
                        System.out.println("The number of elements must be higher than 0. Try again");
                        System.out.println("Enter the number of elements for the ALPHABET\n");
                        n = this.scan.nextInt();
                    }
                    System.out.println("Write the character/s you would like to add:");
                    boolean repeat = false;
                    for (int i = 0; i < n; ++i) {
                        String s = this.scan.next();
                        if (alpha.containString(s)) repeat = true;
                        this.domainCtrl.addElemAlphabet(alphabetKey, Character.toUpperCase(s.charAt(0)));
                    }
                    if (repeat) System.out.println("Some elements where ignored because they already exist in the Alphabet");
                    System.out.println("Element/s added");
                    exit = true;
                    break;
                case "2":
                    boolean rep = false;
                    System.out.println("How many characters would you like to delete?");
                    int n2 = this.scan.nextInt();
                    System.out.println("Write the character/s you would like to delete:");
                    while (n2 <= 0) {
                        System.out.println("The number of elements must be higher than 0. Try again");
                        System.out.println("Enter the number of elements for the ALPHABET\n");
                        n2 = this.scan.nextInt();
                    }
                    for (int i = 0; i < n2; ++i) {
                        String s = this.scan.next();
                        if (!alpha.containString(s)) rep = true;
                        this.domainCtrl.delElemAlphabet(alphabetKey, Character.toUpperCase(s.charAt(0)));
                    }
                    if (rep) System.out.println("Some elements where ignored because they didn't exist in the Alphabet or were repeated");
                    if(alpha.getElem().length == 0){
                        try {
                            this.domainCtrl.deleteAlphabet(alphabetKey);
                        } catch (AlphabetUsed | FileNotDeleted err) {
                            System.out.println(err);
                            return;
                        }
                        System.out.println("ALPHABET DELETED due to deletion of all the elements! next command\n");
                    }
                    else System.out.println("Element/s deleted");
                    exit = true;
                    break;
                case "-1":
                    System.out.println("EXITING ALPHABET MODIFIER\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Input incorrect, try again:\n");
                    caseIn = "0";
                    break;
            }
        }
    }

    public void testDeleteAlphabet() throws IOException, NotContainsKey {
        System.out.println("Enter the identificator of the Alphabet");
        String alphabetKey = this.scan.next();
        try {
            this.domainCtrl.deleteAlphabet(alphabetKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        } catch (AlphabetUsed | FileNotDeleted err) {
            System.out.println(err);
            return;
        }
        System.out.println("ALPHABET DELETED! next command");
    }

    public void testCreateTransitionText() throws NotContainsKey, IOException, ContainsKey {
        System.out.println("Enter the NAME of the ALPHABET used\n");
        String alphabetKey = this.scan.next();
        Alphabet alphabet = new Alphabet();
        try {
            alphabet = domainCtrl.getAlphabet(alphabetKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        }
        System.out.println("Enter the identificator of the TRANSITION:\n");
        String transKey = this.scan.next();
        while(transKey.length() == 0) {
            System.out.println("The identificator cannot be void");
            System.out.println("Enter the identificator of the TRANSITION that you want\n");
            transKey = this.scan.next();
        }
        System.out.println("Enter the TEXT: || 'F' from a FILE");
        String textIn = this.scan.next();
        while(textIn.length()==0){
            System.out.println("You cannot enter a void text.\n Enter the TEXT:");
            textIn = this.scan.next();
        }
        if (textIn.length() == 1) {
            System.out.println("Enter PATH for the text FILE\n");
            String path = this.scan.next();
            textIn = this.domainCtrl.getTextFromFile(path);
        }
        if (!alphabet.containString(textIn)) {
            System.out.println("ERROR: Word with characters not in the Alphabet");
            return;
        }
        try {
            this.domainCtrl.createMatrixText(alphabetKey, transKey, textIn);
        } catch (ContainsKey err) {
            System.out.println(err);
            return;
        } catch (StringNotInAlphabet e) {
            throw new RuntimeException(e);
        } catch (IncorrectType e) {
            throw new RuntimeException(e);
        }
        System.out.println("TRANSITION CREATED! next command");
    }

    public void testCreateTransitionWFL() throws NotContainsKey {
        System.out.println("Enter the NAME of the ALPHABET used\n");
        String alphabetKey = this.scan.next();
        Alphabet alphabet = new Alphabet();
        try {
             alphabet = domainCtrl.getAlphabet(alphabetKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        }

        System.out.println("Enter the identificator of the TRANSITION:\n");
        String transKey = this.scan.next();
        while(transKey.length() == 0) {
            System.out.println("The identificator cannot be void");
            System.out.println("Enter the identificator of the TRANSITION that you want\n");
            transKey = this.scan.next();
        }

        System.out.println("Enter the number of words in the list:\n");
        int numElem = this.scan.nextInt();
        while (numElem <= 0) {
            System.out.println("The number of elements must be higher than 0. Try again");
            System.out.println("Enter the number of words in the list\n");
            numElem = this.scan.nextInt();
        }

        String[] wordList = new String[numElem];
        int[] frequencyList = new int[numElem];
        System.out.println("Enter each word with the frequency\n    i.e: hello \n         3");
        for (int i = 0; i < numElem; ++i) {
            wordList[i] = this.scan.next();
            frequencyList[i] = this.scan.nextInt();
            if (!alphabet.containString(wordList[i])) {
                System.out.println("ERROR: Word with characters not in the Alphabet");
                return;
            }
        }

        try {
            this.domainCtrl.createMatrixFrequencyList(alphabetKey, transKey, frequencyList, wordList);
        } catch (ContainsKey | IOException | StringNotInAlphabet | IncorrectType e) {
            System.out.println(e);
            return;
        }
        System.out.println("TRANSITION CREATED! next command");
    }

    public void testGetTransitionMatrix() {
        System.out.println("Enter the identificator of the Transition");
        String transKey = this.scan.next();
        TransitionMatrix trans = new TransitionMatrix();
        try {
            trans = this.domainCtrl.getTransitionMatrix(transKey);
        } catch (NotContainsKey e) {
            System.out.println(e);
            return;
        }
        System.out.println("key: " + trans.getKey());
        int[][] matrix = trans.getTransitionMatrix();
        System.out.println("elements:");
        for (int i = 0; i < matrix.length; ++i) {
            int[] row = matrix[i];
            for (int j = 0; j < row.length; ++j) {
                System.out.printf(" " + matrix[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public void testListTransition() {
        TransitionMatrix[] listTrans = this.domainCtrl.listTransitionMatrix();

        boolean existsAlphabets = false;
        for (TransitionMatrix listTran : listTrans) {
            existsAlphabets = true;
            System.out.println("Key: " + listTran.getKey());
        }

        if (!existsAlphabets)
            System.out.println("There aren't any TransitionMatrix created at the moment");
    }

    public void testDeleteTransition() throws IOException, NotContainsKey {
        System.out.println("Enter the identificator of the Transition");
        String transKey = this.scan.next();
        try {
            this.domainCtrl.deleteTransitionMatrix(transKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        } catch (FileNotDeleted e) {
            throw new RuntimeException(e);
        }
        System.out.println("TRANSITION DELETED! next command");
    }

    public void testCreateKeyboard() throws IOException {
        System.out.println("Enter the identificator of the ALGORITHM ('QAP' or 'Greedy')\n");
        String algorithKey = this.scan.next();
        if (!algorithKey.equals("QAP") && !algorithKey.equals("Greedy")) {
            System.out.println(
                    "ERROR: Algorithm " + algorithKey + " is not included in the list of algorithms implemented");
            return;
        }
        System.out.println("Enter the identificator of the TRANSITION\n");
        String transKey = this.scan.next();
        System.out.println("Enter the Name of the KEYBOARD\n");
        String keyboardKey = this.scan.next();
        while(keyboardKey.length() == 0) {
            System.out.println("The identificator cannot be void");
            System.out.println("Enter the Name of the KEYBOARD\n");
            keyboardKey = this.scan.next();
        }
        try {
            this.domainCtrl.createKeyboard(keyboardKey, transKey, algorithKey);
        } catch (IncorrectType | ContainsKey | NotContainsKey e) {
            System.out.println(e);
            return;
        }
        System.out.println("KEYBOARD CREATED! next command\n");
    }

    public void testConsultKeyboard() throws NotContainsKey {
        System.out.println("Enter the identificator of the Keyboard");
        String keyKey = this.scan.next();
        Keyboard keyboard = new Keyboard();
        try {
            keyboard = this.domainCtrl.getKeyboard(keyKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        }
        System.out.println("key: " + keyboard.getKey());
        char[][] keyDist = keyboard.getDistribution();
        System.out.println("distribution:");
        for (int i = 0; i < keyDist.length; ++i) {
            char[] row = keyDist[i];
            for (int j = 0; j < row.length; ++j) {
                System.out.printf(" " + keyDist[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public void testListKeyboards() {
        String[] listKeyNames = this.domainCtrl.listKeyboard();

        boolean existsAlphabets = false;
        for (String name : listKeyNames) {
            existsAlphabets = true;
            System.out.println("Key: " + name);
        }

        if (!existsAlphabets)
            System.out.println("There aren't any Keyboards created at the moment");
    }

    public void testModifyKeyboard() throws NotContainsKey {
        System.out.println("Enter the identificator of the Keyboard");
        String keyKey = this.scan.next();
        Keyboard keyboard = new Keyboard();
        try {
            keyboard = this.domainCtrl.getKeyboard(keyKey);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        }
        char[][] dist = keyboard.getDistribution();
        System.out.println("Enter the FIRST 'i' and 'j' position for the swap: 0 <= i < " + dist.length + "   0 <= j < "
                + dist[0].length);
        int i1 = this.scan.nextInt();
        int j1 = this.scan.nextInt();
        System.out.println("Enter the SECOND 'i' and 'j' position for the swap: 0 <= i < " + dist.length
                + "   0 <= j < " + dist[0].length);
        int i2 = this.scan.nextInt();
        int j2 = this.scan.nextInt();
        try {
            this.domainCtrl.modifyKeyboard(keyKey, i1, j1, i2, j2);
        } catch (NotContainsKey err) {
            System.out.println(err);
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("KEYBOARD modified! next command");
    }

    public void testEvaluateKeyboard() {
        System.out.println("Enter the identificator of the Keyboard");
        String keyKey = this.scan.next();
        System.out.println("Enter the identificator of the Transition to be evaluated");
        String transKey = this.scan.next();
        double evaluation = 0;
        try {
            evaluation = this.domainCtrl.evaluateKeyboard(keyKey, transKey);
        } catch (NotContainsKey e) {
            System.out.println(e);
            return;
        }
        System.out.println("Results of the avaluation: " + evaluation);
    }

    public void testDeleteKeyboard() throws IOException {
        System.out.println("Enter the identificator of the Keyboard");
        String keyKey = this.scan.next();
        try {
            this.domainCtrl.deleteKeyboard(keyKey);
        } catch (NotContainsKey e) {
            System.out.println(e);
            return;
        } catch (FileNotDeleted e) {
            throw new RuntimeException(e);
        }
        System.out.println("KEYBOARD DELETED! next command");
    }

    public void getOptions() {
        System.out.println("Options:");
        System.out.println("    1. createAlphabet");
        System.out.println("    2. getAlphabet");
        System.out.println("    3. listAlphabets");
        System.out.println("    4. modifyAlphabet");
        System.out.println("    5. deleteAlphabet");
        System.out.println("    6. createMatrixText");
        System.out.println("    7. createMatrixFrequencyList");
        System.out.println("    8. getTransitionMatrix");
        System.out.println("    9. listTransitionMatrix");
        System.out.println("    10. deleteTransitionMatrix");
        System.out.println("    11. createKeyboard");
        System.out.println("    12. consultKeyboard");
        System.out.println("    13. listKeyboardNames");
        System.out.println("    14. modifyKeyboard");
        System.out.println("    15. evaluateKeyboard");
        System.out.println("    16. deleteKeyboard");
    }

    public static void main(String[] args) throws IOException, ContainsKey, IncorrectAlphabetType, NotContainsKey, AlphabetUsed {
        System.out.println("WELCOME TO DRIVER TEST");
        DriverTest driver = new DriverTest();
        String caseIn = "0";
        boolean exit = false;
        while (!exit) {
            switch (caseIn) {
                case "0":
                    driver.getOptions();
                    break;
                case "1":
                    System.out.printf("1. createAlphabet:");
                    System.out.println("    [USAGE: ALPHABET NAME (string) + ALPHABET ELEMENTS (char[])]\n");
                    driver.testCreateAlphabet();
                    break;
                case "2":
                    System.out.printf("2. getAlphabet:");
                    System.out.println("    [USAGE: ALPHABET NAME (string)]\n");
                    driver.testGetAlphabet();
                    break;
                case "3":
                    System.out.println("3. listAlphabets:");
                    driver.testListAlphabet();
                    break;
                case "4":
                    System.out.printf("4. modifyAlphabet");
                    System.out.println("    [USAGE: ALPHABET NAME (string)]\n");
                    driver.testModifyAlphabet();
                    break;
                case "5":
                    System.out.printf("5. deleteAlphabet:");
                    System.out.println("    [USAGE: ALPHABET NAME (string)]\n");
                    driver.testDeleteAlphabet();
                    break;
                case "6":
                    System.out.printf("6. createMatrixText:");
                    System.out.println(
                            "    [USAGE: ALPHABET NAME (string) + TRANSITION NAME (string) + TEXT (string)]\n");
                    driver.testCreateTransitionText();
                    break;
                case "7":
                    System.out.printf("7. createMatrixFrequencyList:");
                    System.out.println(
                            "   [USAGE: ALPHABET NAME (string) + TRANSITION NAME (string) + NUM WORDS (int) + WORD & FREQUENCY (string + int)]\\n");
                    driver.testCreateTransitionWFL();
                    break;
                case "8":
                    System.out.printf("8. getTransitionMatrix:");
                    System.out.println("    [USAGE: TRANSITION NAME (string)]\n");
                    driver.testGetTransitionMatrix();
                    break;
                case "9":
                    System.out.println("9. listTransitionMatrix:");
                    driver.testListTransition();
                    break;
                case "10":
                    System.out.printf("10. deleteTransitionMatrix:");
                    System.out.println("    [USAGE: TRANSITION NAME (string)]\n");
                    driver.testDeleteTransition();
                    break;
                case "11":
                    System.out.printf("11. createKeyboard:");
                    System.out.println("  ALGORITHM NAME (string) + TRANSITION NAME (string) + KEYBOARD (string)]\n");
                    driver.testCreateKeyboard();
                    break;
                case "12":
                    System.out.printf("12. consultKeyboard:");
                    System.out.println("    [USAGE: KEBOARD NAME (string)]\n");
                    driver.testConsultKeyboard();
                    break;
                case "13":
                    System.out.println("13. listKeyboardNames:");
                    driver.testListKeyboards();
                    break;
                case "14":
                    System.out.println("14. modifyKeyboard:");
                    driver.testModifyKeyboard();
                    break;
                case "15":
                    System.out.printf("15. evaluateKeyboard:");
                    System.out.println("    [USAGE: KEBOARD NAME (string)]\n");
                    driver.testEvaluateKeyboard();
                    break;
                case "16":
                    System.out.printf("16. deleteKeyboard:");
                    System.out.println("    [USAGE: KEBOARD NAME (string)]\n");
                    driver.testDeleteKeyboard();
                    break;

                case "-1":
                    System.out.println("EXIT APPLICATION, thanks ;)");
                    exit = true;
                    break;
                default:
                    System.out.println("Input incorrect, try again:");
                    break;
            }
            if (!exit) {
                System.out.println("Enter case:     [0. show the options || -1. CLOSE]");
                caseIn = driver.scan.next(); // get the case
            }
        }
    }
}
