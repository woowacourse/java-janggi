package piece;

import movement.MovePath;
import movement.MovePaths;
import movement.Movement;
import position.Position;

import java.util.List;

public class General extends Piece {

    // todo: List<Movement> 를 Movements로 바꾸려니까 한 세월 걸리네 이거 한번에 처리할 수 없을까?? Piece에서 할 수 있었으면 좋겠는데,,
    private static final MovePaths moveActions;
    private static final double DISTANCE;

    static {
        moveActions = new MovePaths(List.of(
                new MovePath(Movement.UP),
                new MovePath(Movement.DOWN),
                new MovePath(Movement.LEFT),
                new MovePath(Movement.RIGHT)
        ));

        DISTANCE = moveActions.calculateDistance();
    }

    public General(final Position position, final Country country) {
        super(position, country);
    }

    // TODO 2025. 3. 27. 15:42: validateDistance 의 내부 구현이 모두 동일한데, 이거 Piece에서 처리해줄 수 있지 않나?
    @Override
    protected void validateDistance(double distance) {
        if (distance != DISTANCE) {
            throw new IllegalArgumentException("공격 할 수 없는 거리의 기물입니다.");
        }
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof General;
    }
}
