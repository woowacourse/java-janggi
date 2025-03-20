package domain;

import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.EndlessMovement;
import domain.movements.PieceMovement;
import domain.movements.Route;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.EmptyPiece;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGame {
    private static final int BOARD_ROW_MAX = 10;
    private static final int BOARD_COLUMN_MAX = 9;

    private final Board board;
    private final Player han;
    private final Player cho;

    public JanggiGame(Board board, Player han, Player cho) {
        this.board = board;
        this.han = han;
        this.cho = cho;
    }

    public JanggiGame(Player han, Player cho) {
        this.han = han;
        this.cho = cho;

        board = generateBoard();
    }

    public Map<Point, Piece> getBoard() {
        return board.getLocations();
    }

    public void move(Point originPoint, Point arrivalPoint) {
        board.movePiece(originPoint, arrivalPoint);
    }

    private Board generateBoard() {
        final Map<Point, Piece> locations = generateEmptyBoard();
        locations.putAll(generateLocationsForHan());
        locations.putAll(generateLocationsForCho());
        return new Board(locations);
    }

    private Map<Point, Piece> generateEmptyBoard() {
        final Map<Point, Piece> locations = new HashMap<>();
        for (int row = 0; row < BOARD_ROW_MAX; row++) {
            for (int column = 0; column < BOARD_COLUMN_MAX; column++) {
                locations.put(new Point(row, column), new EmptyPiece());
            }
        }
        return locations;
    }

    private Map<Point, Piece> generateLocationsForHan() {
        final Map<Point, Piece> locations = new HashMap<>();
        PieceMovement soldierMovement = new DefaultMovement(List.of(
                new Route(List.of(Direction.SOUTH)),
                new Route(List.of(Direction.EAST)),
                new Route(List.of(Direction.WEST))
        ));
        locations.put(new Point(6, 0), new Soldier(Team.HAN, soldierMovement));
        locations.put(new Point(6, 2), new Soldier(Team.HAN, soldierMovement));
        locations.put(new Point(6, 4), new Soldier(Team.HAN, soldierMovement));
        locations.put(new Point(6, 6), new Soldier(Team.HAN, soldierMovement));
        locations.put(new Point(6, 8), new Soldier(Team.HAN, soldierMovement));

        PieceMovement endlessMovement = new EndlessMovement();

        locations.put(new Point(9, 0), new Chariot(Team.HAN, endlessMovement));
        locations.put(new Point(9, 8), new Chariot(Team.HAN, endlessMovement));

        locations.put(new Point(7, 1), new Cannon(Team.HAN, endlessMovement));
        locations.put(new Point(7, 7), new Cannon(Team.HAN, endlessMovement));

        PieceMovement elephantMovement = generateElephantMovement();

        locations.put(new Point(9, 1), new Elephant(Team.HAN, elephantMovement));
        locations.put(new Point(9, 7), new Elephant(Team.HAN, elephantMovement));

        PieceMovement horseMovement = generateHorseMovement();

        locations.put(new Point(9, 2), new Horse(Team.HAN, horseMovement));
        locations.put(new Point(9, 6), new Horse(Team.HAN, horseMovement));

        locations.put(new Point(8, 4), new General(Team.HAN));
        locations.put(new Point(9, 3), new Guard(Team.HAN));
        locations.put(new Point(9, 5), new Guard(Team.HAN));

        return locations;
    }

    private Map<Point, Piece> generateLocationsForCho() {
        final Map<Point, Piece> locations = new HashMap<>();
        PieceMovement soldierMovement = new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH)),
                new Route(List.of(Direction.EAST)),
                new Route(List.of(Direction.WEST))
        ));
        locations.put(new Point(3, 0), new Soldier(Team.CHO, soldierMovement));
        locations.put(new Point(3, 2), new Soldier(Team.CHO, soldierMovement));
        locations.put(new Point(3, 4), new Soldier(Team.CHO, soldierMovement));
        locations.put(new Point(3, 6), new Soldier(Team.CHO, soldierMovement));
        locations.put(new Point(3, 8), new Soldier(Team.CHO, soldierMovement));

        PieceMovement endlessMovement = new EndlessMovement();

        locations.put(new Point(0, 0), new Chariot(Team.CHO, endlessMovement));
        locations.put(new Point(0, 8), new Chariot(Team.CHO, endlessMovement));

        locations.put(new Point(2, 1), new Cannon(Team.CHO, endlessMovement));
        locations.put(new Point(2, 7), new Cannon(Team.CHO, endlessMovement));

        PieceMovement elephantMovement = generateElephantMovement();

        locations.put(new Point(0, 1), new Elephant(Team.CHO, elephantMovement));
        locations.put(new Point(0, 7), new Elephant(Team.CHO, elephantMovement));

        PieceMovement horseMovement = generateHorseMovement();

        locations.put(new Point(0, 2), new Horse(Team.CHO, horseMovement));
        locations.put(new Point(0, 6), new Horse(Team.CHO, horseMovement));

        locations.put(new Point(1, 4), new General(Team.CHO));
        locations.put(new Point(0, 3), new Guard(Team.CHO));
        locations.put(new Point(0, 5), new Guard(Team.CHO));

        return locations;
    }

    private static PieceMovement generateHorseMovement() {
        return new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH, Direction.NORTHWEST)),
                new Route(List.of(Direction.NORTH, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.NORTHWEST))
        ));
    }

    private PieceMovement generateElephantMovement() {
        return new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST)),
                new Route(List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.NORTHEAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.SOUTHWEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST))
        ));
    }
}
