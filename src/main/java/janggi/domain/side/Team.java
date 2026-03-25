package janggi.domain.side;

import janggi.dto.BoardSpot;
import java.util.List;

public interface Team {

    boolean isPieceExists(int x, int y);

    List<BoardSpot> makeSpots();

    Team move(int startX, int startY, int endX, int endY);
}
