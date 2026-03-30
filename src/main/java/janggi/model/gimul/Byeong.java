package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.board.movement.ByeongMovement;
import janggi.model.board.movement.Movement;
import java.util.List;

public class Byeong extends AbstractGimul {

    private final Movement movement;

    public Byeong(Team team) {
        super(team);
        this.movement = new ByeongMovement();
    }

    @Override
    public MoveResult getLegalPath(Position from, Position to) {
        if ((team == Team.CHO && isMovingSouth(from, to))
                ||(team == Team.HAN && !isMovingSouth(from, to))) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        return movement.move(from, to);
    }

    @Override
    public boolean canPassThrough(
            List<AbstractGimul> gimulsOnPath,
            AbstractGimul gimulAtTo
    ) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(gimulAtTo);
    }

    private boolean isMovingSouth(Position from, Position to) {
        return from.row().getValue() < to.row().getValue();
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }
}
