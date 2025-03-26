package game;

import board.MemoryGameBoard;
import direction.Point;
import team.Player;
import team.Team;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private static final String RANGE_EXCEED = "[ERROR] 범위를 넘어설 수 없습니다.";
    private static final int HORIZONTAL_START = 1;
    private static final int HORIZONTAL_END = 9;
    private static final int VERTICAL_START = 1;
    private static final int VERTICAL_END = 10;

    private final MemoryGameBoard gameBoard;

    public JanggiGame(MemoryGameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void showInitialBoard() {
        OutputView.displayBoard(gameBoard);
    }

    public void run() {
        for (Team team : Team.values()) {
            Player currentPlayer = gameBoard.findPlayer(team);

            Point start = requestMovementStartPosition(currentPlayer);
            Point end = requestMovementEndPosition();

            currentPlayer.validateAlreadyPlayerPieceInPosition(end);

            currentPlayer.play(gameBoard.findAllPieces(), start, end);
            OutputView.displayBoard(gameBoard);
        }
    }

    private Point requestMovementStartPosition(Player player) {
        while (true) {
            Point start = InputView.requestMoveStartPosition();
            validateBoardRange(start);

            if (player.isContainPiece(start)) {
                return start;
            }
            OutputView.displayWrongPoint();
        }
    }

    private Point requestMovementEndPosition() {
        Point end = InputView.requestMovementEndPosition();
        validateBoardRange(end);
        return end;
    }

    public void validateBoardRange(Point point) {
        if (point.y() < VERTICAL_START || point.y() > VERTICAL_END ||
                point.x() < HORIZONTAL_START || point.x() > HORIZONTAL_END) {
            throw new IllegalArgumentException(RANGE_EXCEED);
        }
    }
}
