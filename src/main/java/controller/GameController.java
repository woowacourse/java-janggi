package controller;

import java.util.List;
import java.util.function.Supplier;
import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.board.strategy.ElephantSetup;
import model.move.Move;
import model.position.Position;
import view.InputView;
import view.OutputView;

public class GameController {

    public void start() {
        Board board = new Board();
        init(board);
        OutputView.printBoard(board);

        while (board.endCondition()) {
            choGamePhase(board);
            hanGamePhase(board);
        }

        endGamePhase(board);
    }

    private void init(Board board) {
        OutputView.printArrangeCountry(Country.CHO);
        Army cho = initArmy(Country.CHO);
        cho.deployTo(board, Country.CHO);
        OutputView.printLine();
        OutputView.printArrangeCountry(Country.HAN);
        Army han = initArmy(Country.HAN);
        han.deployTo(board, Country.HAN);
    }

    private Army initArmy(Country country) {
        OutputView.printArrangeList(ElephantSetup.arrangementList(), country);
        HorseElephantStrategy strategy = retry(() -> {
            int number = InputView.readArrangement();
            return ElephantSetup.init(number);
        });
        return new Army(strategy);
    }

    private void choGamePhase(Board board) {
        if (!board.endCondition()) {
            return ;
        }
        Country country = Country.CHO;
        OutputView.printPositionCountry(Country.CHO);
        gamePhase(board, country);
    }

    private void hanGamePhase(Board board) {
        if (!board.endCondition()) {
            return ;
        }
        Country country = Country.HAN;
        OutputView.printPositionCountry(Country.HAN);
        gamePhase(board, country);
    }

    private void gamePhase(Board board, Country country) {
        retry(() -> gamePhaseRetry(board, country));
        OutputView.printBoard(board);
    }

    private void gamePhaseRetry(Board board, Country country) {
        Position from = selectStartPosition();
        board.checkTurn(from, country);
        Position to = selectEndPosition();
        Move move = new Move(from, to);
        board.move(move);
    }

    private Position selectStartPosition() {
        List<Integer> startList = InputView.readStartPosition();
        return Position.of(startList.get(0), startList.get(1));
    }

    private Position selectEndPosition() {
        List<Integer> startList = InputView.readEndPosition();
        return Position.of(startList.get(0), startList.get(1));
    }

    private void endGamePhase(Board board) {
        board.winnerCountry().ifPresent(OutputView::printWinner);
        OutputView.printScore(Country.CHO, board.sumScore(Country.CHO));
        OutputView.printScore(Country.HAN, board.sumScore(Country.HAN));
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void retry(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
