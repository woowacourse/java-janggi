package domain;

import dao.JanggiBoardDao;
import domain.hurdlePolicy.HurdlePolicy;
import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.path.Path;
import domain.position.JanggiPiecePositions;
import domain.position.JanggiPosition;
import domain.position.generator.InitDefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {
    private final JanggiPiecePositions janggiPiecePositions;

    public JanggiBoard() {
        this.janggiPiecePositions = new JanggiPiecePositions(
                new InitDefaultPositionsGenerator(),
                new JanggiBoardDao()
        );
    }

    public boolean isExistBossAt(JanggiPosition position) {
        if (!isExistPieceAt(position)) {
            return false;
        }
        JanggiChessPiece piece = janggiPiecePositions.getJanggiPieceByPosition(position);
        return piece.getChessPieceType() == Piece.KING;
    }

    public boolean isExistPieceAt(JanggiPosition position) {
        return janggiPiecePositions.existChessPieceByPosition(position);
    }

    public void move(final JanggiTeam currentTeam, final JanggiPosition from, final JanggiPosition to) {
        validateTeam(currentTeam, from);
        validateDestination(from, to);
        if (janggiPiecePositions.existChessPieceByPosition(to)) {
            killTarget(to);
        }
        janggiPiecePositions.move(from, to);
    }

    public void validateTeam(final JanggiTeam currentTeam, final JanggiPosition from) {
        JanggiChessPiece chessPiece = janggiPiecePositions.getJanggiPieceByPosition(from);
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
        janggiPiecePositions.removeJanggiPieceByPosition(to);
    }

    public List<JanggiPosition> getAvailableDestination(final JanggiPosition position) {
        JanggiChessPiece chessPiece = janggiPiecePositions.getJanggiPieceByPosition(position);
        List<Path> coordinatePaths = chessPiece.getCoordinatePaths(position);
        HurdlePolicy hurdlePolicy = chessPiece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(chessPiece.getTeam(), coordinatePaths, janggiPiecePositions);
    }

    public void reset() {
        janggiPiecePositions.reset();
    }

    public Map<JanggiPosition, JanggiChessPiece> getPositions() {
        return janggiPiecePositions.getJanggiPieces();
    }

    public Map<JanggiTeam, Score> getScores() {
        Map<JanggiTeam, Score> scores = new EnumMap<>(JanggiTeam.class);
        for (JanggiTeam team : JanggiTeam.values()) {
            scores.put(team, janggiPiecePositions.calculateScoreWith(team));
        }
        return Collections.unmodifiableMap(scores);
    }
}
