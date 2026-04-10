package controller;

import domain.Game;
import domain.Side;
import domain.coordinate.Position;
import service.JanggiService;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void play() {
        Game game = janggiService.initializeGame();

        while (true) {
            outputView.printBoard(game.getBoardSnapshot());
            Position startPosition = RetryInput.read(() -> getStartPosition(game));

            List<Position> possibleMoves = game.getPossibleMoves(startPosition);
            if (!isPossibleMovePiece(possibleMoves)) {
                continue;
            }

            outputView.printAvailablePositions(possibleMoves);
            Position destination = RetryInput.read(() -> getDestination(possibleMoves));
            janggiService.movePiece(game, startPosition, destination);
            if (checkGameOver(game)) {
                break;
            }
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

    private boolean checkGameOver(Game game) {
        if (game.isGameOver()) {
            outputView.printBoard(game.getBoardSnapshot());
            outputView.printWinner(game.getWinner());
            outputView.printEachScores(game.calculateScore(Side.HAN), game.calculateScore(Side.CHU));
            janggiService.resetGame();
            return true;
        }
        return false;
    }
}