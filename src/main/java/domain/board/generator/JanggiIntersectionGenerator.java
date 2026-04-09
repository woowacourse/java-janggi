package domain.board.generator;

import domain.board.Formation;
import domain.intersection.Intersection;
import domain.intersection.palace.*;
import domain.piece.*;
import domain.point.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JanggiIntersectionGenerator implements IntersectionGenerator {

    public static final int REVERSE_ROW = 9;
    public static final int DEFAULT_SOLDIER_ROW = 3;
    public static final int DEFAULT_CANNON_ROW = 2;
    public static final int DEFAULT_BACK_ROW = 0;

    private static final List<Integer> DEFAULT_SOLDIER_FILES = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> DEFAULT_CANNON_FILES = List.of(1, 7);
    private static final List<Integer> DEFAULT_CHARIOT_FILES = List.of(0, 8);

    private final Formation hanFormation;
    private final Formation choFormation;

    public JanggiIntersectionGenerator(Formation hanFormation, Formation choFormation) {
        this.hanFormation = hanFormation;
        this.choFormation = choFormation;
    }

    public List<Intersection> makeIntersection() {
        return Stream.of(Team.values())
                .filter(team -> team != Team.NONE)
                .flatMap(team -> Stream.of(
                        createDefaultSoldierIntersection(team),
                        createDefaultCannonIntersection(team),
                        createDefaultChariotIntersection(team),
                        createElephantAndHorseByFormation(team, getFormationByTeam(team)),
                        createPalaceOfHanIntersection(),
                        createPalaceOfChoIntersection()
                ))
                .flatMap(List::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<NormalIntersection> createDefaultSoldierIntersection(Team team) {
        return createIntersections(getRow(team, DEFAULT_SOLDIER_ROW), DEFAULT_SOLDIER_FILES, new Piece(team, PieceType.SOLDIER));
    }

    private List<NormalIntersection> createDefaultCannonIntersection(Team team) {
        return createIntersections(getRow(team, DEFAULT_CANNON_ROW), DEFAULT_CANNON_FILES, new Piece(team, PieceType.CANNON));
    }

    private List<NormalIntersection> createDefaultChariotIntersection(Team team) {
        return createIntersections(getRow(team, DEFAULT_BACK_ROW), DEFAULT_CHARIOT_FILES, new Piece(team, PieceType.CHARIOT));
    }

    public List<NormalIntersection> createElephantAndHorseByFormation(Team team, Formation formation) {
        int row = getRow(team, DEFAULT_BACK_ROW);
        return Stream.concat(
                createIntersections(row, formation.elephantFormations(), new Piece(team, PieceType.ELEPHANT)).stream(),
                createIntersections(row, formation.horseFormations(), new Piece(team, PieceType.HORSE)).stream()
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<NormalIntersection> createIntersections(int row, List<Integer> files, Piece piece) {
        return files.stream()
                .map(file -> new NormalIntersection(new Point(row, file), piece))
                .toList();
    }

    private Formation getFormationByTeam(Team team) {
        return team.isHan() ? hanFormation : choFormation;
    }

    private int getRow(Team team, int row) {
        return team == Team.CHO ? REVERSE_ROW - row : row;
    }

    private List<Intersection> createPalaceOfHanIntersection() {
        Intersection leftTop = new LeftTopPalace(new Point(0, 3), new Piece(Team.HAN, PieceType.GUARD));
        Intersection rightTop = new RightTopPalace(new Point(0, 5), new Piece(Team.HAN, PieceType.GUARD));
        Intersection center = new CenterPalace(new Point(1, 4), new Piece(Team.HAN, PieceType.GENERAL));
        Intersection leftBottom = LeftBottomPalace.empty(new Point(2, 3));
        Intersection rightBottom = RightBottomPalace.empty(new Point(2, 5));
        return List.of(leftTop, rightTop, center, leftBottom, rightBottom);
    }

    private List<Intersection> createPalaceOfChoIntersection() {
        Intersection leftTop = LeftTopPalace.empty(new Point(7, 3));
        Intersection rightTop = RightTopPalace.empty(new Point(7, 5));
        Intersection center = new CenterPalace(new Point(8, 4), new Piece(Team.CHO, PieceType.GENERAL));
        Intersection leftBottom = new LeftBottomPalace(new Point(9, 3), new Piece(Team.CHO, PieceType.GUARD));
        Intersection rightBottom = new RightBottomPalace(new Point(9, 5), new Piece(Team.CHO, PieceType.GUARD));
        return List.of(leftTop, rightTop, center, leftBottom, rightBottom);
    }

}
