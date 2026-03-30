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

        Wings choWings = inputView.readWings(Side.CHO);
        Wings hanWings = inputView.readWings(Side.HAN);

        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);
        AlivePieces alivePieces = new AlivePieces(initialPieces.get());
        Board board = new Board(alivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printBoard(board);

        while (true) {
            Side currentTurn = janggiGame.currentTurn();
            Intersection startPosition = inputView.readStartPosition(currentTurn);
            outputView.printBoardWithMovable(
                    board,
                    board.getMovableIntersections(startPosition, currentTurn)
            );

            Intersection destination = inputView.readDestination();
            janggiGame.movePiece(startPosition, destination, currentTurn);
            outputView.printBoard(board);
        }
    }
}
