package domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ElephantFormation {
    RIGHT(1, initRight()),
    INNER(2, initInner()),
    LEFT(3, initLeft()),
    OUTER(4, initOuter());

    private final int formationNumber;
    private final Map<PieceLocation, Map<Camp, List<Position>>> positions;

    ElephantFormation(int formationNumber, Map<PieceLocation, Map<Camp, List<Position>>> positions) {
        this.formationNumber = formationNumber;
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

    public static ElephantFormation getFormationType(int formationNumber) {
        return Arrays.stream(ElephantFormation.values())
                .filter(n -> n.formationNumber == formationNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 타입 번호입니다." + formationNumber));
    }

    public List<Position> getPositions(PieceLocation pieceLocation, Camp camp) {
        return positions.get(pieceLocation).get(camp);
    }
}
