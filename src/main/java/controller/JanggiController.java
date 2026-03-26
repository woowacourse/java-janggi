package controller;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.Position;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardInitializer boardInitializer;

    public JanggiController(InputView inputView, OutputView outputView, BoardInitializer boardInitializer) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardInitializer = boardInitializer;
    }

    public void play() {
        Board board = new Board(boardInitializer);
        outputView.printBoard(board.getBoard());
        Position startPosition = inputView.requestPiecePosition();
        outputView.printAvailablePositions(board.getPieceBy(startPosition).getPossibleMoves(board, startPosition));
    }
}
