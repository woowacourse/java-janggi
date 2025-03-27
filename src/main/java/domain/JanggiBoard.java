package domain;

import domain.hurdlePolicy.HurdlePolicy;
import domain.janggiPiece.JanggiPiece;
import domain.path.Path;
import domain.position.JanggiPiecePositions;
import domain.position.JanggiPosition;
import domain.score.Score;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {
    private final JanggiPiecePositions janggiPiecePositions;
    private final Map<JanggiTeam, Score> scores = new EnumMap<>(JanggiTeam.class);

    public JanggiBoard(final JanggiPiecePositions janggiPiecePositions) {
        this.janggiPiecePositions = janggiPiecePositions;
        scores.putAll(Map.of(
                JanggiTeam.RED, Score.zero(),
                JanggiTeam.BLUE, Score.zero()
        ));
    }

    public boolean isExistPieceAt(JanggiPosition position) {
        return janggiPiecePositions.existChessPieceByPosition(position);
    }

    public boolean isExistBossAt(JanggiPosition position) {
        if (!isExistPieceAt(position)) {
            return false;
        }
        JanggiPiece piece = janggiPiecePositions.getJanggiPieceByPosition(position);
        return piece.getChessPieceType() == JanggiPieceType.KING;
    }

    public void move(final JanggiTeam currentTeam, final JanggiPosition from, final JanggiPosition to) {
        validateTeam(currentTeam, from);
        validateDestination(from, to);
        if (janggiPiecePositions.existChessPieceByPosition(to)) {
            killTarget(currentTeam, to);
        }
        janggiPiecePositions.move(from, to);
    }

    public List<JanggiPosition> getAvailableDestination(final JanggiPosition position) {
        JanggiPiece chessPiece = janggiPiecePositions.getJanggiPieceByPosition(position);
        List<Path> coordinatePaths = chessPiece.getCoordinatePaths(position);
        HurdlePolicy hurdlePolicy = chessPiece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(chessPiece.getTeam(), coordinatePaths, janggiPiecePositions);
    }

    public void validateTeam(final JanggiTeam currentTeam, final JanggiPosition from) {
        JanggiPiece chessPiece = janggiPiecePositions.getJanggiPieceByPosition(from);
        if (currentTeam != chessPiece.getTeam()) {
            throw new IllegalArgumentException("상대편의 기물을 움직일 수 없습니다.");
        }
    }

    private void killTarget(JanggiTeam currentTeam, JanggiPosition to) {
        JanggiPiece target = janggiPiecePositions.getJanggiPieceByPosition(to);
        updateScore(currentTeam, target);
        janggiPiecePositions.removeJanggiPieceByPosition(to);
    }

    private void updateScore(JanggiTeam currentTeam, JanggiPiece target) {
        Score score = target.getScore();
        Score updatedScore = scores.get(currentTeam).add(score);
        scores.put(currentTeam, updatedScore);
    }

    private void validateDestination(final JanggiPosition from, final JanggiPosition to) {
        List<JanggiPosition> destinations = getAvailableDestination(from);
        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다.");
        }
    }

    public Map<JanggiPosition, JanggiPiece> getPositions() {
        return janggiPiecePositions.getJanggiPieces();
    }

    public Map<JanggiTeam, Score> getScores() {
        return Collections.unmodifiableMap(scores);
    }
}
