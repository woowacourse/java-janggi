package domain;

import domain.piece.Gung;
import domain.piece.Piece;

import java.util.Map;

public class JanggiBoard {
    public static final int ROW_SIZE = 10;
    public static final int COL_SIZE = 9;
    public static final int BOUNDARY_START = 1;

    private final Map<JanggiCoordinate, Piece> board;

    public JanggiBoard(Map<JanggiCoordinate, Piece> board) {
        this.board = board;
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

    private boolean isChoCastleCoordinate(JanggiCoordinate coordinate) {
        return ((coordinate.row() >= 8 && coordinate.row() <= 10) && (coordinate.col() >= 4 && coordinate.col() <= 6));
    }

    private boolean isHanCastleCoordinate(JanggiCoordinate coordinate) {
        return ((coordinate.row() >= 1 && coordinate.row() <= 3) && (coordinate.col() >= 4 && coordinate.col() <= 6));
    }
}
