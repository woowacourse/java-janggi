package domain.board;

import domain.intersection.Intersection;
import domain.piece.*;
import domain.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JanggiGenerator implements IntersectionGenerator {

    public static final int DEFAULT_SOLDIER_ROW = 3;
    public static final int DEFAULT_CANNON_ROW = 2;
    public static final int DEFAULT_GENERAL_ROW = 1;
    public static final int DEFAULT_CHARIOT_AND_GUARD_ROW = 0;
    public static final int DEFAULT_ELEPHANT_AND_HORSE_ROW = 0;

    private final Formation hanFormation;
    private final Formation choFormation;

    public JanggiGenerator(Formation hanFormation, Formation choFormation) {
        this.hanFormation = hanFormation;
        this.choFormation = choFormation;
    }

    public List<Intersection> makeIntersection() {
        return Stream.of(Team.values())
                .flatMap(team -> Stream.of(
                        createDefaultSoldierIntersection(team, DEFAULT_SOLDIER_ROW),
                        createDefaultCannonIntersection(team, DEFAULT_CANNON_ROW),
                        createDefaultGeneralIntersection(team, DEFAULT_GENERAL_ROW),
                        createDefaultGuardAndChariotIntersection(team, DEFAULT_CHARIOT_AND_GUARD_ROW),
                        createDefaultElephantAndHorseIntersection(team, DEFAULT_ELEPHANT_AND_HORSE_ROW),
                        createElephantAndHorseByFormation()
                ))
                .flatMap(List::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Intersection> createDefaultSoldierIntersection(Team team, int row) {
        row = reverseRow(team, row);
        List<Intersection> intersections = new ArrayList<>();

        for (int file = 0; file <= 8; file += 2) {
            intersections.add(new Intersection(new Point(row, file), new Soldier(team)));
        }
        return intersections;
    }

    private List<Intersection> createDefaultCannonIntersection(Team team, int row) {
        row = reverseRow(team, row);
        return List.of(
                new Intersection(new Point(row, 1), new Cannon(team)),
                new Intersection(new Point(row, 7), new Cannon(team))
        );
    }

    private List<Intersection> createDefaultGeneralIntersection(Team team, int row) {
        row = reverseRow(team, row);
        return List.of(new Intersection(new Point(row, 4), new General(team)));
    }

    private List<Intersection> createDefaultGuardAndChariotIntersection(Team team, int row) {
        row = reverseRow(team, row);
        return List.of(
                new Intersection(new Point(row, 0), new Chariot(team)),
                new Intersection(new Point(row, 8), new Chariot(team)),
                new Intersection(new Point(row, 3), new Guard(team)),
                new Intersection(new Point(row, 5), new Guard(team))
        );
    }

    private List<Intersection> createDefaultElephantAndHorseIntersection(Team team, int row) {
        row = reverseRow(team, row);
        return List.of(
                new Intersection(new Point(row, 1), new Elephant(team)),
                new Intersection(new Point(row, 2), new Horse(team)),
                new Intersection(new Point(row, 6), new Elephant(team)),
                new Intersection(new Point(row, 7), new Horse(team))
        );
    }

    public List<Intersection> createElephantAndHorseByFormation() {
        return Stream.concat(
                createElephantAndHorseByFormation(Team.HAN, hanFormation).stream(),
                createElephantAndHorseByFormation(Team.CHO, choFormation).stream()
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Intersection> createElephantAndHorseByFormation(Team team, Formation formation) {
        int correctedRow = reverseRow(team, DEFAULT_ELEPHANT_AND_HORSE_ROW);

        return Stream.concat(
                createIntersections(correctedRow, formation.elephantFormations(), new Elephant(team)).stream(),
                createIntersections(correctedRow, formation.horseFormations(), new Horse(team)).stream()
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Intersection> createIntersections(int row, List<Integer> files, Piece piece) {
        return files.stream()
                .map(file -> new Intersection(new Point(row, file), piece))
                .toList();
    }

    private int reverseRow(Team team, int row) {
        int maxRow = 9;
        if (team == Team.CHO) {
            return maxRow - row;
        }
        return row;
    }

}
