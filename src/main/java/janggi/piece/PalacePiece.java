package janggi.piece;

import janggi.movement.Movement;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;
import java.util.Objects;

public class PalacePiece implements Piece{

    private final Team team;
    private final PieceType pieceType;

    public PalacePiece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public Position move(Position startPosition, Position arrivedPosition) {
        Movement availableMovement = findAvailableMovementByArrivedPosition(startPosition,arrivedPosition);
        return  availableMovement.step(startPosition, arrivedPosition);
    }

    public Movement findAvailableMovementByArrivedPosition(Position startPosition,Position arrivedPosition) {
        List<Movement> movements = pieceType.generateMovements(startPosition);
        return movements.stream()
                .filter(route ->
                        !arrivedPosition.isOutOfPalace() && !arrivedPosition.isOutOfBoards() && route.step(startPosition, arrivedPosition).equals(arrivedPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("도착 위치로 이동할 수 없습니다"));
    }

    public List<Position> extractPathPositions(Position startPosition,Position arrivedPosition) {
        Movement availableMovement = findAvailableMovementByArrivedPosition(startPosition,arrivedPosition);
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
        PalacePiece that = (PalacePiece) o;
        return team == that.team && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }
}
