package domain.board;

import domain.Coordinate;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Board {

    private final Map<Coordinate, Piece> board;

    public Board(BoardSettingUpStrategy hanSettingUpStrategy, BoardSettingUpStrategy choSettingUpStrategy) {
        Map<Coordinate, Piece> pieces = new HashMap<>(BoardSettingUpStrategy.setUp());
        pieces.putAll(hanSettingUpStrategy.setUpHan());
        pieces.putAll(choSettingUpStrategy.setUpCho());
        this.board = pieces;
    }

    public Board(Map<Coordinate, Piece> board) {
        this.board = board;
    }

    public void movePiece(Coordinate from, Coordinate to) {
        Piece piece = findPieceByCoordinate(from);

        List<Coordinate> availables = piece.availableMovePositions(from, this);
        validateMoveCoordinate(to, availables);

        board.put(to, piece);
        board.remove(from);
    }

    public Piece findPieceByCoordinate(Coordinate coordinate) {
        validatePieceCoordinate(coordinate);
        return board.get(coordinate);
    }

    public PieceType findPieceTypeByCoordinate(Coordinate coordinate) {
        validatePieceCoordinate(coordinate);
        return board.get(coordinate).getType();
    }

    private void validateMoveCoordinate(Coordinate newCoordinate, List<Coordinate> coordinates) {
        if (!coordinates.contains(newCoordinate)) {
            throw new IllegalArgumentException("[ERROR] 이동 불가능한 위치입니다.");
        }
    }

    private void validatePieceCoordinate(Coordinate coordinate) {
        if (!board.containsKey(coordinate)) {
            throw new IllegalArgumentException("[ERROR] 기물이 존재하지 않는 위치입니다.");
        }
    }

    public boolean isBlankCoordinate(Coordinate coordinate) {
        return !board.containsKey(coordinate);
    }

    public boolean hasPiece(Coordinate coordinate) {
        return board.containsKey(coordinate);
    }

    public boolean isMyTeam(Country country, Coordinate to) {
        return hasPiece(to) && country == findPieceByCoordinate(to).getCountry();
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
}
