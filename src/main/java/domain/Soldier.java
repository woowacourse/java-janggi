package domain;

public class Soldier extends AbstractPiece implements Piece {

    public Soldier(final Team team) {
        super(team);
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (Team.GREEN == super.team && distance.x() == 0 && distance.y() == 1) {
            return true;
        }
        if (Team.RED == team && distance.x() == 0 && distance.y() == -1) {
            return true;
        }
        return (distance.x() == 1 || distance.x() == -1) && distance.y() == 0;
    }
}
