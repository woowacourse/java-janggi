package janggi.board;

import janggi.dao.BoardDao;
import janggi.piece.Movement;
import janggi.piece.Piece;
import janggi.piece.PieceType;

import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public class Board {

    private final List<Piece> locatedPieces;
    private static final int GAME_PROGRESS_CONDITION = 2;

    public Board(List<Piece> locatedPieces) {
        this.locatedPieces = locatedPieces;
    }

    public void dropPiece(Turn turn, Position startPosition, Position arrivedPosition, BoardDao boardDao) {
        Piece attackerPiece = findByPosition(startPosition);
        turn.checkTurn(attackerPiece);
        boolean isOccupy = isOccupiedPosition(arrivedPosition);
        if (isOccupy) {
            attackToTarget(attackerPiece, arrivedPosition ,boardDao);
            return;
        }
        move(attackerPiece, arrivedPosition, boardDao);
    }

    private void attackToTarget(Piece attackerPiece, Position arrivedPosition, BoardDao boardDao) {
        Piece targetPiece = findByPosition(arrivedPosition);
        validateAttackingSameTeam(attackerPiece, targetPiece);
        Piece updatedPiece = targetPiece.receiveAttack();
        updatePiece(targetPiece,updatedPiece,boardDao);
        move(attackerPiece, arrivedPosition, boardDao);
    }

    private void move(Piece attackerPiece, Position arrivedPosition, BoardDao boardDao) {
        validateObstacle(attackerPiece, arrivedPosition);
        Piece movedPiece = attackerPiece.move(arrivedPosition);
        updatePiece(attackerPiece, movedPiece, boardDao);
    }

    private void updatePiece(Piece previousPiece, Piece updatePiece,  BoardDao boardDao) {
        locatedPieces.remove(previousPiece);
        locatedPieces.add(updatePiece);
        boardDao.updateBoardPiece(previousPiece, updatePiece);
    }

    private void validateAttackingSameTeam(Piece attakerPiece, Piece targetPiece) {
        if (attakerPiece.isSameTeam(targetPiece.getTeam())) {
            throw new IllegalArgumentException("도착 위치에 아군 기물이 존재합니다");
        }
    }

    private void validateObstacle(Piece attackerPiece, Position arrivedPosition) {
        List<Movement> availableMovement = attackerPiece.findAvailableMovementByArrivedPosition(arrivedPosition);
        List<Position> pathPositions = attackerPiece.extractPathPositions(availableMovement, arrivedPosition);
        if (isExistObstacleOfPath(attackerPiece, pathPositions, locatedPieces)) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다");
        };
    }

    private boolean isOccupiedPosition(Position arrivedPosition) {
        return locatedPieces.stream()
                .anyMatch(piece -> piece.matchesPosition(arrivedPosition));
    }

    private boolean isExistObstacleOfPath(Piece attackerPiece, List<Position> pathPositions, List<Piece> locatedPieces) {
        List<Piece> obstacles = locatedPieces.stream()
                .filter(piece -> piece.isObstacle(pathPositions))
                .toList();
        if (attackerPiece.getPieceType() == PieceType.CANNON) {
            if (obstacles.size() != 1) {
                return true;
            }
            Piece obstacle = obstacles.getFirst();

            if (obstacle.canNotJumpingOver()) {
                return true;
            }
            return false;
        }
        if (obstacles.size() >= 1) {
            return true;
        }
        return false;
    }

    private Piece findByPosition(Position startPosition) {
        return locatedPieces.stream()
                .filter(piece -> piece.matchesPosition(startPosition) && piece.isLive())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }

    public List<Piece> extractLocatedLivePieces() {
        return locatedPieces.stream()
                .filter(Piece::isLive)
                .toList();
    }

    public boolean isGameOver() {
        long liveKingCount = locatedPieces.stream()
                .filter(piece -> piece.isLive() && piece.getPieceType() == PieceType.KING)
                .count();
        return liveKingCount < GAME_PROGRESS_CONDITION;
    }

    public Piece extractWinnerKing() {
        return locatedPieces.stream()
                .filter(piece -> piece.isLive() && piece.getPieceType() == PieceType.KING)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("살아있는 왕이 존재하지 않습니다"));
    }

    public int calculateScore(Team team) {
        return locatedPieces.stream()
                .filter(piece -> piece.isSameTeam(team) && piece.isLive())
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
    }
}
