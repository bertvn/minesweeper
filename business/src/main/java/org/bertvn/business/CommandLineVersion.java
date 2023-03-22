package org.bertvn.business;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * temporary interface for playing the game
 */
public class CommandLineVersion {

    public static final Pattern CHECK_FLAG_PATTERN = Pattern.compile("(?:check|surround|flag)");

    public static void main(String[] args) {

        GameHandler gameHandler = new GameHandler();
        gameHandler.changeGameBoard(10, 10, 20);
        // Enter data using BufferReader
        Scanner reader = new Scanner(System.in);

        // Reading data using readLine
        System.out.println("choose a row and column");
        int row = reader.nextInt();
        int column = reader.nextInt();

        gameHandler.clearCell(row, column);

        while(true) {
            gameHandler.printBoard();
            System.out.println("check, surround or flag");
            String action = reader.next(CHECK_FLAG_PATTERN);
            boolean safe = switch(action) {
                case "check" -> checkAction(gameHandler, reader);
                case "surround" -> surroundAction(gameHandler, reader);
                case "flag" -> flagAction(gameHandler, reader);
                default -> true;
            };

            if(!safe) {
                System.out.println("you lost");
                break;
            }

            if(gameHandler.isCompleted()){
                System.out.println("you won");
                break;
            }
        }
    }

    private static boolean checkAction(GameHandler gameHandler, Scanner reader) {
        System.out.println("choose a row and column");
        int row2 = reader.nextInt();
        int column2 = reader.nextInt();

        return gameHandler.clearCell(row2, column2);
    }

    private static boolean surroundAction(GameHandler gameHandler, Scanner reader) {
        System.out.println("choose a row and column");
        int row2 = reader.nextInt();
        int column2 = reader.nextInt();

        return gameHandler.clearSurrounding(row2, column2);
    }

    private static boolean flagAction(GameHandler gameHandler, Scanner reader) {
        System.out.println("choose a row and column");
        int row2 = reader.nextInt();
        int column2 = reader.nextInt();

        gameHandler.flagCell(row2, column2);
        return true;
    }
}
