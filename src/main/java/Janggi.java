import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.position.JanggiPosition;
import domain.score.Score;
import domain.type.JanggiTeam;
import service.JanggiPieceService;
import service.JanggiTurnService;
import view.InputView;
import view.OutputView;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Janggi {
    private final JanggiPieceService janggiPieceService;
    private final JanggiTurnService janggiTurnService;

    public Janggi(JanggiPieceService janggiPieceService, JanggiTurnService janggiTurnService) {
        this.janggiPieceService = janggiPieceService;
        this.janggiTurnService = janggiTurnService;
    }

    public void play() {
        while (true) {
            try {
                JanggiTeam currentTeam = janggiTurnService.getCurrentTeam();
                showBoard();
                OutputView.printCurrentTeam(currentTeam);
                JanggiPosition startPosition = getStartPosition();
                OutputView.printAvailableDestinations(janggiPieceService.getAvailableDestination(startPosition));
                JanggiPosition destinationPosition = getDestinationPosition(startPosition);
                if (isExistBossAt(destinationPosition)) {
                    break;
                }
                move(currentTeam, startPosition, destinationPosition);
                janggiTurnService.switchTurn();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
        OutputView.printGameResult(janggiTurnService.getCurrentTeam(), getScores());
        reset();
    }

    private void showBoard() {
        OutputView.printBoard(janggiPieceService.getPieces());
    }

    private JanggiPosition getStartPosition() {
        while (true) {
            JanggiPosition targetPosition = InputView.readStartPosition();
            if (!janggiPieceService.isExistAt(targetPosition)) {
                OutputView.printNotExistPieceAt(targetPosition);
                continue;
            }
            validateTeam(janggiTurnService.getCurrentTeam(), targetPosition);
            List<JanggiPosition> availableDestinations = janggiPieceService.getAvailableDestination(targetPosition);
            if (!availableDestinations.isEmpty()) {
                return targetPosition;
            }
            OutputView.printNotExistPath();
        }
    }

    private void validateTeam(final JanggiTeam currentTeam, final JanggiPosition from) {
        JanggiChessPiece chessPiece = janggiPieceService.getPieceByPosition(from);
        if (currentTeam != chessPiece.getTeam()) {
            throw new IllegalArgumentException("상대편의 기물을 움직일 수 없습니다.");
        }
    }

    private JanggiPosition getDestinationPosition(JanggiPosition startPosition) {
        while (true) {
            JanggiPosition destinationPosition = InputView.readDestinationPosition();
            List<JanggiPosition> availableDestinations = janggiPieceService.getAvailableDestination(startPosition);
            if (availableDestinations.contains(destinationPosition)) {
                return destinationPosition;
            }
            OutputView.printInvalidDestination(destinationPosition);
        }
    }

    private boolean isExistBossAt(JanggiPosition position) {
        if (!janggiPieceService.isExistAt(position)) {
            return false;
        }
        JanggiChessPiece piece = janggiPieceService.getPieceByPosition(position);
        return piece.getChessPieceType() == Piece.KING;
    }

    private void move(final JanggiTeam currentTeam, final JanggiPosition from, final JanggiPosition to) {
        validateTeam(currentTeam, from);
        janggiPieceService.move(from, to);
    }

    private Map<JanggiTeam, Score> getScores() {
        Map<JanggiTeam, Score> scores = new EnumMap<>(JanggiTeam.class);
        for (JanggiTeam team : JanggiTeam.values()) {
            scores.put(team, janggiPieceService.getScoreWith(team));
        }
        return Collections.unmodifiableMap(scores);
    }

    public void reset() {
        janggiPieceService.reset();
        janggiTurnService.reset();
    }
}
