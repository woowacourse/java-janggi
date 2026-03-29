package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ElephantFormation {
    RIGHT(initRight()),
    INNER(initInner()),
    LEFT(initLeft()),
    OUTER(initOuter());

    private final Map<PieceLocation, Map<Camp, List<Position>>> positions;

    ElephantFormation(Map<PieceLocation, Map<Camp, List<Position>>> positions) {
        this.positions = positions;
    }

    private static Map<PieceLocation, Map<Camp, List<Position>>> initRight() {
        Map<PieceLocation, Map<Camp, List<Position>>> position = new HashMap<>();
        Map<Camp, List<Position>> horseRightPosition = new HashMap<>();
        horseRightPosition.put(Camp.HAN, List.of(
                new Position(2, 0),
                new Position(7, 0)
        ));

        horseRightPosition.put(Camp.CHO, List.of(
                new Position(1, 9),
                new Position(6, 9)
        ));

        position.put(PieceLocation.HORSE, horseRightPosition);

        Map<Camp, List<Position>> elephantRightPosition = new HashMap<>();
        elephantRightPosition.put(Camp.HAN, List.of(
                new Position(1, 0),
                new Position(6, 0)
        ));

        elephantRightPosition.put(Camp.CHO, List.of(
                new Position(2, 9),
                new Position(7, 9)
        ));

        position.put(PieceLocation.ELEPHANT, elephantRightPosition);

        return position;
    }

    private static Map<PieceLocation, Map<Camp, List<Position>>> initInner() {
        Map<PieceLocation, Map<Camp, List<Position>>> position = new HashMap<>();
        Map<Camp, List<Position>> horseInnerPosition = new HashMap<>();
        horseInnerPosition.put(Camp.HAN, List.of(
                new Position(1, 0),
                new Position(7, 0)
        ));

        horseInnerPosition.put(Camp.CHO, List.of(
                new Position(1, 9),
                new Position(7, 9)
        ));

        position.put(PieceLocation.HORSE, horseInnerPosition);

        Map<Camp, List<Position>> elephantInnerPosition = new HashMap<>();
        elephantInnerPosition.put(Camp.HAN, List.of(
                new Position(2, 0),
                new Position(6, 0)
        ));

        elephantInnerPosition.put(Camp.CHO, List.of(
                new Position(2, 9),
                new Position(6, 9)
        ));

        position.put(PieceLocation.ELEPHANT, elephantInnerPosition);

        return position;
    }

    private static Map<PieceLocation, Map<Camp, List<Position>>> initLeft() {
        Map<PieceLocation, Map<Camp, List<Position>>> position = new HashMap<>();
        Map<Camp, List<Position>> horseLeftPosition = new HashMap<>();
        horseLeftPosition.put(Camp.HAN, List.of(
                new Position(1, 0),
                new Position(6, 0)
        ));

        horseLeftPosition.put(Camp.CHO, List.of(
                new Position(2, 9),
                new Position(7, 9)
        ));

        position.put(PieceLocation.HORSE, horseLeftPosition);

        Map<Camp, List<Position>> elephantLeftPosition = new HashMap<>();
        elephantLeftPosition.put(Camp.HAN, List.of(
                new Position(2, 0),
                new Position(7, 0)
        ));

        elephantLeftPosition.put(Camp.CHO, List.of(
                new Position(1, 9),
                new Position(6, 9)
        ));

        position.put(PieceLocation.ELEPHANT, elephantLeftPosition);

        return position;
    }

    private static Map<PieceLocation, Map<Camp, List<Position>>> initOuter() {
        Map<PieceLocation, Map<Camp, List<Position>>> position = new HashMap<>();
        Map<Camp, List<Position>> horseOuterPosition = new HashMap<>();
        horseOuterPosition.put(Camp.HAN, List.of(
                new Position(2, 0),
                new Position(6, 0)
        ));

        horseOuterPosition.put(Camp.CHO, List.of(
                new Position(2, 9),
                new Position(6, 9)
        ));

        position.put(PieceLocation.HORSE, horseOuterPosition);

        Map<Camp, List<Position>> elephantOuterPosition = new HashMap<>();
        elephantOuterPosition.put(Camp.HAN, List.of(
                new Position(1, 0),
                new Position(7, 0)
        ));

        elephantOuterPosition.put(Camp.CHO, List.of(
                new Position(1, 9),
                new Position(7, 9)
        ));

        position.put(PieceLocation.ELEPHANT, elephantOuterPosition);

        return position;
    }

    public List<Position> getPositions(PieceLocation pieceLocation, Camp camp) {
        return positions.get(pieceLocation).get(camp);
    }
}
