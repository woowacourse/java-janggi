package controller;

import java.awt.*;
import java.util.List;

import model.board.*;
import model.move.Move;
import model.position.Position;
import view.InputHandler;
import view.InputView;
import view.OutputView;

public class GameController {
    private final BoardInitializer boardInitializer;

    public GameController(BoardInitializer boardInitializer) {
        this.boardInitializer = boardInitializer;
    }

    public void start() {
        Board board = new Board();
        init(board);
        OutputView.printBoard(board);

        while (true) {
            choGamePhase(board);
            hanGamePhase(board);
        }
    }

    private void init(Board board){
        ArrangementType choType = readArrangementType(Country.CHO);
        OutputView.printLine();
        ArrangementType hanType = readArrangementType(Country.HAN);

        boardInitializer.initialize(board, choType, hanType);
    }

    private ArrangementType readArrangementType(Country country){
        OutputView.printArrangeCountry(country);
        return InputHandler.retry(() ->
                ArrangementType.from(InputView.readArrangement(country)));
    }

    private void choGamePhase(Board board) {
        OutputView.printPositionCountry(Country.CHO);
        gamePhase(board, Country.CHO);
    }

    private void hanGamePhase(Board board) {
        OutputView.printPositionCountry(Country.HAN);
        gamePhase(board, Country.HAN);
    }

    private void gamePhase(Board board, Country country) {

        InputHandler.retry(() -> {
            List<Integer> startList = InputView.readStartPosition();
            List<Integer> endList = InputView.readEndPosition();
            Position from = Position.of(startList.get(0), startList.get(1));
            Position to = Position.of(endList.get(0), endList.get(1));
            Move move = new Move(from, to);
            board.move(country, move);
            OutputView.printBoard(board);
            return null;
        });
    }
}
