
package controller;

import domain.board.Board;
import domain.board.Position;
import view.InputView;
import view.OutputView;

public class GameController {
    public void move(Board board) {
        while (true) {
            try {
                Position departure = parsePosition(InputView.readDeparturePosition());
                Position destination = parsePosition(InputView.readDestinationPosition());
                board.move(departure, destination);
                return;
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception.getMessage());
            }
        }
    }

    public void printBoard(Board board) {
        OutputView.printBoard(board);
    }

    public void printWinner(Board board) {
        OutputView.printWinner(board.winner());
    }

    private Position parsePosition(String value) {
        String[] tokens = value.split(",");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("좌표는 x,y 형식으로 입력해야 합니다.");
        }

        try {
            int column = Integer.parseInt(tokens[0].trim());
            int row = Integer.parseInt(tokens[1].trim());
            return new Position(column, row);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("좌표는 숫자로 입력해야 합니다.");
        }
    }
}
