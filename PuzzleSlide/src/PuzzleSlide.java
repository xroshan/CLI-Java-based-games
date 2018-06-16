/*
Roshan Shrestha
This program creates a random puzzle and the user has to arrange them in order just by sliding the box adjacent to the empty ones. The user can slide
number boxes by entering the number in that box. Only the box adjacent to empty ones can be slided.
The winning condition of the puzzle is to order the numbers in ascending order horizontally as shown below.
---------------------
|* 1*|* 2*|* 3*|* 4*|
---------------------
|* 5*|* 6*|* 7*|* 8*|
---------------------
|* 9*|*10*|*11*|*12*|
---------------------
|*13*|*14*|*15*|    |
---------------------
The logic behind this game is to store all the numbers (0-15) in 4*4 matrix and the empty box is denoted by 0. The user can only slide the numbers that
are adjacent to 0.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PuzzleSlide {
    public static void main(String[] args) {
        int turn = 1;
        int[][] puzzle = create_puzzle();                       //creates the random puzzle in 4*4 matrix
        //puzzle = test();                                      //uncomment it if you want to see if winning condition works
        System.out.println("------------------PUZZLE SLIDE------------------");                 //Game description
        System.out.println("You have to solve this randonly created puzzle by arranging them in order just by sliding the box adjacent to the empty ones.");
        System.out.println("The winning condition of the puzzle is to order the numbers in ascending order horizontally.");
        System.out.println("GOOD LUCK!!!");
        print_game(puzzle);                                 //displays the current game situation
        while (true) {                                      //loop runs until the puzzle is solved
            System.out.println("Turn: " + turn);            
            int user_in = userInteger("Enter a number that you want to move, or q to quit: ", puzzle);      //call userInteger method to verfy user input validity
            puzzle = game_situation(puzzle, user_in);                           //calls game_situation method and stores the curret game info in puzzle 4*4 matrix
            print_game(puzzle);
            if (game_logic(puzzle)) {                                           //calls method to check the winning condition
                System.out.println("Congratulation!!! You solved the puzzle in " + turn + " turns. You are genius.");
                System.exit(0);                                                 //program ends if the winning condition is met
            }
            turn++;
        }
    }

    public static int userInteger(String message, int[][] puzzle) {                 //asks for user input and verifies its validity
        Scanner in = new Scanner(System.in);
        int i, j;
        while (true) {                                  //loop runs until it finds the valid input
            System.out.print(message);
            if (in.hasNextInt()) {                      //this long nested if-else statement is to check whether the user input number is adjacent to 0 or not
                int x = in.nextInt();
                for (i = 0; i < 4; i++) {
                    for (j = 0; j < 4; j++) {
                        if (puzzle[i][j] == 0) {                //these are all the conditions depending upon the position of 0 i.e empty boxes
                            if (i == 0) {                       
                                if (j == 0) {                   //when the 0 is in first row and first column
                                    if ((x == puzzle[i + 1][j]) || (x == puzzle[i][j + 1])) {       //only the integer that are in next right and next below to 0 is valid. 
                                        return x;                                                   //returns the valid input.
                                    }
                                } else if (j == 3) {
                                    if ((x == puzzle[i + 1][j]) || (x == puzzle[i][j - 1])) {           //when the 0 is in first row and fourth column
                                        return x;
                                    }
                                } else if ((x == puzzle[i + 1][j]) || (x == puzzle[i][j - 1]) || (x == puzzle[i][j + 1])) {   //when the 0 is in first row
                                    return x;
                                }
                            } else if (i == 3) {
                                if (j == 0) {
                                    if ((x == puzzle[i - 1][j]) || (x == puzzle[i][j + 1])) {
                                        return x;
                                    }
                                } else if (j == 3) {
                                    if ((x == puzzle[i - 1][j]) || (x == puzzle[i][j - 1])) {
                                        return x;
                                    }
                                } else if ((x == puzzle[i - 1][j]) || (x == puzzle[i][j - 1]) || (x == puzzle[i][j + 1])) {
                                    return x;
                                }
                            } else if (j == 0) {                                       //when the 0 is in first column
                                if ((x == puzzle[i - 1][j]) || (x == puzzle[i + 1][j]) || (x == puzzle[i][j + 1])) {
                                    return x;
                                }
                            } else if (j == 3) {                                        //when the 0 is in fourth column
                                if ((x == puzzle[i - 1][j]) || (x == puzzle[i + 1][j]) || (x == puzzle[i][j - 1])) {
                                    return x;
                                }
                            } else if ((x == puzzle[i + 1][j]) || (x == puzzle[i - 1][j]) || (x == puzzle[i][j + 1]) || (x == puzzle[i][j - 1])) {      //when the 0 is not in edge
                                return x;
                            }
                            System.out.println("Invalid number!!!\n");
                            continue;
                        }
                    }
                }
            } else {
                String s = in.next();       
                if (s.equals("q")) {        //if the user input in 'q', the program quits
                    System.out.println("Thank you for playing!!! Come back again.");
                    System.exit(0);
                }
                System.out.println("Invalid input!!!\n");
            }
        }
    }

    public static void print_game(int[][] puzzle) {                     //displays the current game situation in properly arranged boxes
        int i, j;
        String dash = "---------------------";
        System.out.println(dash);                                       //displays the top border as dashes
        for (i = 0; i < 4; i++) {
            System.out.print("|");
            for (j = 0; j < 4; j++) {
                if (puzzle[i][j] == 0) {                                //if the element of the matrix has 0 value, the box is made empty
                    System.out.print("    |");
                } else {
                    System.out.printf("*%2d*|", puzzle[i][j]);          //otherwise same number is entered.
                }
            }
            System.out.println("\n" + dash);
        }
        System.out.println();
    }

    public static int[][] game_situation(int[][] puzzle, int user_in) {     //stores the current game situation in the original 4*4 matrix     
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (puzzle[i][j] == 0) {                                    //swaps the value of user input integer and 0 so that user input integer is moved to empty box.
                    puzzle[i][j] = user_in;
                } else if (puzzle[i][j] == user_in) {                       
                    puzzle[i][j] = 0;
                }
            }
        }
        return puzzle;  
    }

    public static boolean game_logic(int[][] puzzle) {            //checks the winning condition of puzzle by comparing the current matrix with winning matrix
        int i, j;
        int k = 1;
        int[][] win = new int[4][4];
        for (i = 0; i < 4; i++) {                       //this is the winning matrix. numbers (1-15) are arranged in ascending order horizontally
            for (j = 0; j < 4; j++, k++) {
                win[i][j] = k;
            }
        }
        win[3][3] = 0;                                  //winning marix has the last box i.e. on the fourth row and the fourth column empty.
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++, k++) {
                if (puzzle[i][j] != win[i][j]) {                    //checks the current matrix with winning matrix element by element
                    return false;                                   //returns false if the criteria is not met
                }
            }
        }
        return true;
    }

    public static int[][] test() {                    //this is just to create the almost solved puzzle for testing purposes. this method is not required
        int i, j;
        int k = 1;
        int[][] res = new int[4][4];    
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++, k++) {              //creates the puzzle almost similar to winning matrix. only one move is required to win.
                res[i][j] = k;
            }
        }
        res[3][2] = 0;                      //the last 2 position are swapped. thats only difference between winning matrix and test matrix
        res[3][3] = 15;
        return res;
    }

    public static int[][] create_puzzle() {                 //creates the random puzzle
        Random r = new Random();
        ArrayList<Integer> num = new ArrayList<Integer>();      //this 1d array is used for storing all the number (0-15) for convenience
        int i, j;
        for (i = 0; i <= 15; i++) {
            num.add(i);
        }
        int[][] res = new int[4][4];
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                int x = r.nextInt(num.size());          //gives the random number less than the length of previously created 1d array
                res[i][j] = num.get(x);                 //stores the number corresponding to random number index of 1d array
                num.remove(num.get(x));                 //removes that number from 1d array so that next time same number wont be picked
            }
        }
        return res;                     //returns the randomly created puzzle
    }
}
