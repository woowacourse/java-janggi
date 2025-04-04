package view.support;

import domain.board.BoardLocation;
import java.util.Comparator;

public class BoardComparator implements Comparator<BoardLocation> {

    @Override
    public int compare(BoardLocation o1, BoardLocation o2) {
        if (o1.row() == o2.row()) {
            return Integer.compare(o1.column(), o2.column());
        }
        return Integer.compare(o1.row(), o2.row());
    }
}
