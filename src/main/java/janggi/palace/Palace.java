package janggi.palace;

import janggi.board.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Palace {
    protected List<Position> positions = new ArrayList<>();

    public abstract boolean isPieceInsidePalace(Position position);
}
