package domain.gungsung;

import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Gungsung {

    private static final List<List<Position>> CHO_GUNGSUNGS = List.of(
            List.of(new Position(7, 3), new Position(7, 4), new Position(7, 5)),
            List.of(new Position(8, 3), new Position(8, 4), new Position(8, 5)),
            List.of(new Position(9, 3), new Position(9, 4), new Position(9, 5))
    );

    private static final List<List<Position>> HAN_GUNGSUNGS = List.of(
            List.of(new Position(0, 3), new Position(0, 4), new Position(0, 5)),
            List.of(new Position(1, 3), new Position(1, 4), new Position(1, 5)),
            List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5))
    );

    private static final List<List<List<Integer>>> DIAGONAL_INDEX_PAIR = List.of(
            List.of(List.of(0, 0), List.of(1, 1)),
            List.of(List.of(0, 0), List.of(2, 2)),

            List.of(List.of(0, 2), List.of(1, 1)),
            List.of(List.of(0, 2), List.of(2, 0)),

            List.of(List.of(1, 1), List.of(0, 0)),
            List.of(List.of(1, 1), List.of(0, 2)),
            List.of(List.of(1, 1), List.of(2, 0)),
            List.of(List.of(1, 1), List.of(2, 2)),

            List.of(List.of(2, 0), List.of(0, 2)),
            List.of(List.of(2, 0), List.of(1, 1)),

            List.of(List.of(2, 2), List.of(0, 0)),
            List.of(List.of(2, 2), List.of(1, 1))
    );

    public boolean isGungsung(Position position) {
        return isChoGungsung(position) || isHanGungsung(position);
    }

    public boolean isDiagonal(Position source, Position destination) {
        for (List<List<Integer>> length1IndexPair : DIAGONAL_INDEX_PAIR) {
            List<Integer> targetSourceIndex = length1IndexPair.getFirst();
            List<Integer> targetDestinationIndex = length1IndexPair.get(1);

            Position targetSource = CHO_GUNGSUNGS.get(targetSourceIndex.getFirst())
                    .get(targetSourceIndex.get(1));
            Position targetDestination = CHO_GUNGSUNGS.get(targetDestinationIndex.getFirst())
                    .get(targetDestinationIndex.get(1));

            if (source.equals(targetSource) && destination.equals(targetDestination)) {
                return true;
            }
        }

        for (List<List<Integer>> length1IndexPair : DIAGONAL_INDEX_PAIR) {
            List<Integer> targetSourceIndex = length1IndexPair.getFirst();
            List<Integer> targetDestinationIndex = length1IndexPair.get(1);

            Position targetSource = HAN_GUNGSUNGS.get(targetSourceIndex.getFirst())
                    .get(targetSourceIndex.get(1));
            Position targetDestination = HAN_GUNGSUNGS.get(targetDestinationIndex.getFirst())
                    .get(targetDestinationIndex.get(1));

            if (source.equals(targetSource) && destination.equals(targetDestination)) {
                return true;
            }
        }

        return false;
    }

    public List<Position> getAllPositions() {
        List<Position> allPositions = new ArrayList<>();
        for (List<Position> choGungsung : CHO_GUNGSUNGS) {
            allPositions.addAll(choGungsung);
        }
        for (List<Position> hanGungsung : HAN_GUNGSUNGS) {
            allPositions.addAll(hanGungsung);
        }
        return allPositions;
    }

    private boolean isChoGungsung(Position position) {
        return contains(CHO_GUNGSUNGS, position);
    }

    private boolean isHanGungsung(Position position) {
        return contains(HAN_GUNGSUNGS, position);
    }

    private boolean contains(List<List<Position>> gungsungs, Position position) {
        for (List<Position> gungsung : gungsungs) {
            if (gungsung.contains(position)) {
                return true;
            }
        }
        return false;
    }
}
