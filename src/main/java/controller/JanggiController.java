package controller;

import domain.board.Board;
import domain.board.BoardInitializer;
import domain.Position;
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

        while (true) {
            outputView.printBoard(board.getBoard());
            Position startPosition = RetryInput.read(() -> getStartPosition(board));

            List<Position> possibleMoves = board.getPieceBy(startPosition).getPossibleMoves(board, startPosition);
            if (isPossibleMovePiece(possibleMoves)) {
                continue;
            }
            outputView.printAvailablePositions(possibleMoves);

            Position destination = RetryInput.read(() -> getDestination(possibleMoves));

            board.move(startPosition, destination);
        }
    }

    private Position getStartPosition(Board board) {
        Position position = inputView.requestStartPiecePosition(board.getTurn());
        board.validateStartPosition(position);
        return position;
    }

    private boolean isPossibleMovePiece(List<Position> possibleMoves) {
        int possibleMovesCount = possibleMoves.size();
        if (possibleMovesCount == 0) {
            outputView.printCanNotMovablePieceError();
            return false;
        }
        return true;
    }

    private Position getDestination(List<Position> possibleMoves) {
        int possibleMovesCount = possibleMoves.size();
        int index = inputView.requestPieceDestination();
        if (index > possibleMovesCount) {
            throw new IllegalArgumentException("번호 중에 선택하세요.");
        }
        return possibleMoves.get(index - 1);
    }
}
