package move;

public class HorseMovement implements MoveStrategy{
    @Override
    public Position move(Position from, Position to) {
        return to;
    }
}
