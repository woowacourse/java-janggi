package domain.board;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {

    public static final int MIN_POSITION = 1;
    private static final double HAN_PIECE_BONUS_SCORE = 1.5;
    private final Map<Position, Place> board;

    public Board(Map<Position, Place> board) {
        this.board = new HashMap<>(board);
    }

    public void move(Position from, Position to, Side side) {
        validateMove(from, to, side);

        Place fromPlace = board.get(from);

        if (!moveValid(from, to, fromPlace)) {
            throw new IllegalArgumentException("[ERROR] 기물이 가지 못하는 자리입니다.");
        }
        movePiece(from, to);
    }

    private boolean moveValid(Position from, Position to, Place fromPlace) {
        boolean normalMoveFlag = normalMoveValid(from, to, fromPlace);
        boolean palaceMoveFlag = palaceMoveValid(from, to, fromPlace);

        return normalMoveFlag || palaceMoveFlag;
    }

    private boolean normalMoveValid(Position from, Position to, Place fromPlace) {
        Map<Position, Place> obstacles = getObstacles(fromPlace.getNormalPath(from));
        return fromPlace.canNormalMove(obstacles, from, to);
    }

    private boolean palaceMoveValid(Position from, Position to, Place fromPlace) {
        Map<Position, Place> obstacles = getObstacles(fromPlace.getPalacePath(from));
        return fromPlace.canPalaceMove(obstacles, from, to);
    }

    private Map<Position, Place> getObstacles(List<Position> path) {
        return path.stream()
                .map(position -> Map.entry(position, board.get(position)))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private void validateMove(Position from, Position to, Side side) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로 이동 불가합니다.");
        }

        Place fromPlace = board.get(from);

        if (fromPlace.isEmpty() || !fromPlace.hasSide(side)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 기물 선택입니다.");
        }
    }

    private void movePiece(Position from, Position to) {
        Place piece = board.get(from);
        board.put(from, new Empty());
        board.put(to, piece);
    }

    public boolean isCheck(Side side) {
        Position generalPos = getGeneral(side);
        Side attackingSide = side.opposite();

        return board.entrySet().stream()
                .filter(e -> e.getValue().hasSide(attackingSide))
                .anyMatch(e -> moveValid(e.getKey(), generalPos, e.getValue()));
    }

    private Position getGeneral(Side side) {
        return board.entrySet().stream()
                .filter(e -> e.getValue().hasSide(side))
                .filter(e -> e.getValue().isSameSymbol(PieceSymbol.GENERAL))
                .map(Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 장군이 없습니다."));
    }

    public boolean isAliveGeneral(Side side) {
        return board.values().stream()
                .filter(p -> p.hasSide(side))
                .anyMatch(p -> p.isSameSymbol(PieceSymbol.GENERAL));
    }

    public double getSideScore(Side side) {
        double sum = board.values().stream()
                .filter(place -> place.hasSide(side))
                .mapToDouble(Place::getScore)
                .sum();
        if (side == Side.HAN) {
            sum += HAN_PIECE_BONUS_SCORE;
        }
        return sum;
    }

    public Map<Position, Place> board() {
        return Map.copyOf(board);
    }

    public List<List<String>> getFormatBoard() {
        return getBoard(Place::getFormat);
    }

    public List<List<Optional<Side>>> getSideBoard() {
        return getBoard(Place::getSide);
    }

    private <T> List<List<T>> getBoard(Function<Place, T> mapper) {
        int maxRow = board.keySet().stream()
                .mapToInt(Position::getRow)
                .max()
                .orElse(MIN_POSITION);
        int maxCol = board.keySet().stream()
                .mapToInt(Position::getColumn)
                .max()
                .orElse(MIN_POSITION);

        return getBoard(mapper, maxRow, maxCol);
    }

    private <T> List<List<T>> getBoard(Function<Place, T> mapper, int maxRow, int maxCol) {
        List<List<T>> result = new ArrayList<>();

        for (int row = MIN_POSITION; row <= maxRow; row++) {
            List<T> rowResult = new ArrayList<>();
            for (int col = MIN_POSITION; col <= maxCol; col++) {
                Place place = board.get(new Position(row, col));
                rowResult.add(mapper.apply(place));
            }
            result.add(rowResult);
        }
        return result;
    }

}
