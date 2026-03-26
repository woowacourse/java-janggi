package domain.piece;

import domain.Direction;
import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(PieceInfo pieceInfo) {
        super(pieceInfo);
    }

    public List<Position> path(Position from, Position to) {
        // to에서 from을 뺀 값
        List<Integer> distances = from.calculateDistance(to);
        List<Direction> directions = Direction.findDirections(distances.get(0), distances.get(1));

        List<Position> path = new ArrayList<>();
        path.add(from);
        Position position = from;
        for (Direction direction : directions) {
            position = position.nextPosition(direction);
            path.add(position);
        }
        return path;
    }
}
