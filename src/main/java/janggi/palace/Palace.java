package janggi.palace;

import janggi.board.Position;
import java.util.List;

public class Palace {
    private final List<Position> positions;

    public Palace(List<Position> positions) {
        this.positions = positions;
    }

    public boolean isPieceInsidePalace(Position position) {
        return positions.contains(position);
    }
}
