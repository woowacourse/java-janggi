package janggi.domain.piece;

public interface Piece {

    boolean canMove(int startX, int startY, int endX, int endY);

    String nickname();
}
