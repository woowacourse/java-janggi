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
            // startPosition 입력 + 도메인 검증까지 묶어서 retry
            Position startPosition = RetryInput.read(() -> {
                Position position = inputView.requestStartPiecePosition(board.getTurn());
                board.getPieceBy(position); // 도메인 검증 포함
                return position;
            });

            List<Position> possibleMoves = board.getPieceBy(startPosition).getPossibleMoves(board, startPosition);
            int possibleMovesCount = possibleMoves.size();
            if (possibleMovesCount == 0) {
                outputView.printCanNotMovablePieceError();
                continue;
            }
            outputView.printAvailablePositions(possibleMoves);

            // destination도 별도로 retry
            Position destination = RetryInput.read(() -> {
                int index = inputView.requestPieceDestination();
                if (index > possibleMovesCount) {
                    throw new IllegalArgumentException("번호 중에 선택하세요.");
                }
                return possibleMoves.get(index - 1);
            });

            board.move(startPosition, destination);
        }
    }
}
