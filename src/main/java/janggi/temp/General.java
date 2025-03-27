package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.LEFT_DOWN;
import static janggi.temp.Movement.LEFT_UP;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.RIGHT_DOWN;
import static janggi.temp.Movement.RIGHT_UP;
import static janggi.temp.Movement.UP;

import java.util.List;
import java.util.Set;

public final class General extends Piece {

    public General(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Position destination, final Set<Piece> pieces) {
//        if (destination.equals(position())) {
//            throw new IllegalArgumentException("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
//        }
//        for (Piece piece : pieces) {
//            if (piece.position().equals(destination) && piece.team() == this.team()) {
//                throw new IllegalArgumentException("[ERROR] 같은 팀이 있는 위치로 이동할 수 없습니다.");
//            }
//        }
        if (!destination.isPalace()) {
            throw new IllegalArgumentException("[ERROR] 궁성 밖으로 이동할 수 없습니다.");
        }
        final List<Position> movablePositions = movements().stream()
                .filter(position()::canMove)
                .map(position()::move)
                .toList();
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }
        return new General(destination, team());
    }

    private Set<Movement> movements() {
        if (position().isPalaceSide()) {
            return Set.of(RIGHT, LEFT, UP, DOWN);
        }
        return Set.of(RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN);
    }
}
