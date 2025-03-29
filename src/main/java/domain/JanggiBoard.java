package domain;

import dao.JanggiBoardDao;
import domain.hurdlePolicy.HurdlePolicy;
import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositions;
import domain.position.generator.InitDefaultPositionsGenerator;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {
    private final JanggiPositions janggiPositions;

    public JanggiBoard() {
        this.janggiPositions = new JanggiPositions(
                new InitDefaultPositionsGenerator(),
                new JanggiBoardDao()
        );
    }

    public boolean isExistBossAt(JanggiPosition position) {
        if (!isExistPieceAt(position)) {
            return false;
        }
        JanggiChessPiece piece = janggiPositions.getJanggiPieceByPosition(position);
        return piece.getChessPieceType() == Piece.KING;
    }

    public boolean isExistPieceAt(JanggiPosition position) {
        return janggiPositions.existChessPieceByPosition(position);
    }

    public void move(final JanggiTeam currentTeam, final JanggiPosition from, final JanggiPosition to) {
        validateTeam(currentTeam, from);
        validateDestination(from, to);
        if (janggiPositions.existChessPieceByPosition(to)) {
            killTarget(to);
        }
        janggiPositions.move(from, to);
    }

    public void validateTeam(final JanggiTeam currentTeam, final JanggiPosition from) {
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

    public List<JanggiPosition> getAvailableDestination(final JanggiPosition position) {
        JanggiChessPiece chessPiece = janggiPositions.getJanggiPieceByPosition(position);
        List<Path> coordinatePaths = chessPiece.getCoordinatePaths(position);
        HurdlePolicy hurdlePolicy = chessPiece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(chessPiece.getTeam(), coordinatePaths, janggiPositions);
    }

    public void reset() {
        janggiPositions.reset();
    }

    public Map<JanggiPosition, JanggiChessPiece> getPositions() {
        return janggiPositions.getJanggiPieces();
    }

    public Map<JanggiTeam, Score> getScores() {
        Map<JanggiTeam, Score> scores = new EnumMap<>(JanggiTeam.class);
        for (JanggiTeam team : JanggiTeam.values()) {
            scores.put(team, janggiPositions.calculateScoreWith(team));
        }
        return Collections.unmodifiableMap(scores);
    }
}
