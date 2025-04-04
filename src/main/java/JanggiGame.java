import db.JanggiGameRepository;
import db.connector.MySqlConnector;
import db.mysql.JanggiGameMysqlRepository;
import db.mysql.PositionMysqlRepository;
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
        final JanggiGameRepository janggiGameRepository = new JanggiGameMysqlRepository();
        final PositionMysqlRepository positionMysqlRepository = new PositionMysqlRepository();
        final MySqlConnector mySqlConnector = new MySqlConnector();

        Loop.run(() -> {
            OutputView.printBoard(board);
            printTurn();

            final Position prevPosition = readStartPosition();
            if (isInvalidPiece(prevPosition)) {
                return processTurnChange(janggiGameRepository, mySqlConnector, OutputView::printInvalidFromPoint);
            }

            final Point nextPoint = readEndPoint();
            if (isSamePoint(prevPosition, nextPoint)) {
                throw new IllegalArgumentException("아무 행동을 하지 않고 턴을 넘길 수 없습니다.");
            }

            if (isInvalidEndPoint(prevPosition, nextPoint)) {
                return processTurnChange(janggiGameRepository, mySqlConnector, OutputView::printInvalidEndPoint);
            }

            processMove(prevPosition, nextPoint, positionMysqlRepository, mySqlConnector);

            if (board.hasOnlyOneGeneral()) {
                processGameResult();
                return false;
            }
            return processTurnChange(janggiGameRepository, mySqlConnector, OutputView::printEndTurn);
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

    private boolean processTurnChange(final JanggiGameRepository janggiGameRepository,
                                      final MySqlConnector mySqlConnector, final Runnable messagePrinter) {
        messagePrinter.run();
        changeTurn(janggiGameRepository, mySqlConnector);
        return true;
    }

    private Point readEndPoint() {
        final List<String> toNumber = InputView.readToPoint();
        return Point.of(toNumber.getFirst(), toNumber.getLast());
    }

    private void processMove(final Position prevPosition,
                             final Point nextPoint,
                             final PositionMysqlRepository positionMysqlRepository,
                             final MySqlConnector mySqlConnector
    ) {
        board.move(prevPosition, nextPoint, OutputView::printCaptureMessage);
        final PointValue pointValue = nextPoint.value();

        positionMysqlRepository.deletePosition(mySqlConnector.getConnection(), pointValue);
        positionMysqlRepository.updatePoint(mySqlConnector.getConnection(), prevPosition.getPointValue(), pointValue);
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

    private void changeTurn(final JanggiGameRepository janggiGameRepository, final MySqlConnector mySqlConnector) {
        turn = turn.opposite();
        janggiGameRepository.changeTurn(mySqlConnector.getConnection(), turn);
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
