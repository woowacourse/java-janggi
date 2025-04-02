package domain.piece;

import domain.position.Position;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final Position position;
    private final PieceType type;
    private final MovementRule rule;

    public Piece(final Position position, PieceType type, final MovementRule rule) {
        this.position = position;
        this.type = type;
        this.rule = rule;
    }

    public Piece updatePosition(final Position position) {
        return new Piece(position, type, rule);
    }

    public String getName() {
        return type.getName();
    }

    public boolean isEqualType(final PieceType type) {
        return this.type == type;
    }

    public int getScore() {
        return type.getScore();
    }

    public List<Position> getPath(final Position targetPosition) {
        return rule.getPath(position, targetPosition);
    }

    public void validateMovablePosition(final Position target) {
        if (!rule.isValidRangePosition(target) || !rule.canMoveToTargetPosition(position, target)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 위치입니다.");
        }
    }

    public boolean isSamePosition(final Position position) {
        return this.position.equals(position);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Piece piece = (Piece) object;
        return Objects.equals(position, piece.position) && Objects.equals(rule, piece.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, rule);
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getType() {
        return type;
    }
}
