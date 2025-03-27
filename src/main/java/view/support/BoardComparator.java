package view.support;

import domain.board.BoardLocation;
import java.util.Comparator;

public class BoardComparator implements Comparator<BoardLocation> {

    @Override
    public int compare(BoardLocation o1, BoardLocation o2) {
        if (o1.y() == o2.y()) {
            return Integer.compare(o1.x(), o2.x());
        }
        return Integer.compare(o1.y(), o2.y());
    }
}
