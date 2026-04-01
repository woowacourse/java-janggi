package controller;

import domain.Game;
import domain.board.BoardInitializer;
import domain.coordinate.Position;
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
        Game game = new Game(boardInitializer);

        while (true) {
            outputView.printBoard(game.toSnapshot());
            Position startPosition = RetryInput.read(() -> getStartPosition(game));

            List<Position> possibleMoves = game.getPossibleMoves(startPosition);
            if (!isPossibleMovePiece(possibleMoves)) {
                continue;
            }

            outputView.printAvailablePositions(possibleMoves);
            Position destination = RetryInput.read(() -> getDestination(possibleMoves));
            game.move(startPosition, destination);
        }
    }

    private Position getStartPosition(Game game) {
        Position position = inputView.requestStartPiecePosition(game.getTurn());
        game.validateStartPosition(position);
        return position;
    }

    private boolean isPossibleMovePiece(List<Position> possibleMoves) {
        if (possibleMoves.isEmpty()) {
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
