/*
* This program is the Tic Tac Toe program.
* Got help from https://youtu.be/trKjYdBASyQ,
* from the Youtube channel "The Coding Train"
*
* @author  Julie Nguyen & Mr. Coxall & Sean McLeod
* @version 1.0
* @since   2021-05-15
*/

import java.util.Scanner;

public final class TicTacToe {
    private TicTacToe() {
        // Prevent instantiation
        // Optional: throw an exception e.g. AssertionError
        // if this ever *is* called
        throw new IllegalStateException("Cannot be instantiated");
    }

    /** The third position of the TicTacToe format. */
    private static final int THREE = 3;
    /** The fourth position of the TicTacToe format. */
    public static final int FOUR = 4;
    /** The fifth position of the TicTacToe format. */
    public static final int FIVE = 5;
    /** The sixth position of the TicTacToe format. */
    private static final int SIX = 6;
    /** The seventh position of the TicTacToe format. */
    public static final int SEVEN = 7;
    /** The eighth position of the TicTacToe format. */
    public static final int EIGHT = 8;


    /**
    * This function gets input and calls functions
    * to organize the whole program.
    * @param args
    */
    public static void main(final String[] args) {
        // main stub, get user input here
        boolean boardFull = false;
        boolean checkWinnerX = false;
        boolean checkWinnerO = false;

        Scanner input = new Scanner(System.in);
        String[] board = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        System.out.println("Welcome to tic tac toe!");

        do {
            printBoard(board);
            System.out.print("\nWhich space would you like to put the X? ");
            if (input.hasNextInt()) {
                int space = input.nextInt();

                if (space > board.length || space < 0) {
                    System.out.println("That spot is invalid!");
                } else if (board[space - 1].equalsIgnoreCase("X")
                           || board[space - 1].equalsIgnoreCase("O")) {
                    System.out.println("That spot's taken!");
                } else if (isNumeric(board[space - 1])) {
                    board[space - 1] = "X";
                    // check for "X"
                    checkWinnerX = winOrLost(board, "X");
                    if (checkWinnerX) {
                        System.out.println("\nX has won.");
                        printBoard(board);
                        break;
                    }
                    // place a function call here to get the best move for O
                    if (!isFull(board)) {
                        int goodComputerMove = bestMove(board);
                        board[goodComputerMove] = "O";
                        // check for "O"
                        checkWinnerO = winOrLost(board, "O");
                        if (checkWinnerO) {
                            System.out.println("\nO has won.");
                            printBoard(board);
                            break;
                        }
                    } else {
                        System.out.println("\nTie.");
                        printBoard(board);
                    }
                } else {
                    System.out.println("Error");
                    break;
                }
            } else {
                System.out.println("\nPlease enter an integer");
                break;
            }
            boardFull = isFull(board);
        } while (!boardFull);

        System.out.println("\nGame Over.");
    }

    /**
    * This function figures out whether
    * or not the player won.
    * @param board
    * @param playerCheck
    * @return boolean of won or lost
    */
    public static boolean winOrLost(final String[] board,
                                    final String playerCheck) {
        return (board[0] == playerCheck && board[1] == playerCheck
                                        && board[2] == playerCheck)
            || (board[THREE] == playerCheck
                && board[FOUR] == playerCheck && board[FIVE] == playerCheck)
            || (board[SIX] == playerCheck
                && board[SEVEN] == playerCheck && board[EIGHT] == playerCheck)
            || (board[0] == playerCheck
                && board[THREE] == playerCheck && board[SIX] == playerCheck)
            || (board[1] == playerCheck
                && board[FOUR] == playerCheck && board[SEVEN] == playerCheck)
            || (board[2] == playerCheck
                && board[FIVE] == playerCheck && board[EIGHT] == playerCheck)
            || (board[0] == playerCheck
                && board[FOUR] == playerCheck && board[EIGHT] == playerCheck)
            || (board[2] == playerCheck
                && board[FOUR] == playerCheck && board[SIX] == playerCheck);
    }

    /**
    * This function figures out the
    * best Move for the AI player.
    * @param currentBoard
    * @return move for the AI
    */
    public static int bestMove(final String[] currentBoard) {
        // AI's turn
        double bestScore = Double.NEGATIVE_INFINITY;
        int move = -1;

        if (isNumeric(currentBoard[FOUR])) {
            // Always take middle if possible
            return FOUR;

        } else {
            for (int item = 0; item < currentBoard.length; item++) {
                // accessing each element of array
                if (isNumeric(currentBoard[item])) {
                    // this means the spot is free
                    currentBoard[item] = "O";
                    double score = miniMax(currentBoard, false);
                    currentBoard[item] = String.valueOf(item + 1);
                    if (score > bestScore) {
                        bestScore = score;
                        move = item;
                    }
                }
            }
        }
        return move;
    }

    /**
    * This function is the
    * MiniMax algorithm.
    * @param liveBoard
    * @param isMaximizing
    * @return bestScore
    */
    public static double miniMax(final String[] liveBoard,
                                 final boolean isMaximizing) {
        String symbol;
        String[] newBoard = liveBoard;
        double bestScore;

        if (winOrLost(newBoard, "X")) {
            return -1;
        } else if (winOrLost(newBoard, "O")) {
            return 1;
        } else if (isFull(newBoard)) {
            return 0;
        }

        if (!isMaximizing) {
            bestScore = Double.POSITIVE_INFINITY;
            symbol = "X";
        } else {
            bestScore = Double.NEGATIVE_INFINITY;
            symbol = "O";
        }

        for (int item = 0; item < newBoard.length; item++) {
            // accessing each element of array
            if (isNumeric(newBoard[item])) {
                // this means the spot is free
                newBoard[item] = symbol;
                double score = miniMax(newBoard, !(isMaximizing));
                newBoard[item] = String.valueOf(item + 1);
                if (isMaximizing) {
                    if (score > bestScore) {
                        bestScore = score;
                    }
                } else if (score < bestScore) {
                    bestScore = score;
                }
            }
        }
        return bestScore;
    }

    /**
    * This function checks
    * if the board is full.
    * @param presentBoard
    * @return boolean of full or not
    */
    public static boolean isFull(final String[] presentBoard) {
        boolean full = true;
        for (int counter = 0; counter < presentBoard.length; counter++) {
            if (isNumeric(presentBoard[counter])) {
                full = false;
                break;
            }
        }
        return full;
    }

    /**
    * This function prints
    * out the board for TicTacToe.
    * @param theBoard
    */
    public static void printBoard(final String[] theBoard) {
        System.out.println("\n----+----+----");
        for (int count = 0; count < theBoard.length; count++) {
            if (count % THREE - 2 == 0) {
                System.out.print("| " + theBoard[count] + " |\n");
                System.out.println("----+----+----");
            } else {
                System.out.print("| " + theBoard[count] + " ");
            }
        }
    }

    /**
    * This function checks if there
    * is a spot for the players.
    * @param strNum
    * @return boolean of free or not
    */
    public static boolean isNumeric(final String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
