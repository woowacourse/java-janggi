package controller;

import domain.board.Board;
import domain.board.InitialPieces;
import domain.board.wing.ChoWings;
import domain.board.wing.HanWings;
import domain.game.JanggiGame;
import domain.piece.AlivePieces;
import dto.BoardDto;
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

        ChoWings choWings = inputView.readChoWings();
        HanWings hanWings = inputView.readHanWings();

        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);
        AlivePieces alivePieces = new AlivePieces(initialPieces.get());
        Board board = new Board(alivePieces);
        JanggiGame janggiGame = new JanggiGame(board);

        outputView.printBoard(board);
    }
}
