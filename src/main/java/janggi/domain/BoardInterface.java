package janggi.domain;

public interface BoardInterface {
    boolean isEmpty(Position position);
    boolean isPo(Position position);
    boolean isEnemy(Side side, Position position);
}
