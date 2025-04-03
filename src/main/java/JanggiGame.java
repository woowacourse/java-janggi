import db.JanggiDao;
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

    public void start() {
        final JanggiDao janggiDao = new JanggiDao();

        Loop.run(() -> {
            OutputView.printBoard(board);
            printTurn();

            final Position prevPosition = readStartPosition();
            if (isInvalidPiece(prevPosition)) {
                return processTurnChange(janggiDao, OutputView::printInvalidFromPoint);
            }

            final Point nextPoint = readEndPoint();
            if (isSamePoint(prevPosition, nextPoint)) {
                throw new IllegalArgumentException("아무 행동을 하지 않고 턴을 넘길 수 없습니다.");
            }

            if (isInvalidEndPoint(prevPosition, nextPoint)) {
                return processTurnChange(janggiDao, OutputView::printInvalidEndPoint);
            }

            processMove(prevPosition, nextPoint, janggiDao);

            if (board.hasOnlyOneGeneral()) {
                processGameResult();
                return false;
            }
            return processTurnChange(janggiDao, OutputView::printEndTurn);
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

    private boolean processTurnChange(final JanggiDao janggiDao, final Runnable messagePrinter) {
        messagePrinter.run();
        changeTurn(janggiDao);
        return true;
    }

    private Point readEndPoint() {
        final List<String> toNumber = InputView.readToPoint();
        return Point.of(toNumber.getFirst(), toNumber.getLast());
    }

    private void processMove(final Position prevPosition, final Point nextPoint, final JanggiDao janggiDao) {
        board.move(prevPosition, nextPoint, OutputView::printCaptureMessage);
        final PointValue pointValue = nextPoint.value();
        janggiDao.deletePosition(pointValue);
        janggiDao.updatePoint(prevPosition.getPointValue(), pointValue);
    }

    private void processGameResult() {
        final Team winingTeam = determineWiningTeam();
        final double winnerScore = adjustScore(calculateScore(winingTeam), winingTeam.isRedTeam());
        final double loserScore = adjustScore(calculateScore(winingTeam.opposite()), winingTeam.isGreenTeam());
        printGameResult(winingTeam, winnerScore, loserScore);
    }

    private Team determineWiningTeam() {
        return board.determineWinTeam();
    }

    private double calculateScore(final Team winnerTeam) {
        final Map<PieceType, Integer> loserPieceCounts = board.countPieces(winnerTeam);
        return Score.calculate(loserPieceCounts);
    }

    private double adjustScore(final double score, final boolean isRedTeam) {
        if (isRedTeam) {
            return Score.adjustScore(score);
        }
        return score;
    }

    private void printGameResult(final Team winnerTeam, final double winnerScore, final double loserScore) {
        OutputView.printWinnerTeam(winnerTeam, winnerScore, loserScore);
    }

    private void changeTurn(final JanggiDao janggiDao) {
        turn = turn.opposite();
        janggiDao.changeTurn(turn);
    }

    private boolean isSamePoint(final Position prevPosition, final Point nextPoint) {
        return prevPosition.isSame(nextPoint);
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
