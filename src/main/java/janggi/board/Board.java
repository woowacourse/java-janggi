package janggi.board;

import janggi.dao.BoardDao;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Map;

public class Board {
    private static final int GAME_PROGRESS_CONDITION = 2;

    private final Map<Position, Piece> locatedPieces;

    public Board(Map<Position, Piece> locatedPieces) {
        this.locatedPieces = locatedPieces;
    }

    public void movePiece(Turn turn, Position startPosition, Position arrivedPosition) {
        validateExistsPosition(startPosition);
        Piece attacker = locatedPieces.get(startPosition);
        turn.checkTurn(attacker);
        if (isExistPiece(arrivedPosition)) {
            attackToTarget(attacker, startPosition, arrivedPosition);
            return;
        }
        move(attacker, startPosition, arrivedPosition);
    }

    private void attackToTarget(Piece attacker, Position startPosition, Position arrivedPosition) {
        // todo 공격해서 공격 위치에 있는 기물이 죽으면 DB 에서 삭제 되도록 변경
        Piece target = locatedPieces.get(arrivedPosition);
        validateCatchablePiece(attacker, target);
        move(attacker, startPosition, arrivedPosition);
    }

    private void move(Piece attacker, Position startPosition, Position arrivedPosition) {
        validateObstacle(attacker, startPosition, arrivedPosition);
        locatedPieces.remove(startPosition);
        locatedPieces.put(arrivedPosition, attacker);
    }

    private long calculateObstacleCount(List<Position> paths) {
        return paths.stream().filter(locatedPieces::containsKey).count();
    }

    private boolean canOverObstacle(List<Position> paths) {
        Position position = paths.getFirst();
        return locatedPieces.get(position).isJumpable();
    }

    private void validateObstacle(Piece attackerPiece, Position startPosition, Position arrivedPosition) {
        List<Position> pathPositions = attackerPiece.extractPathPositions(startPosition, arrivedPosition);

        long obstacleCount = calculateObstacleCount(pathPositions);

        if (attackerPiece.isJumpable() && obstacleCount > 1) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
        }

        if (attackerPiece.isJumpable() && canOverObstacle(pathPositions)) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
        }

        if (!attackerPiece.isJumpable() && obstacleCount >= 1) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
        }
    }

    private void validateCatchablePiece(Piece attakerPiece, Piece targetPiece) {
        validateAttackingSameTeam(attakerPiece, targetPiece);
        if (attakerPiece.isJumpable() && targetPiece.isJumpable()) {
            throw new IllegalArgumentException("공격 불가능한 기물입니다");
        }
    }

    private void validateAttackingSameTeam(Piece attakerPiece, Piece targetPiece) {
        if (attakerPiece.isSameTeam(targetPiece.getTeam())) {
            throw new IllegalArgumentException("도착 위치에 아군 기물이 존재합니다");
        }
    }

    private boolean isExistPiece(Position position) {
        return locatedPieces.containsKey(position);
    }

    private void validateExistsPosition(Position startPosition) {
        if (locatedPieces.containsKey(startPosition)) {
            return;
        }
        throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다");
    }

    public boolean isGameOver() {
        long liveKingCount = locatedPieces.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.KING)
                .count();
        return liveKingCount < GAME_PROGRESS_CONDITION;
    }

    public Piece extractWinnerKing() {
        return locatedPieces.values().stream()
                .filter(piece -> piece.getPieceType() == PieceType.KING)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("살아있는 왕이 존재하지 않습니다"));
    }

    public int calculateScore(Team team) {
        return locatedPieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
    }

    public Map<Position, Piece> getLocatedPieces() {
        return locatedPieces;
    }
}
