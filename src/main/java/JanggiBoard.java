import dao.DatabaseConnector;
import dao.JanggiBoardDao;
import dao.JanggiTurnDao;
import dao.TurnDao;
import domain.hurdlePolicy.HurdlePolicy;
import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositions;
import domain.position.generator.InitDefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;
import util.Database;
import view.InputView;
import view.OutputView;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {
    private final JanggiPositions janggiPositions;
    private JanggiTeam currentTeam;
    private final TurnDao turnDao;

    public JanggiBoard(DatabaseConnector connector) {
        this.turnDao = new JanggiTurnDao(connector);
        this.janggiPositions = new JanggiPositions(
                new InitDefaultPositionsGenerator(),
                new JanggiBoardDao(connector)
        );
        JanggiTeam lastTurn = Database.doDatabaseWorkWithReturn(turnDao::findTurn);
        if (lastTurn == null) {
            this.currentTeam = JanggiTeam.firstTurn();
            turnDao.save(currentTeam);
            return;
        }
        this.currentTeam = lastTurn;
    }

    public void play() {
        while (true) {
            try {
                showBoard();
                OutputView.printCurrentTeam(currentTeam);
                JanggiPosition startPosition = getStartPosition();
                OutputView.printAvailableDestinations(getAvailableDestination(startPosition));
                JanggiPosition destinationPosition = getDestinationPosition(startPosition);
                if (isExistBossAt(destinationPosition)) {
                    break;
                }
                move(currentTeam, startPosition, destinationPosition);
                switchTeam();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
        OutputView.printGameResult(currentTeam, getScores());
        reset();
    }

    private void showBoard() {
        OutputView.printBoard(janggiPositions.getJanggiPieces());
    }

    private JanggiPosition getStartPosition() {
        while (true) {
            JanggiPosition targetPosition = InputView.readStartPosition();
            if (!janggiPositions.existChessPieceByPosition(targetPosition)) {
                OutputView.printNotExistPieceAt(targetPosition);
                continue;
            }
            validateTeam(currentTeam, targetPosition);
            List<JanggiPosition> availableDestinations = getAvailableDestination(targetPosition);
            if (!availableDestinations.isEmpty()) {
                return targetPosition;
            }
            OutputView.printNotExistPath();
        }
    }

    private JanggiPosition getDestinationPosition(JanggiPosition startPosition) {
        while (true) {
            JanggiPosition destinationPosition = InputView.readDestinationPosition();
            List<JanggiPosition> availableDestinations = getAvailableDestination(startPosition);
            if (availableDestinations.contains(destinationPosition)) {
                return destinationPosition;
            }
            OutputView.printInvalidDestination(destinationPosition);
        }
    }

    private void switchTeam() {
        switch (currentTeam) {
            case RED -> currentTeam = JanggiTeam.BLUE;
            case BLUE -> currentTeam = JanggiTeam.RED;
        }
        Database.doDatabaseWork(() -> turnDao.save(currentTeam));
    }

    private void reset() {
        janggiPositions.reset();
        turnDao.deleteAll();
    }

    private boolean isExistBossAt(JanggiPosition position) {
        if (!janggiPositions.existChessPieceByPosition(position)) {
            return false;
        }
        JanggiChessPiece piece = janggiPositions.getJanggiPieceByPosition(position);
        return piece.getChessPieceType() == Piece.KING;
    }

    private void move(final JanggiTeam currentTeam, final JanggiPosition from, final JanggiPosition to) {
        validateTeam(currentTeam, from);
        validateDestination(from, to);
        if (janggiPositions.existChessPieceByPosition(to)) {
            killTarget(to);
        }
        janggiPositions.move(from, to);
    }

    private void validateTeam(final JanggiTeam currentTeam, final JanggiPosition from) {
        JanggiChessPiece chessPiece = janggiPositions.getJanggiPieceByPosition(from);
        if (currentTeam != chessPiece.getTeam()) {
            throw new IllegalArgumentException("상대편의 기물을 움직일 수 없습니다.");
        }
    }

    private void validateDestination(final JanggiPosition from, final JanggiPosition to) {
        List<JanggiPosition> destinations = getAvailableDestination(from);
        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다.");
        }
    }

    private void killTarget(JanggiPosition to) {
        janggiPositions.removeJanggiPieceByPosition(to);
    }

    private List<JanggiPosition> getAvailableDestination(final JanggiPosition position) {
        JanggiChessPiece chessPiece = janggiPositions.getJanggiPieceByPosition(position);
        List<Path> coordinatePaths = chessPiece.getCoordinatePaths(position);
        HurdlePolicy hurdlePolicy = chessPiece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(chessPiece.getTeam(), coordinatePaths, janggiPositions);
    }

    private Map<JanggiTeam, Score> getScores() {
        Map<JanggiTeam, Score> scores = new EnumMap<>(JanggiTeam.class);
        for (JanggiTeam team : JanggiTeam.values()) {
            scores.put(team, janggiPositions.calculateScoreWith(team));
        }
        return Collections.unmodifiableMap(scores);
    }
}
