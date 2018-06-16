/*
Roshan Shrestha
This is a very primitive TicTacToe game. 2 player plays this game. It takes the certain integer input which is position from the player and the position
is displayed in the remaining position box. Player 1 has 'o' and player 2 has '+'. 
The logic behind this game is storing the user inputed data in the form of 3*3 matrix and checking the different value in matrix to determine the current
game situation.
 */
import java.util.Scanner;

public class Tictactoe {

    public static void print(int[][] picked) {              //create 3*3 box with dashes and line to show moves and remaining position
        String dash = "-------------";
        String space = "               ";
        int i, j;
        System.out.println("Current game" + space + "Remaining position");
        System.out.println(dash + space + dash);                        //builds the top dashes of box
        for (i = 0; i <= 2; i++) {
            System.out.print("|");
            for (j = 0; j <= 2; j++) {
                System.out.print((picked[i][j] == 0 ? "   |" : (picked[i][j] > 0) ? " o |" : " + |"));   //prints 'o' if it's player 1 turn otherwise '+' in the user inputed position
            }
            System.out.print(space + "|");
            for (j = 0; j <= 2; j++) {
                System.out.print((picked[i][j] == 0 ? " " + (i + 1) + (j + 1) + "|" : "   |"));  //prints the position of remaining and prints blank if the position is already inputed
            }
            System.out.println();
            System.out.println(dash + space + dash);
        }
    }

    public static boolean check(int choice, int[][] picked) {                   //checks if the user inputed position is valid or not i.e.
        int r = (choice / 10) - 1;                                              //extract row number and colum number of matrix from the user inputed choice
        int c = (choice % 10) - 1;                                    //subtracting 1 just to match with correct index of matrix
        if ((r <= 2) && (r >= 0) && (c <= 2) && (c >= 0)) {                     //checks if r and c is between 0 and 2(index of 3*3 matrix) inclusive
            return picked[r][c] == 0;                                           //returns true if the user inputed position has not been inputed before;it is done by checking whether the value of that position is 0 or not.
        } else {
            return false;
        }
    }

    public static int[][] toMatrix(int choice, int[][] picked, int player) {     //stores all user inputed position in 3*3 matrix.
        int r = (choice / 10) - 1;                                               //extract row number and colum number of matrix from the user inputed choice
        int c = (choice % 10) - 1;
        if (player == 1) {                                                       //player 1 position is indicated by 1 and player 2 position is indicated by -1.
            picked[r][c] = 1;                                                    //if the position is not taken. it is indicated by 0.
        } else {
            picked[r][c] = -1;
        }
        return picked;
    }

    public static int game_logic(int[][] picked) {                                  //it checks the winning criteria of game by looking the value of each position of 3*3 matrix.
        // returns 1 if player 1 completes the critera, returns 2 if player 2 completed the criteria. returns 0 if the winning criteria is still not completed.
        int opt = 0;                                       //opt checks horizontally
        int opt2 = 0;                                       //opt2 checks vertically
        int opt3 = 0;                                       //opt3 and opt4 checks diagonally
        int opt4 = 0;
        int i, j;
        opt3 = picked[0][0] + picked[1][1] + picked[2][2];          //sum diagonal elements of matrix
        opt4 = picked[2][0] + picked[1][1] + picked[0][2];
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                opt = opt + picked[i][j];                           //sum of horizontal elements of matrix
                opt2 = opt2 + picked[j][i];                         //sum of vertical elements of matrix
            }
            if (opt == 3 || opt2 == 3 || opt3 == 3 || opt4 == 3) {      //if any of the sum is 3 then returns 1; player 1 won
                return 1;
            } else if (opt == -3 || opt2 == -3 || opt3 == -3 || opt4 == -3) {   //if any of the sum is -3 then returns 2; player 2 won
                return 2;
            }
            opt = opt2 = 0;
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int i, player, game;                //i is for loop. player denotes the player turn. game denotes if player has won or not.
        int[][] picked = new int[3][3];     //initializing new 3*3 matrix to store inputed position of player
        System.out.println("-------------------TICTACTOE-------------------");             //Game description
        System.out.println("Player 1 is denoted by 'o' and Player 2 is denoted by '+'.");
        System.out.println("Enter your choice of position by looking at the remaining position box. For e.g. 12 gives the 1st row and 2nd column.");
        System.out.println("GOOD LUCK!!!");
        print(picked);
        for (i = 1; i <= 9;) {              //loop runs for 9 times because there is maximum of 9 turns in tictactoe
            if (i % 2 == 0) {               //if i is odd, its player 1 turn and if i is even its player 2 turn.
                player = 2;
            } else {
                player = 1;
            }
            System.out.println();
            System.out.println("Turn " + i + "(Player " + player + ")");   //prints the turn number and player turn
            System.out.print("Pick your position or q to quit: ");
            if (in.hasNextInt()) {                                          //checks if user input is integer or not
                int choice = in.nextInt();
                if (check(choice, picked)) {                                //calls check method to check whether the user inputed position is valid or not
                    picked = toMatrix(choice, picked, player);              //stores the inputed position in 3*3 matrix. 
                    i++;                                                    //just to proceed the turn number
                    print(picked);                                          //prints the current game situation and remaining position in a 3*3 box
                    game = game_logic(picked);                              //checks if anyone has won the game or not
                    switch (game) {
                        case 1:                                             //if player 1 wins the game, game is 1. so it prints congrats statement and ends the program
                            System.out.println("Congratulation!!!Player 1 won the game.\nThank you for playing!");
                            System.exit(0);
                        case 2:                                             //for player 2.
                            System.out.println("Congratulation!!!Player 2 won the game.\nThank you for playing!");
                            System.exit(0);
                    }
                } else {                                                //for invalid user inputed position, runs loop again with no increment in i(turn number)
                    System.out.println("Invalid position!!!");
                    print(picked);
                }
            } else {                                                            //if user input is other than integer, this wing activates
                String other = in.next();
                if (other.equals("q")) {                                        //q quits the program by printing loser.
                    System.out.println("You are loser. Player " + ((player == 1) ? "2" : "1") + " wins.");
                    System.exit(0);
                } else {                                                        //if user input is other than integer and q, runs loop again with no increment in i(turn number)
                    System.out.println("Input not recognized!!!");
                    print(picked);
                }
            }
        }
        System.out.println("Game Tied!!!");
    }
}
