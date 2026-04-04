package domain;

public interface ExistBoard {

    boolean isExist(Position position);

    boolean isNotCannon(Position position);
}
