import db.PositionDao;
import db.TurnDao;
import domain.Board;
import domain.Score;
import domain.Team;
import domain.piece.PieceType;
import domain.position.Point;
import domain.position.PointValue;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import util.Loop;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final Board board;
    private Team turn;

    public JanggiGame(final Board board, final Team turn) {
        this.turn = turn;
        this.board = board;
    }

    public void start(final PositionDao positionDao, final TurnDao turnDao) {

        Loop.run(() -> {
            OutputView.printBoard(board);
            printTurn();

            final Position prevPosition = readStartPosition();
            if (isInvalidPiece(prevPosition)) {
                return processTurnChange(turnDao, OutputView::printInvalidFromPoint);
            }

            final Point nextPoint = readEndPoint();
            if (isInvalidEndPoint(prevPosition, nextPoint)) {
                return processTurnChange(turnDao, OutputView::printInvalidEndPoint);
            }

            processMove(prevPosition, nextPoint, positionDao);

            if (board.hasOnlyOneGeneral()) {
                processGameResult();
                return false;
            }
            return processTurnChange(turnDao, OutputView::printEndTurn);
        });
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
        return isGreenTurnWhenPickRedPiece(prevPosition) || isRedTurnWhenPickGreenPiece(prevPosition);
    }

    private boolean isGreenTurnWhenPickRedPiece(final Position prevPosition) {
        return isGreenTurn() && !prevPosition.isGreenTeam();
    }

    private boolean isRedTurnWhenPickGreenPiece(final Position prevPosition) {
        return isRedTurn() && prevPosition.isGreenTeam();
    }

    private boolean processTurnChange(final TurnDao turnDao, final Runnable messagePrinter) {
        messagePrinter.run();
        changeTurn(turnDao);
        return true;
    }

    private Point readEndPoint() {
        final List<String> toNumber = InputView.readToPoint();
        return Point.of(toNumber.getFirst(), toNumber.getLast());
    }

    private void processMove(final Position prevPosition, final Point nextPoint, final PositionDao positionDao) {
        board.move(prevPosition, nextPoint, OutputView::printCaptureMessage);
        final PointValue pointValue = nextPoint.value();
        positionDao.deletePosition(pointValue);
        positionDao.updatePoint(prevPosition.getPointValue(), pointValue);
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

    private void changeTurn(final TurnDao turnDao) {
        turn = turn.opposite();
        turnDao.changeTurn(turn);
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
