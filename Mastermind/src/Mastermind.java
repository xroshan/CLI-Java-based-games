/*
Roshan Shrestha
This is a popular mastermind game. It randomnly generates 4 digit number (1-9) and the player has to guess it through 10 trials. For each trial, the number
of digits matched and the number of digits in position are provided.
The logic behind this game is converting integers into strings so that it is easy to check the matching numbers with the help of indexOf.
 */

import java.util.Scanner;
import java.util.Random;

public class Mastermind {

    public static void main(String[] args) {
        String actual_number = rand();               //generate random digits
        String[] picked = new String[10];            //initializing new String array to store previous trials
        String n;
        System.out.println("---------------------MASTERMIND---------------------");                             //Game description
        System.out.println("This program generates the random 4 digit number (1-9) and the player has to guess it through 10 trials.");
        System.out.println("For each trial, the first column gives the number of digits matched and the second column gives the number of digits in position.");
        System.out.println("GOOD LUCK!!!");                                         //Wish of luck from the programmer :)
        System.out.println();
        for (int i = 0; i < 10; i++) {                                              //loop runs 10 times as there are 10 trials
            n = userInteger("Please enter 4 digit positive number (1-9) or q to quit: ", actual_number);        //calls method to take the user input
            picked[i] = n;                                                                                 //stores the input in matrix
            printBox(picked, actual_number);                                                        //calls method to print current game situation
            if (check_pos(picked[i], actual_number) == 4) {                                         //calls method to check the winning criteria
                System.out.printf("Congratulation! You found the number in %dth trial. You are genius.\n",i+1);
                System.exit(0);                                                                     //exits after player finds out the number
            }
        }
        System.out.println("The correct number was " + actual_number + ".");                  //gives the actual number if player fails to guess  
        System.out.println("Thank you for playing!!! Come back again.");
    }

    public static String userInteger(String message, String actual_number) {                //method to take the correct input from the user
        Scanner in = new Scanner(System.in);
        int i;
        while (true) {                                                                    //loop runs until it finds the correct input
            System.out.print(message);
            String s = in.next();                                                         //stores the input in strings
            if (s.equals("q")) {                                                          //quits program if player input 'q'
                System.out.println("\nThe correct number was " + actual_number + ".");
                System.out.println("Thanks for playing!!! Come back again.");
                System.exit(0);
            }
            if (s.length() == 4) {                                                  //checks the string length and if it contains '0'
                for (i = 0; i < s.length(); i++) {
                    if ("123456789".indexOf(s.charAt(i)) < 0) {
                        break;
                    } else if (i == 3) {                                            //after all the digits are checked it returns the number in string.
                        return s;
                    }
                }

            }
            System.out.println("Invalid number!!!");                                    //loop runs again if the user input is invalid
        }
    }

    public static void printBox(String[] picked, String actual_number) {                    //method to create current game situation in boxes
        String dash = "-----------------";                              
        String dash2 = "---------";
        String space = "            ";
        System.out.println("   Current game" + space + "  Number matched");                      //just making the proper display
        System.out.println(dash + space + " " + dash2);                             //borders
        for (int i = 0; i < 10; i++) {                                          //loop runs for 10 times as there are 10 trials
            System.out.print("| ");
            if (picked[i] == null) {                                            //if the elements in the array are null, the box printed empty
                for (int j = 0; j < 4; j++) {
                    System.out.print("  | ");                                   //blank boxes
                }
                System.out.print(space + "|   |   |");                          //blank boxes
                System.out.println("\n" + dash + space + " " + dash2);          //borders
                continue;                                                       //continues to loop and checks if the element is null
            }
            for (int j = 0; j < 4; j++) {                                       //displays the user inputed digits in box format
                System.out.print(picked[i].charAt(j) + " | ");
            }
            int pos = check_pos(picked[i], actual_number);
            int no_pos = check_num(picked[i], actual_number) - pos;
            System.out.print(space + "| " + no_pos + " | " + pos + " |");    //displays the matched digits
            System.out.println("\n" + dash + space + " " + dash2);                              //borders
        }
        System.out.println();
    }

    public static String rand() {                                       //method to generate random numbers
        Random r = new Random();
        while (true) {                                                  //generates random number with no 0
            int a = r.nextInt(9001) + 999;                              //generates 4 digit random number
            String b = Integer.toString(a);                             
            if (b.indexOf("0") < 0) {                                   //only returns that number with no 0
                return b;
            }
        }
    }

    public static int check_num(String n, String actual_number) {           //method to check the number of digits matched
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int index = actual_number.indexOf(n.charAt(i));                 //finds the index value of user input digits in the actual number
            if (index > -1) {                                               //if there is common digit, that digit is cut from the actual_digit
                actual_number = actual_number.substring(0, index) + actual_number.substring(index + 1, actual_number.length());
                count++;                                                    //counts the number of digits matched
            }
        }
        return count;
    }

    public static int check_pos(String n, String actual_number) {               //method to check the number of digits in position
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (actual_number.charAt(i) == n.charAt(i)) {                   //checks the digits in same index
                count++;                                                    //counts if position match
            }
        }
        return count;
    }
}
