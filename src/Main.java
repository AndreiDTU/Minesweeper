import Controller.Parser;
import Controller.Controller;
import Model.Board;
import Model.Move;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();

        try(Scanner sc = new Scanner(System.in)) {
            String command;
            Move move;

            System.out.println(board);
            do {
                command = sc.nextLine();
            } while (!Parser.validateMove(command));
            move = Parser.parseMove(command);
            while (!Controller.make(move, board)) {
                board = new Board();
            }

            do {
                System.out.println(board);
                do {
                    command = sc.nextLine();
                } while (!Parser.validateMove(command));
                move = Parser.parseMove(command);
            } while(Controller.make(move, board) && !board.win());
        }

        System.out.println(board);
        System.out.println(board.win() ? "You win!" : "Game Over!");
    }
}