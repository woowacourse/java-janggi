package domain.piece;

import domain.Path;
import domain.board.Position;

import java.util.List;
import java.util.Objects;

public record Piece(PieceType pieceType, Team team, MoveStrategy moveStrategy) {

    public List<Position> getPathPositions(Position from, Position to) {
        return moveStrategy.getPathPositions(from, to);
    }

    public void canMove(List<Path> paths, Piece to) {
        moveStrategy.canMove(paths, to);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) return false;
        return team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, team);
    }

    public boolean isSameTeam(Piece another){
        return another.team == team;
    }
}
