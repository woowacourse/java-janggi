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

    // todo attack 시 해당 위치에 적 기물이 존재하는 경우 기물을 잡습니다.
    // todo 기물이 존재하는 경우 이동하는 기물과 위치하고 있던 기물 사이에 catch() 메시지를 전달하여 catch가 가능하면 잡고 true false 반환한다.
    public void move(Team turn, Position startPosition, Position arrivedPosition) {
        Piece attackerPiece = findByPosition(startPosition);
        checkTurn(turn, attackerPiece);

        boolean isOccupy = isOccupiedPosition(arrivedPosition);
        if (isOccupy) {
            Piece targetPiece = findByPosition(arrivedPosition);
            validateAttackingSameTeam(attackerPiece, targetPiece);
        }
        validateObstacle(attackerPiece, arrivedPosition);
        attackerPiece.attack(arrivedPosition);
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
        }
        ;
    }

    // todo 포지션에 누군가 존재하는 경우 검증 로직 진행하고 공격
    // todo 포지션에 누군가 없으면 바로 공격
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
                .filter(piece -> piece.matchesPosition(startPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }

    // todo 불변으로 반환하 추후 살아있는 상태의 기물만 반환
    public List<Piece> getLocatedPieces() {
        return locatedPieces;
    }
}
