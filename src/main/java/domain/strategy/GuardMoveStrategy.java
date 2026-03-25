package domain.strategy;

public class GuardMoveStrategy implements MoveStrategy{

    @Override
    public boolean canMove() {
        return false;
    }
}
