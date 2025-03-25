package piece;

import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;
import static direction.Direction.UP;

import direction.Direction;
import direction.Point;
import java.util.List;

public class Soldier extends Piece {

    private static final List<Direction> paths = List.of(LEFT, RIGHT, UP);

    public Soldier(PieceType pieceType, Point point) {
        super(pieceType, point);
    }

    @Override
    public void validateDestination(Point from, Point to) {
        paths.stream()
                .filter(path -> isValidDestination(from, to, path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다."));
    }

    @Override
    public void checkPaths(Pieces allPieces, Point from, Point to) {

    }

    private boolean isValidDestination(Point from, Point to, Direction path) {
        Point direction = path.apply(getSide());
        return from.isDestinationDirection(to, direction);
    }
}
