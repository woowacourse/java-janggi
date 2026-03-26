package janggi.gimul;

import janggi.position.PositionPath;
import janggi.position.Position;
import janggi.Team;
import java.util.List;

public class Pho extends Gimul{
    protected Pho(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if ((!from.isSameRow(to) && !from.isSameColumn(to)) || from.equals(to)) {
            throw new IllegalArgumentException("해당 경로로는 이동할 수 없습니다.");
        }

        if (from.isSameRow(to)) {
            return from.moveHorizontal(to.getColumnDistance(from));
        }

        return from.moveVertical(to.getRowDistance(from));
    }


    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return  gimuls.size() == 1 && gimuls.stream()
                .noneMatch(gimul -> gimul instanceof Pho);

    }
}
