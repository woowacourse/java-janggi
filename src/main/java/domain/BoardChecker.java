package domain;

public interface BoardChecker {

    boolean isExist(Position position);

    boolean isNotCannon(Position position);
}
