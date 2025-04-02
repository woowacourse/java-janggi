package domain.board;

import domain.piece.coordiante.Coordinate;
import domain.board.setting.BoardFactory;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.piece.Country;
import domain.piece.Paths;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class Board implements ReadableBoard {

    private final Map<Coordinate, Piece> board;

    public Board(ChoSettingUpStrategy choSettingUpStrategy, HanSettingUpStrategy hanSettingUpStrategy) {
        Map<Coordinate, Piece> pieces = new BoardFactory().setUp();
        pieces.putAll(choSettingUpStrategy.setUpCho());
        pieces.putAll(hanSettingUpStrategy.setUpHan());
        this.board = pieces;
    }

    public Board(Map<Coordinate, Piece> board) {
        this.board = board;
    }

    public void movePiece(Coordinate from, Coordinate to) {
        Piece piece = findPieceByCoordinate(from);

        List<Coordinate> availablePaths = piece.findAvailablePaths(from, this);
        Paths paths = new Paths(availablePaths);
        paths.canMove(to);

        board.put(to, piece);
        board.remove(from);
    }

    @Override
    public boolean isMyTeam(Country country, Coordinate to) {
        return hasPiece(to) && country == findPieceByCoordinate(to).getCountry();
    }

    @Override
    public PieceType findPieceTypeByCoordinate(Coordinate coordinate) {
        validatePieceCoordinate(coordinate);
        return board.get(coordinate).getType();
    }

    @Override
    public boolean hasPiece(Coordinate coordinate) {
        return board.containsKey(coordinate);
    }

    public Piece findPieceByCoordinate(Coordinate coordinate) {
        validatePieceCoordinate(coordinate);
        return board.get(coordinate);
    }

    private void validatePieceCoordinate(Coordinate coordinate) {
        if (!board.containsKey(coordinate)) {
            throw new IllegalArgumentException("[ERROR] 기물이 존재하지 않는 위치입니다.");
        }
    }

    public boolean isBlankCoordinate(Coordinate coordinate) {
        return !board.containsKey(coordinate);
    }

    public Country findCountryByCoordinate(Coordinate currCoordinate) {
        return findPieceByCoordinate(currCoordinate).getCountry();
    }

    public void validateIsMyPiece(Coordinate from, Country currentCountry) {
        if (findCountryByCoordinate(from) != currentCountry) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물만 움직일 수 있습니다.");
        }
    }

    public boolean isChoGungDead() {
        return board.values().stream()
                .noneMatch(piece -> piece.getType() == PieceType.GUNG && piece.getCountry() == Country.CHO);
    }

    public boolean isHanGungDead() {
        return board.values().stream()
                .noneMatch(piece -> piece.getType() == PieceType.GUNG && piece.getCountry() == Country.HAN);
    }

    public int calculateHanScore() {
        return board.values().stream()
                .filter(Piece::isHan)
                .mapToInt(Piece::getScore)
                .sum();
    }

    public int calculateChoScore() {
        return board.values().stream()
                .filter(Piece::isCho)
                .mapToInt(Piece::getScore)
                .sum();
    }

    public Map<Coordinate, Piece> getBoard() {
        return board;
    }

    public boolean isEmpty() {
        return board.isEmpty();
    }
}
