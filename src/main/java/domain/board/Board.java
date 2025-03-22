package domain.board;

import static domain.Coordinate.BOARD_MIN_SIZE;
import static domain.Coordinate.COL_SIZE;
import static domain.Coordinate.ROW_SIZE;

import domain.Coordinate;
import domain.piece.Country;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public final class Board {

    private final Map<Coordinate, Piece> board;

    public Board(BoardInitStrategy boardInitStrategy) {
        board = boardInitStrategy.initialize();
    }

    public void movePiece(Coordinate oldCoordinate, Coordinate newCoordinate) {
        validatePieceCoordinate(oldCoordinate);
        Piece piece = board.get(oldCoordinate);
        List<Coordinate> coordinates = piece.availableMovePositions(oldCoordinate, this);
        validateMoveCoordinate(newCoordinate, coordinates);
        board.put(newCoordinate, piece);
        board.remove(oldCoordinate);
    }

    public String getPieceType(Coordinate coordinate) {
        validatePieceCoordinate(coordinate);
        return board.get(coordinate).getPieceType();
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

    public boolean isMyTeam(Coordinate originCoordinate, Coordinate coordinate) {
        return hasPiece(coordinate) && board.get(originCoordinate).getCountry() == board.get(coordinate).getCountry();
    }

    public boolean isOutOfBoundary(Coordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();
        if (row < BOARD_MIN_SIZE || row > ROW_SIZE) {
            return true;
        }
        return col < BOARD_MIN_SIZE || col > COL_SIZE;
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
