package janggi.domain.side;

public interface Team {

    boolean isPieceExists(int x, int y);

    Team move(int startX, int startY, int endX, int endY);
}
