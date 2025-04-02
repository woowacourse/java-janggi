package domain;

import domain.piece.Gung;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {
    public static final int ROW_SIZE = 10;
    public static final int COL_SIZE = 9;
    public static final int BOUNDARY_START = 1;

    private final Map<JanggiCoordinate, Piece> board;
    private final Map<JanggiCoordinate, List<Direction>> castleDirection;

    public JanggiBoard(Map<JanggiCoordinate, Piece> board) {
        this.board = board;
        this.castleDirection = initCastleDiagonalDirection();
    }

    public boolean isOccupied(JanggiCoordinate coordinate) {
        return board.containsKey(coordinate);
    }

    public Piece findPieceByCoordinate(JanggiCoordinate coordinate) {
        if (isOccupied(coordinate)) {
            return board.get(coordinate);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
    }

    public int getPieceScoreSum(Country country) {
        return board.values().stream()
                .filter(piece -> piece.getCountry() == country)
                .mapToInt(Piece::getScore)
                .sum();
    }

    public boolean isChoGungAlive() {
        return board.containsValue(new Gung(Country.CHO));
    }

    public boolean isHanGungAlive() {
        return board.containsValue(new Gung(Country.HAN));
    }

    public void movePiece(JanggiCoordinate from, JanggiCoordinate to) {
        Piece piece = findPieceByCoordinate(from);
        board.put(to, piece);
        board.remove(from);
    }

    public boolean isCastleCoordinate(JanggiCoordinate coordinate) {
        return isChoCastleCoordinate(coordinate) || isHanCastleCoordinate(coordinate);
    }

    public boolean castleCoordinateHasDirection(JanggiCoordinate coordinate, Direction direction) {
        return findCastleDirectionByCoordinate(coordinate).contains(direction);
    }

    private boolean isChoCastleCoordinate(JanggiCoordinate coordinate) {
        return ((coordinate.row() >= 8 && coordinate.row() <= 10) && (coordinate.col() >= 4 && coordinate.col() <= 6));
    }

    private boolean isHanCastleCoordinate(JanggiCoordinate coordinate) {
        return ((coordinate.row() >= 1 && coordinate.row() <= 3) && (coordinate.col() >= 4 && coordinate.col() <= 6));
    }

    private Map<JanggiCoordinate, List<Direction>> initCastleDiagonalDirection() {
        Map<JanggiCoordinate, List<Direction>> directions = new HashMap<>();

        // 한
        JanggiCoordinate hanLeftUp = new JanggiCoordinate(1, 4);
        directions.put(hanLeftUp, List.of(Direction.RIGHT_DOWN));

        JanggiCoordinate hanRightUp = new JanggiCoordinate(1, 6);
        directions.put(hanRightUp, List.of(Direction.LEFT_DOWN));

        JanggiCoordinate hanCenter = new JanggiCoordinate(2, 5);
        directions.put(hanCenter, List.of(Direction.LEFT_UP, Direction.RIGHT_UP, Direction.LEFT_DOWN, Direction.RIGHT_DOWN));

        JanggiCoordinate hanLeftDown = new JanggiCoordinate(3, 4);
        directions.put(hanLeftDown, List.of(Direction.RIGHT_UP));

        JanggiCoordinate hanRightDown = new JanggiCoordinate(3, 6);
        directions.put(hanRightDown, List.of(Direction.LEFT_UP));

        // 초
        JanggiCoordinate choLeftUp = new JanggiCoordinate(8, 4);
        directions.put(choLeftUp, List.of(Direction.RIGHT_DOWN));

        JanggiCoordinate choRightUp = new JanggiCoordinate(8, 6);
        directions.put(choRightUp, List.of(Direction.LEFT_DOWN));

        JanggiCoordinate choCenter = new JanggiCoordinate(9, 5);
        directions.put(choCenter, List.of(Direction.LEFT_UP, Direction.RIGHT_UP, Direction.LEFT_DOWN, Direction.RIGHT_DOWN));

        JanggiCoordinate choLeftDown = new JanggiCoordinate(10, 4);
        directions.put(choLeftDown, List.of(Direction.RIGHT_UP));

        JanggiCoordinate choRightDown = new JanggiCoordinate(10, 6);
        directions.put(choRightDown, List.of(Direction.LEFT_UP));

        return directions;
    }

    private List<Direction> findCastleDirectionByCoordinate(JanggiCoordinate coordinate) {
        return castleDirection.getOrDefault(coordinate, new ArrayList<>());
    }

    public List<JanggiCoordinate> getOccupiedCoordinates() {
        return board.keySet().stream().toList();
    }

    public List<Piece> getPieces() {
        return board.values().stream().toList();
    }

    public Map<JanggiCoordinate, Piece> getBoard() {
        return board;
    }
}
