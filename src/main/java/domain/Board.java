package domain;

import domain.chessPiece.ChessPiece;
import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessTeam;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final ChessPiecePositions chessPiecePositions;
    private final Map<ChessTeam, Score> scores = new EnumMap<>(ChessTeam.class);

    public Board(final ChessPiecePositions chessPiecePositions) {
        this.chessPiecePositions = chessPiecePositions;
        scores.putAll(Map.of(
                ChessTeam.RED, Score.zero(),
                ChessTeam.BLUE, Score.zero()
        ));
    }

    public boolean isExistPieceAt(ChessPosition position) {
        return chessPiecePositions.existChessPieceByPosition(position);
    }

    public void move(final ChessTeam currentTeam, final ChessPosition from, final ChessPosition to) {
        validateTeam(currentTeam, from);
        validateDestination(from, to);
        if (chessPiecePositions.existChessPieceByPosition(to)) {
            killTarget(currentTeam, to);
        }
        chessPiecePositions.move(from, to);
    }

    public List<ChessPosition> getAvailableDestination(final ChessPosition position) {
        ChessPiece chessPiece = chessPiecePositions.getChessPieceByPosition(position);
        final List<Path> coordinatePaths = chessPiece.getCoordinatePaths(position);
        final HurdlePolicy hurdlePolicy = chessPiece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(chessPiece.getTeam(), coordinatePaths, chessPiecePositions);
    }

    public void validateTeam(final ChessTeam currentTeam, final ChessPosition from) {
        ChessPiece chessPiece = chessPiecePositions.getChessPieceByPosition(from);
        if (currentTeam != chessPiece.getTeam()) {
            throw new IllegalArgumentException("상대편의 기물을 움직일 수 없습니다.");
        }
    }

    private void killTarget(ChessTeam currentTeam, ChessPosition to) {
        ChessPiece target = chessPiecePositions.getChessPieceByPosition(to);
        updateScore(currentTeam, target);
        chessPiecePositions.removeChessPieceByPosition(to);
    }

    private void updateScore(ChessTeam currentTeam, ChessPiece target) {
        Score score = target.getScore();
        Score updatedScore = scores.get(currentTeam).add(score);
        scores.put(currentTeam, updatedScore);
    }

    private void validateDestination(final ChessPosition from, final ChessPosition to) {
        List<ChessPosition> destinations = getAvailableDestination(from);
        if (!destinations.contains(to)) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다.");
        }
    }

    public Map<ChessPosition, ChessPiece> getPositions() {
        return chessPiecePositions.getChessPieces();
    }
}
