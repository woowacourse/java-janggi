package domain;

import domain.position.Point;
import domain.position.Position;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final Board board;
    private Team turn = Team.GREEN;

    public JanggiGame(final Board board) {
        this.board = board;
    }

    public void start() {
        while (true) {
            OutputView.printBoard(board);
            printTurn();

            final Position prevPosition = readStartPosition();
            if (isValidPiece(prevPosition)) {
                OutputView.printEndTurn();
                changeTurn();
                continue;
            }

            final Point nextPoint = readEndPoint();
            if (isInvalidEndPoint(prevPosition, nextPoint)) {
                changeTurn();
                continue;
            }

            board.move(prevPosition, nextPoint, OutputView::printCaptureMessage);
            if (board.hasOnlyOneGeneral()) {
                final Team team = board.determineWinTeam();
                OutputView.printWinnerTeam(team);
                break;
            }
        }
    }

    private void printTurn() {
        if (isGreenTurn()) {
            OutputView.printGreenTurn();
            return;
        }
        OutputView.printRedTurn();
    }

    private Position readStartPosition() {
        final List<String> fromNumber = InputView.readFromPoint();
        final Point prevPoint = Point.of(fromNumber.getFirst(), fromNumber.getLast());
        return board.findPositionBy(prevPoint);
    }

    private boolean isValidPiece(final Position prevPosition) {
        return isGreenTurn() && !prevPosition.isGreenTeam() || isRedTurn() && prevPosition.isGreenTeam();
    }

    private void changeTurn() {
        if (turn == Team.GREEN) {
            turn = Team.RED;
            return;
        }
        turn = Team.GREEN;
    }

    private Point readEndPoint() {
        final List<String> toNumber = InputView.readToPoint();
        return Point.of(toNumber.getFirst(), toNumber.getLast());
    }

    private boolean isInvalidEndPoint(final Position prevPosition, final Point nextPoint) {
        return !prevPosition.isMovableTo(nextPoint) || !board.canMoveOnPath(prevPosition, nextPoint);
    }

    private boolean isGreenTurn() {
        return turn == Team.GREEN;
    }

    private boolean isRedTurn() {
        return turn == Team.RED;
    }
}
