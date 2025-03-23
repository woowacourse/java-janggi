package domain.board;

import domain.Coordinate;
import domain.piece.Country;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Board {

    private final Map<Coordinate, Piece> board;

    public Board() {
        this.board = new HashMap<>(BoardSettingUpStrategy.setUp());
    }

    public Board(Map<Coordinate, Piece> board) {
        this.board = board;
    }

    public void setUpHan(SettingUp settingUp) {
        board.putAll(settingUp.getStrategy().setUpHan());
    }

    public void setUpCho(SettingUp settingUp) {
        board.putAll(settingUp.getStrategy().setUpCho());
    }

    public void movePiece(Coordinate oldCoordinate, Coordinate newCoordinate) {
        validatePieceCoordinate(oldCoordinate);
        Piece piece = board.get(oldCoordinate);
        List<Coordinate> coordinates = piece.availableMovePositions(oldCoordinate, this);
        validateMoveCoordinate(newCoordinate, coordinates);
        board.put(newCoordinate, piece);
        board.remove(oldCoordinate);
    }

    public Piece findPieceByCoordinate(Coordinate coordinate) {
        return board.get(coordinate);
    }

    public String getPieceType(Coordinate coordinate) {
        validatePieceCoordinate(coordinate);
        return board.get(coordinate).getPieceName();
    }

    private void validateMoveCoordinate(Coordinate newCoordinate,
                                        List<Coordinate> coordinates) {
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

    public boolean isMyTeam(Coordinate from, Coordinate to) {
        return hasPiece(to) && board.get(from).getCountry() == board.get(to).getCountry();
    }

    public boolean isPho(Coordinate phoCoordinate) {
        return board.get(phoCoordinate).isPho();
    }

    public Map<Coordinate, Piece> getBoard() {
        return board;
    }

    public Country findCountryByCoordinate(Coordinate currCoordinate) {
        return board.get(currCoordinate).getCountry();
    }

    public void validateOriginCoordinate(Coordinate originCoordinate, Country currentTurn) {
        if (!hasPiece(originCoordinate)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        }
        if (findCountryByCoordinate(originCoordinate) != currentTurn) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물만 움직일 수 있습니다.");
        }
    }
}
