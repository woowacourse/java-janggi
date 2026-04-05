package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.util.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    public void run() {
        Board board = new Board(new BasicPlacementStrategy());

        while (!board.gameEnd()) {
            try {
                Position fromPosition = readPosition();
                Position toPosition = readPosition();

                board.move(fromPosition, toPosition);
                OutputView.printBoard(board);
                OutputView.printTeamScore(board);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private Position readPosition() {
        while (true) {
            try {
                String rawInput = InputView.askFromPosition();
                List<String> positionValues = InputParser.splitByDelimiter(rawInput);
                return Position.from(positionValues);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
