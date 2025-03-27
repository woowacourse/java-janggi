package game;

import board.GameBoard;
import direction.Point;
import java.util.Objects;
import team.Player;
import team.Team;
import view.InputView;
import view.OutputView;

public class JanggiGame {
    private static final int HORIZONTAL_START = 1;
    private static final int HORIZONTAL_END = 9;
    private static final int VERTICAL_START = 1;
    private static final int VERTICAL_END = 10;

    private final GameBoard gameBoard;

    public JanggiGame(final GameBoard gameBoard) {
        if (Objects.isNull(gameBoard)) {
            throw new IllegalArgumentException("[ERROR] GameBoard는 null이 될 수 없습니다.");
        }
        this.gameBoard = gameBoard;
    }

    public void run() {
        Team currentTeam = Team.getFirstTurnTeam();
        while (true) {
            OutputView.printBoard(gameBoard);
            Player player = gameBoard.findPlayer(currentTeam);
            try {
                Point start = requestMoveStartPosition(player);
                Point end = requestMoveEndPosition(start);

                player.move(gameBoard.findAllPieces(), start, end);
                currentTeam = currentTeam.oppsite();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private Point requestMoveStartPosition(Player player) {
        Point start = InputView.requestMoveStartPosition();
        validateBoardRange(start);

        while (!player.isContainPiece(start)) {
            OutputView.displayWrongPoint();
            start = InputView.requestMoveStartPosition();
            validateBoardRange(start);
        }

        return start;
    }

    private Point requestMoveEndPosition(Point start) {
        Point end = InputView.requestMoveEndPosition();
        validateBoardRange(end);
        validateStartSameDestination(start, end);

        return end;
    }

    private void validateStartSameDestination(Point start, Point end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException("[ERROR] 원래 위치를 선택할 수 없습니다.");
        }
    }

    public void validateBoardRange(Point point) {
        if (isVerticalOutOfRange(point) || isHorizontalOutOfRange(point)) {
            throw new IllegalArgumentException("[ERROR] 장기판 범위를 넘어설 수 없습니다.");
        }
    }

    private boolean isHorizontalOutOfRange(Point point) {
        return point.column() < HORIZONTAL_START || point.column() > HORIZONTAL_END;
    }

    private boolean isVerticalOutOfRange(Point point) {
        return point.row() < VERTICAL_START || point.row() > VERTICAL_END;
    }
}
