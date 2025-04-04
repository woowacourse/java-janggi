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

    public void dropPiece(Turn turn, Position startPosition, Position arrivedPosition, BoardDao boardDao) {
        validateExistsPosition(startPosition);
        Piece attacker = locatedPieces.get(startPosition);
        turn.checkTurn(attacker);
        if (isExistPiece(arrivedPosition)) {
            attackToTarget(attacker, startPosition, arrivedPosition, boardDao);
            return;
        }
        move(attacker, startPosition, arrivedPosition, boardDao);
    }

    private void attackToTarget(Piece attacker, Position startPosition, Position arrivedPosition, BoardDao boardDao) {
        // todo 공격해서 공격 위치에 있는 기물이 죽으면 DB 에서 삭제 되도록 변경
        Piece target = locatedPieces.get(arrivedPosition);
        validateAttackingSameTeam(attacker, target);
        move(attacker, startPosition, arrivedPosition, boardDao);
    }

    private void move(Piece attacker, Position startPosition, Position arrivedPosition, BoardDao boardDao) {
        validateObstacle(attacker, startPosition, arrivedPosition);
        locatedPieces.remove(startPosition);
        locatedPieces.put(arrivedPosition, attacker);
    }

    private long calculateObstacleCount(List<Position> paths) {
        return paths.stream().filter(locatedPieces::containsKey).count();
    }

    private boolean canOverObstacle(List<Position> paths) {
        Position position = paths.getFirst();
        return !locatedPieces.get(position).isJumpable();
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

    private void updatePiece(Piece previousPiece, Piece updatePiece, BoardDao boardDao) {
        locatedPieces.remove(previousPiece);
//        locatedPieces.add(updatePiece);
//        boardDao.updateBoardPiece(previousPiece, updatePiece);
    }


    private void validateAttackingSameTeam(Piece attakerPiece, Piece targetPiece) {
        if (attakerPiece.isSameTeam(targetPiece.getTeam())) {
            throw new IllegalArgumentException("도착 위치에 아군 기물이 존재합니다");
        }
    }

    private boolean isExistPiece(Position position) {
        return locatedPieces.containsKey(position);
    }

/*
    private boolean isExistObstacleOfPath(Piece attackerPiece, List<Position> pathPositions, List<Piece> locatedPieces) {
        List<Piece> obstacles = locatedPieces.stream()
                .filter(piece -> piece.isObstacle(pathPositions))
                .toList();
        if (attackerPiece.getPieceType() == PieceType.CANNON) {
            if (obstacles.size() != 1) {
                return true;
            }
            Piece obstacle = obstacles.getFirst();

            return obstacle.canNotJumpingOver();
        }
        return !obstacles.isEmpty();
    }*/

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
