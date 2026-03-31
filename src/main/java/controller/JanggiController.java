package controller;

import domain.board.Board;
import domain.board.InitialPieces;
import domain.board.Intersection;
import domain.board.wing.Wings;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import view.InputView;
import view.OutputView;

public final class JanggiController {

    private static final String INVALID_POSITION_ERROR_MESSAGE =
            "(1,1)에서 (10,9) 사이의 유효한 좌표를 입력해주세요.(이전 입력: %d,%d)";

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printGameStart();

        InitialPieces initialPieces = setUpInitialPieces();
        AlivePieces alivePieces = new AlivePieces(initialPieces.toMap());
        Board board = new Board(alivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printBoard(board);

        while (true) {
            Side currentTurn = janggiGame.currentTurn();

            Intersection startPosition = readValidStartPositionAndPrintBoard(currentTurn, board);

            readValidDestinationAndPrintBoard(janggiGame, board, startPosition, currentTurn);
        }
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
            Side currentTurn) {
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
}
