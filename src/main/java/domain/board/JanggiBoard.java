package domain.board;

import domain.JanggiCoordinate;
import domain.piece.Country;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public final class JanggiBoard {
    public static final int ROW_SIZE = 10;
    public static final int COL_SIZE = 9;
    public static final int BOARD_MIN_SIZE = 1;

    private final Map<JanggiCoordinate, Piece> board;

    public JanggiBoard(Map<JanggiCoordinate, Piece> initBoard) {
        board = initBoard;
    }

    public void movePiece(JanggiCoordinate oldCoordinate, JanggiCoordinate newCoordinate) {
        validatePieceCoordinate(oldCoordinate);
        List<JanggiCoordinate> janggiCoordinates = board.get(oldCoordinate).availableMovePositions(oldCoordinate, this);
        validateMoveCoordinate(newCoordinate, janggiCoordinates);
        board.put(newCoordinate, board.get(oldCoordinate));
        board.remove(oldCoordinate);
    }

    public String getPieceType(JanggiCoordinate janggiCoordinate) {
        validatePieceCoordinate(janggiCoordinate);
        return board.get(janggiCoordinate).getPieceType();
    }

    private void validateMoveCoordinate(JanggiCoordinate newCoordinate,
                                        List<JanggiCoordinate> janggiCoordinates) {
        if (!janggiCoordinates.contains(newCoordinate)) {
            throw new IllegalArgumentException("[ERROR] 이동 불가능한 위치입니다.");
        }
    }

    private void validatePieceCoordinate(JanggiCoordinate janggiCoordinate) {
        if (!board.containsKey(janggiCoordinate)) {
            throw new IllegalArgumentException("[ERROR] 기물이 존재하지 않는 위치입니다.");
        }
    }

    public boolean isBlankCoordinate(JanggiCoordinate coordinate) {
        return !board.containsKey(coordinate);
    }

    public boolean hasPiece(JanggiCoordinate coordinate) {
        return board.containsKey(coordinate);
    }

    public boolean isMyTeam(JanggiCoordinate originCoordinate, JanggiCoordinate coordinate) {
        return hasPiece(coordinate) && board.get(originCoordinate).getCountry() == board.get(coordinate).getCountry();
    }

    public boolean isOutOfBoundary(JanggiCoordinate janggiCoordinate) {
        int row = janggiCoordinate.getRow();
        int col = janggiCoordinate.getCol();
        if (row < BOARD_MIN_SIZE || row > ROW_SIZE) {
            return true;
        }
        return col < BOARD_MIN_SIZE || col > COL_SIZE;
    }

    public boolean isPho(JanggiCoordinate phoCoordinate) {
        return board.get(phoCoordinate).isPho();
    }

    public Map<JanggiCoordinate, Piece> getBoard() {
        return board;
    }

    public Country findCountryByCoordinate(JanggiCoordinate currCoordinate) {
        return board.get(currCoordinate).getCountry();
    }

    public void validateOriginCoordinate(JanggiCoordinate originCoordinate, Country currentTurn) {
        if (!hasPiece(originCoordinate)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        }
        if (findCountryByCoordinate(originCoordinate) != currentTurn) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물만 움직일 수 있습니다.");
        }
    }
}
