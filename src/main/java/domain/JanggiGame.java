package domain;

import domain.position.Point;
import domain.position.Position;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final Board board;

    public JanggiGame(final Board board) {
        this.board = board;
    }

    public void start() {
        boolean flag = true;
        while (true) {
            OutputView.printBoard(board);
            if (flag) {
                OutputView.printGreenTurn();
            } else {
                OutputView.printRedTurn();
            }
            final List<String> fromNumber = InputView.readFromPoint();
            final Point prevPoint = Point.of(fromNumber.getFirst(), fromNumber.getLast());
            final Position prevPosition = board.findPositionBy(prevPoint);
            if (flag && !prevPosition.isGreenTeam() || !flag && prevPosition.isGreenTeam()) {
                System.out.println("우리팀 말이 아닙니다. 턴이 종료되었습니다.\n");
                flag = !flag;
                continue;
            }
            final List<String> toNumber = InputView.readToPoint();
            final Point nextPoint = Point.of(toNumber.getFirst(), toNumber.getLast());

            if (prevPosition.isMovable(nextPoint)) {
                if (board.canMoveOnPath(prevPosition, nextPoint)) {
                    board.moveForEnd(prevPosition, nextPoint, OutputView::printCaptureMessage);
                    if (board.hasOnlyOneGeneral()) {
                        final Team team = board.determineWinTeam();
                        OutputView.printWinnerTeam(team);
                        break;
                    }
                }
            }
            flag = !flag;
        }
    }

    public boolean test(final int x, final int y) {
        return board.hasPieceAt(Point.of(x, y));
    }
}
