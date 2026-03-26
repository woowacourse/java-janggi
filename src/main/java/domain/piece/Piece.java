package domain.piece;

import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class Piece {
    private final PieceInfo pieceInfo;

    public Piece(PieceInfo pieceInfo) {
        this.pieceInfo = pieceInfo;
    }

    public PieceInfo getPieceInfo() {
        return pieceInfo;
    }

    public List<Position> path(Position from, Position to) {
        // to에서 from을 뺀 값
        List<Integer> distances = from.calculateDistance(to);
        List<Direction> directions = Direction.findDirections(distances.get(0), distances.get(1));

        validateSoldierDirection(directions);
        List<Position> path = new ArrayList<>();
        path.add(from);
        Position position = from;
        for (Direction direction : directions) {
            position = position.nextPosition(direction);
            path.add(position);
        }
        return path;
    }

    private void validateSoldierDirection(List<Direction> directions) {
        if (isSoldier() && pieceInfo.getCountry() == Country.CHO) {
            if (directions.getFirst() == Direction.DOWN) {
                throw new IllegalArgumentException("[ERROR] 졸・병은 후진할 수 없습니다.");
            }
        }
        if (isSoldier() && pieceInfo.getCountry() == Country.HAN) {
            if (directions.getFirst() == Direction.UP) {
                throw new IllegalArgumentException("[ERROR] 졸・병은 후진할 수 없습니다.");
            }
        }
    }

    private boolean isSoldier() {
        return pieceInfo.getPieceType() == PieceType.SOLDIER;
    }
}
