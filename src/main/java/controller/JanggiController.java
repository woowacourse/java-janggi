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

            // TODO command 받고 -> position 변환 이거 하나로 묶을 수 없는지 시도하기. depth 1로.
            String command = inputView.readCommand(currentTurn);
            if (command.equals("exit")) {
                outputView.printGameFinishedByCommand();
                return;
            }

            Intersection startPosition;
            try {
                startPosition = Intersection.parse(command);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                continue;
            }

            outputView.printBoardWithMovable(board, board.getMovableIntersections(startPosition, currentTurn));

            readValidDestinationAndPrintBoard(janggiGame, board, startPosition, currentTurn);
        }

        outputView.printWinner(janggiGame.determineResult());
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
}
