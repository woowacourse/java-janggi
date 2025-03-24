package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {

    private static final List<Movement> CHO_MOVEMENTS = List.of(
            new Movement(UP), new Movement(RIGHT), new Movement(LEFT)
    );
    private static final List<Movement> HAN_MOVEMENTS = List.of(
            new Movement(DOWN), new Movement(RIGHT), new Movement(LEFT)
    );

    public Soldier(Team team) {
        super(PieceType.SOLDIER, team);
    }

    @Override
    protected void validateMove(final int dy, final int dx) {

        if (isSameTeam(Team.CHO) && isInValidMovement(CHO_MOVEMENTS, dy, dx)) {
            throw new IllegalArgumentException("[ERROR] 졸은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
        }
        if (isSameTeam(Team.HAN) && isInValidMovement(HAN_MOVEMENTS, dy, dx)) {
            throw new IllegalArgumentException("[ERROR] 병은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
        }
    }

    @Override
    protected int moveY(Position arrivalPosition, int differenceForY, final int differenceForX, int currentY,
                        List<Position> positions,
                        int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    @Override
    protected int moveX(Position arrivalPosition, final int differenceForY, int differenceForX, int currentX,
                        List<Position> positions,
                        int currentY) {
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX()) {
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentX;
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }
}
