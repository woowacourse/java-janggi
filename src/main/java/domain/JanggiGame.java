package domain;

import domain.piece.PieceType;
import domain.position.Point;
import domain.position.Position;
import java.util.List;
import java.util.Map;
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
            if (isInvalidPiece(prevPosition)) {
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
                processGameResult();
                break;
            }
            changeTurn();
        }
    }

    private void processGameResult() {
        final Team team = board.determineWinTeam();
        final Map<PieceType, Integer> winnerPieceCounts = board.countPieces(team);
        double winnerScore = Score.calculate(winnerPieceCounts);
        final Map<PieceType, Integer> loserPieceCounts = board.countPieces(team.opposite());
        double loserScore = Score.calculate(loserPieceCounts);

        if (team.isRedTeam()) {
            winnerScore = Score.adjustScore(winnerScore);
        }
        if (team.isGreenTeam()) {
            loserScore = Score.adjustScore(loserScore);
        }
        OutputView.printWinnerTeam(team, winnerScore, loserScore);
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

    private boolean isInvalidPiece(final Position prevPosition) {
        return (isGreenTurn() && !prevPosition.isGreenTeam()) || (isRedTurn() && prevPosition.isGreenTeam());
    }

    private void changeTurn() {
        turn = turn.opposite();
    }

    private Point readEndPoint() {
        final List<String> toNumber = InputView.readToPoint();
        return Point.of(toNumber.getFirst(), toNumber.getLast());
    }

    private boolean isInvalidEndPoint(final Position prevPosition, final Point nextPoint) {
        return !(prevPosition.isMovableTo(nextPoint) && board.canMoveOnPath(prevPosition, nextPoint));
    }

    private boolean isGreenTurn() {
        return turn.isGreenTeam();
    }

    private boolean isRedTurn() {
        return turn.isRedTeam();
    }
}
