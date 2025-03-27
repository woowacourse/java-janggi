package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.UP;

import java.util.List;
import java.util.Set;

public final class Soldier extends Piece {

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Position destination, final Set<Piece> pieces) {
        // TODO 목적지에 대한 검증은 밖으로 빼는게 나을지도?
//        if (destination.equals(position())) {
//            throw new IllegalArgumentException("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
//        }
//        for (Piece piece : pieces) {
//            if (piece.position().equals(destination) && piece.team() == this.team()) {
//                throw new IllegalArgumentException("[ERROR] 같은 팀이 있는 위치로 이동할 수 없습니다.");
//            }
//        }
        final List<Position> movablePositions = movements().stream()
                .filter(position()::canMove)
                .map(position()::move)
                .toList();
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }
        return new Soldier(destination, team());
    }

    private Set<Movement> movements() {
        if (team() == Team.HAN) {
            return Set.of(DOWN, RIGHT, LEFT);
        }
        return Set.of(UP, RIGHT, LEFT);
    }
}
