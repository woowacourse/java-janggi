package location;

import java.util.List;

public interface PathManager {
    void checkStraightMovement(Position from, Position to);

    void checkOneMovement(Position from, Position to);

    List<Position> calculateOneDirectionPaths(Position from, Position to);

    boolean isPalacePosition(Position destination);

    void checkValidOneDiagonalMovementInPalace(Position from, Position to);

    void checkValidTwoDiagonalMovementInPalace(Position from, Position to);
}
