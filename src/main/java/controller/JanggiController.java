package controller;

import domain.board.Board;
import domain.board.InitialPieces;
import domain.board.Intersection;
import domain.board.wing.Wings;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import java.util.LinkedHashMap;
import java.util.Map;
import view.InputView;
import view.OutputView;

public final class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printGameStart();

        InitialPieces initialPieces = setUpInitialPieces();
        AlivePieces alivePieces = initialPieces.toAlivePieces();
        Board board = new Board(alivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printBoard(board);

        while (!janggiGame.isFinished()) {
            Side currentTurn = janggiGame.currentTurn();

            Intersection startPosition = readValidStartPositionAndPrintBoard(currentTurn, board);

            readValidDestinationAndPrintBoard(janggiGame, board, startPosition, currentTurn);
        }

        outputView.printWinner(
                calculateTotalPointBySide(janggiGame),
                findWinner(janggiGame)
        );
    }

    private InitialPieces setUpInitialPieces() {
        Wings choWings = readValidWings(Side.CHO);
        Wings hanWings = readValidWings(Side.HAN);

        return new InitialPieces(hanWings, choWings);
    }

    private Wings readValidWings(Side side) {
        while (true) {
            try {
                return inputView.readWings(side);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Intersection readValidStartPositionAndPrintBoard(Side currentTurn, Board board) {
        while (true) {
            try {
                Intersection startPosition = inputView.readStartPosition(currentTurn);
                outputView.printBoardWithMovable(board, board.getMovableIntersections(startPosition, currentTurn));

                return startPosition;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Intersection readValidDestinationAndPrintBoard(
            JanggiGame janggiGame,
            Board board,
            Intersection startPosition,
            Side currentTurn
    ) {
        while (true) {
            try {
                Intersection destination = inputView.readDestination();
                janggiGame.movePiece(startPosition, destination, currentTurn);
                outputView.printBoard(board);

                return destination;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Map<Side, Double> calculateTotalPointBySide(JanggiGame janggiGame) {
        Map<Side, Double> totalPointBySide = new LinkedHashMap<>();
        final Side cho = Side.CHO;
        final Side han = Side.HAN;

        totalPointBySide.put(cho, janggiGame.calculatePointOf(cho));
        totalPointBySide.put(han, janggiGame.calculatePointOf(han));

        return totalPointBySide;
    }

    private String findWinner(JanggiGame janggiGame) {
        Map<Side, Double> totalPointBySide = calculateTotalPointBySide(janggiGame);
        final Side cho = Side.CHO;
        final Side han = Side.HAN;

        String winner = cho.name();

        if (totalPointBySide.get(cho) < totalPointBySide.get(han)) {
            winner = han.name();
        }

        return winner;
    }
}
