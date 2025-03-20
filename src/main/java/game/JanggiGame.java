package game;

import board.Board;
import direction.Point;
import team.Player;
import team.Team;
import view.InputView;
import view.OutputView;

public class JanggiGame {
    private final Board board;

    public JanggiGame(Board board) {
        this.board = board;
    }

    public void showInitialBoard() {
        OutputView.displayBoard(board);
    }

    public void run() {
        for (Team team : Team.values()) {
            Player player = board.findPlayer(team);

            Point start;
            while (true) {
                start = InputView.requestMoveStartPosition();
                validateBoardRange(start);

                if (player.isContainPiece(start)) {
                    break;
                }
                OutputView.displayWrongPoint();
            }

            Point end = InputView.requestMoveEndPosition();
            validateBoardRange(end);
            player.move(board.findAll(), start, end);
            OutputView.displayBoard(board);
        }
    }

    public void validateBoardRange(Point point) {
        if (point.y() < 1 || point.y() > 10 || point.x() < 1 || point.x() > 9) {
            throw new IllegalArgumentException("[ERROR] 범위를 넘어설 수 없습니다.");
        }
    }
}
