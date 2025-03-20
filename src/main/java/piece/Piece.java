package piece;

import move.MoveStrategy;
import move.Position;
import direction.Point;
import java.util.Objects;
import move.MovementRule;

public class Piece {

    private final MovementRule movementRule;
    private Point point;


public abstract class Piece {
    private final MoveStrategy moveStrategy;
    private Position position;

    public Piece(MoveStrategy moveStrategy, Position position) {
        this.moveStrategy = moveStrategy;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return Objects.equals(movementRule, piece.movementRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movementRule);
    }
}
