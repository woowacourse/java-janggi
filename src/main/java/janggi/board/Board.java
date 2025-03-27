package janggi.board;

import janggi.piece.Movement;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public class Board {

    private final List<Piece> locatedPieces;

    public Board(List<Piece> locatedPieces) {
        this.locatedPieces = locatedPieces;
    }

    public void move(Team turn, Position startPosition, Position arrivedPosition) {
        Piece attackerPiece = findByPosition(startPosition);
        checkTurn(turn, attackerPiece);
        boolean isOccupy = isOccupiedPosition(arrivedPosition);
        if (isOccupy) {
            attackToTarget(attackerPiece, arrivedPosition);
            return;
        }
        move(attackerPiece, arrivedPosition);
    }

    private void attackToTarget(Piece attackerPiece, Position arrivedPosition) {
        Piece targetPiece = findByPosition(arrivedPosition);
        validateAttackingSameTeam(attackerPiece, targetPiece);
        validateObstacle(attackerPiece, arrivedPosition);
        targetPiece.receiveAttack();
        attackerPiece.move(arrivedPosition);
    }

    private void move(Piece attackerPiece, Position arrivedPosition) {
        validateObstacle(attackerPiece, arrivedPosition);
        attackerPiece.move(arrivedPosition);
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
        if (attackerPiece.getpieceType() == PieceType.CANNON) {
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

    private void checkTurn(Team turn, Piece attackerPiece) {
        if (attackerPiece.isSameTeam(turn)) {
            return;
        }
        throw new IllegalArgumentException("순서를 확인하세요");
    }

    private Piece findByPosition(Position startPosition) {
        return locatedPieces.stream()
                .filter(piece -> piece.matchesPosition(startPosition) && piece.isLive())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }

    public List<Piece> extractLocatedLivePicecs() {
        return locatedPieces.stream()
                .filter(piece -> piece.isLive())
                .toList();
    }

    public boolean isGameOver() {
        long liveKingCount = locatedPieces.stream()
                .filter(piece -> piece.isLive() && piece.getpieceType() == PieceType.KING)
                .count();
        return liveKingCount == 2;
    }

    public Piece extractWinnerKing() {
        return locatedPieces.stream()
                .filter(piece -> piece.isLive() && piece.getpieceType() == PieceType.KING)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("살아있는 왕이 존재하지 않습니다"));
    }
}
