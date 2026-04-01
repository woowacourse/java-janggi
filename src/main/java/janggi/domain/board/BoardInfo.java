package janggi.domain.board;

public interface BoardInfo {

    boolean isEmpty(Position position);

    boolean isAlly(Position currentPosition, Position targetPosition);
}
