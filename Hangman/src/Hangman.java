/*
Roshan Shrestha
This program picks up the random word from words.txt and then the user has to guess with the provided hints. The user is given total of 6 chances to 
guess the word letter by letter.
The logic behind this program is making all the words and input into UpperCase and then checking the user input letter by letter. It ignores the repeated
input.
words.txt file stores the words and hints. Hints starts with '-'. 
 */

import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Hangman {

    public static void main(String[] args) {
        String[] word_and_hint = random_word(wordlist());               //stores randomly picked word and its hint by calling random_word method
        String actual_word1 = word_and_hint[0].toUpperCase();            //stores random word and hint in separate variable for more convenience
        String actual_word = actual_word1.replace(" ", "");               //removes space from the acutal word
        String hint = word_and_hint[1].substring(1);
        hint = hint.toUpperCase();
        String picked = "";
        System.out.println("------------------HANGMAN------------------");
        System.out.println("You have to guess the word based on given hint. You are given 6 chances and you have to guess letter by letter.");    //Game description
        for (int i = 0; i < 6;) {                                   //iterates 6 times because there is only 6 chances
            System.out.println("\nChance: " + (6 - i) + "              Hint: " + hint);           //prints chance and hint
            print(picked, actual_word);                                                     //prints the current game situation
            String a = userletter("Please enter a letter or 0 to quit: ", actual_word, picked);     //asks for user input and verifies its validity
            picked = picked + a;                                                                    //stores all the user input in variable
            i = trial(picked, actual_word);                          //checks if picked word is correct or incorrect; if incorrect chance is decreased by 1
            if (game_situation(picked, actual_word))          //calls game_situation to check if the picked letter match actual word
            {
                print(picked, actual_word); 
                System.out.println("Congratulation!!You guessed the word. But, the word was easy.");
                System.exit(0);                                                     //quits program after winning
            }
        }
        System.out.println("\nYou failed to guess the word.");                  //if failed to guess words, these lines gets printed
        System.out.println("The correct word was " + actual_word1 + ".");
        System.out.println("Thanks for playing!!! Come back again.");
    }

    public static boolean game_situation(String picked, String actual_word)         //checks if the user picked letter match the actual words or not
    {
        for (int i = 0; i < picked.length(); i++)               //this loop checks the user input letter by letter
        {
            String letter = picked.substring(i, i + 1);
            if (actual_word.indexOf(letter) > -1) {                 //removes the matched letter from actual word
                actual_word = actual_word.replace(letter, "");
            }
        }
        if (actual_word.length() == 0) {                        //after removing letters, if the length of actual word is 0, the return true; that winning
            return true;
        }
        return false;
    }

    public static int trial(String picked, String actual_word) {                //gives the number of incorrect letters
        int i;
        int count = 0;
        for (i = 0; i < picked.length(); i++) {                         //this loop checks letter by letter and then counts the incorrect letters.
            char letter = picked.charAt(i);
            if (actual_word.indexOf(letter) < 0) {
                count++;
            }
        }
        return count;
    }

    public static void print(String picked, String actual_word) {                       //prints the current game situation in user readable format
        int i;
        for (i = 0; i < actual_word.length(); i++) {                //this loop prints the picked letters that are matched in order.
            char letter = actual_word.charAt(i);
            if (picked.indexOf(letter) > -1) {
                System.out.print(letter + " ");
            } else if (letter == ' ') {
                System.out.print("   ");
            } else {
                System.out.print("_ ");                             //dash is proved if the letter has not been yet discovered
            }
        }
        System.out.print("          {");
        for (i = 0; i < picked.length(); i++) {                         //this loop prints all picked letter for convenience
            System.out.print(" " + picked.charAt(i));
        }
        System.out.print(" }\n\n");
    }

    public static String userletter(String message, String actual_word, String picked) {            //asks for user input and checks its validity
        Scanner in = new Scanner(System.in);
        int i;
        String alp = "abcdefghijklmnopqrstuvwxyz";                  //initializing alphabets for checking user input
        alp = alp + alp.toUpperCase();
        while (true) {
            System.out.print(message);                                  
            String s = in.nextLine();
            if (s.equals("0")) {                                    //if user enters '0' the program quits
                System.out.println("\nThe correct word was " + actual_word + ".");
                System.out.println("Thanks for playing!!! Come back again.");
                System.exit(0);
            }
            if (s.length() == 1) {                                  //checks length of user input, only accepts input of length 1
                for (i = 0; i < alp.length(); i++) {                
                    if (picked.indexOf(s.toUpperCase()) > -1) {         //checks if they are picked previously or not
                        System.out.println("Already picked!!!");
                        break;
                    } else if (s.equals(alp.substring(i, i + 1))) {     //checks if they are alphabets; case-insensitive
                        return s.toUpperCase();                         //returns the valid input, else the loop runs again ask the user for input
                    }
                }
            }
            System.out.println("Invalid input!!!\n");
        }
    }

    public static ArrayList<String> wordlist() {                    //scans the words.txt and stores all words in array
        ArrayList<String> result = new ArrayList<String>();
        File words = new File("words.txt");
        Scanner wordsFile = null;
        try {                                                       //try catch; file exception handling
            wordsFile = new Scanner(words);
        } catch (Exception e) {
            System.out.println("No input file found ");
            System.exit(0);
        }
        while (wordsFile.hasNext()) {                               //only proceeds if there is another input in words file
            String a = wordsFile.nextLine();                        //stores all the data in same line in string and then stores in array
            result.add(a);
        }
        wordsFile.close();                                      //closes the words.txt file
        return result;
    }

    public static String[] random_word(ArrayList<String> wordlist) {                //picks up random word and related hint from the wordlist
        Random r = new Random();
        while (true) {
            int x = r.nextInt(wordlist.size());                                     //generates the random number less than length of wordlist array
            String word = wordlist.get(x);                                          //gets the word from array linked with random number
            if (word.charAt(0) != '-') {                                            //checks if the picked word is valid or not; if it starts with '-' its a hint, otherwise its a word
                for (int i = x; i > -1; i--) {                                      //this loop looks for hints related to picked word by checking the first character
                    String a = wordlist.get(i);
                    if (a.charAt(0) == '-') {                                       //if first character is '-', its the hint related to picked number
                        String[] word_and_hint = {word, a};                         //stores the word and hint in array and returns them
                        return word_and_hint;
                    }
                }
            }
        }
    }
}
