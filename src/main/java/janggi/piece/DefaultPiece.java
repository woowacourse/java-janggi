package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class DefaultPiece implements Piece{

    private final Team team;
    private final PieceType pieceType;

    public DefaultPiece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public Position move(Position startPosition, Position arrivedPosition) {
        Movement availableMovement = findAvailableMovementByArrivedPosition(startPosition,arrivedPosition);
        return availableMovement.step(startPosition, arrivedPosition);
    }

    public Movement findAvailableMovementByArrivedPosition(Position startPosition,Position arrivedPosition) {
        List<Movement> movements = pieceType.generateMovements(startPosition);
        return movements.stream()
                .filter(movement -> {
                        Position movedPosition = movement.step(startPosition, arrivedPosition);
                        if (movedPosition.isOutOfPalace() && startPosition.isCrossFromPosition(arrivedPosition)) {
                        return false;
                        }
                        if (pieceType.canNotMoveBack() && team.isMoveBack(startPosition, movedPosition)) {
                            return false;
                        }
                        return !arrivedPosition.isOutOfBoards() && movedPosition.equals(arrivedPosition);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    // todo 이동 가능한 경로 뽑아주고 Board에게 장애물 없는지 확인받은 후 move로 움직이기
    public List<Position> extractPathPositions(Position startPosition ,Position arrivedPosition) {
        Movement availableMovement = findAvailableMovementByArrivedPosition(startPosition, arrivedPosition);
        return availableMovement.extractPathPositions(startPosition,arrivedPosition);
    }

    @Override
    public boolean isJumpable() {
        return pieceType.isJumpable();
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultPiece that = (DefaultPiece) o;
        return team == that.team && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }
}
