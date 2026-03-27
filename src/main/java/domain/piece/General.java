package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class General extends Piece {
    public General(Country country) {
        super(new PieceInfo(PieceType.GENERAL, country));
    }

    @Override
    public List<Direction> findDirections(Position from, Position to) {
        List<Integer> distances = from.calculateDistance(to);
        int x = distances.get(0);
        int y = distances.get(1);

        List<Direction> directions = Direction.findDirections(x, y);
        if (directions.size() != 1) {
            throw new IllegalArgumentException("[ERROR] 궁은 한 칸만 이동할 수 있습니다.");
        }
        if (directions.getFirst().isDialog()) {
            throw new IllegalArgumentException("[ERROR] 궁은 직선으로만 이동 가능합니다.");
        }
        return directions;
    }
}
