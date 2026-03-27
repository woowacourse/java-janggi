package domain.board;

import domain.intersection.Intersection;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.Team;
import domain.point.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JanggiGenerator implements IntersectionGenerator {

    public static final int MAX_ROW = 9;
    public static final int DEFAULT_SOLDIER_ROW = 3;
    public static final int DEFAULT_CANNON_ROW = 2;
    public static final int DEFAULT_GENERAL_ROW = 1;
    public static final int DEFAULT_BACK_ROW = 0;

    private static final List<Integer> DEFAULT_SOLDIER_FILES = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> DEFAULT_CANNON_FILES = List.of(1, 7);
    private static final List<Integer> DEFAULT_GENERAL_FILES = List.of(4);
    private static final List<Integer> DEFAULT_GUARD_FILES = List.of(3, 5);
    private static final List<Integer> DEFAULT_CHARIOT_FILES = List.of(0, 8);

    private final Formation hanFormation;
    private final Formation choFormation;

    public JanggiGenerator(Formation hanFormation, Formation choFormation) {
        this.hanFormation = hanFormation;
        this.choFormation = choFormation;
    }

    public List<Intersection> makeIntersection() {
        return Stream.of(Team.values())
                .flatMap(team -> Stream.of(
                        createDefaultSoldierIntersectionV2(team),
                        createDefaultCannonIntersectionV2(team),
                        createDefaultGeneralIntersectionV2(team),
                        createDefaultGuardIntersectionV2(team),
                        createDefaultChariotIntersectionV2(team),
                        createElephantAndHorseByFormation(team, getFormationByTeam(team))
                ))
                .flatMap(List::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Intersection> createDefaultSoldierIntersectionV2(Team team) {
        return createIntersections(getRow(team, DEFAULT_SOLDIER_ROW), DEFAULT_SOLDIER_FILES, new Soldier(team));
    }

    private List<Intersection> createDefaultCannonIntersectionV2(Team team) {
        return createIntersections(getRow(team, DEFAULT_CANNON_ROW), DEFAULT_CANNON_FILES, new Cannon(team));
    }

    private List<Intersection> createDefaultGeneralIntersectionV2(Team team) {
        return createIntersections(getRow(team, DEFAULT_GENERAL_ROW), DEFAULT_GENERAL_FILES, new General(team));
    }

    private List<Intersection> createDefaultGuardIntersectionV2(Team team) {
        return createIntersections(getRow(team, DEFAULT_BACK_ROW), DEFAULT_GUARD_FILES, new Guard(team));
    }

    private List<Intersection> createDefaultChariotIntersectionV2(Team team) {
        return createIntersections(getRow(team, DEFAULT_BACK_ROW), DEFAULT_CHARIOT_FILES, new Chariot(team));
    }

    public List<Intersection> createElephantAndHorseByFormation(Team team, Formation formation) {
        int row = getRow(team, DEFAULT_BACK_ROW);
        return Stream.concat(
                createIntersections(row, formation.elephantFormations(), new Elephant(team)).stream(),
                createIntersections(row, formation.horseFormations(), new Horse(team)).stream()
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Intersection> createIntersections(int row, List<Integer> files, Piece piece) {
        return files.stream()
                .map(file -> new Intersection(new Point(row, file), piece))
                .toList();
    }

    private Formation getFormationByTeam(Team team) {
        if (team == Team.HAN) {
            return hanFormation;
        }
        return choFormation;
    }

    private int getRow(Team team, int row) {
        if (team == Team.CHO) {
            return MAX_ROW - row;
        }
        return row;
    }

}
