package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;

import java.util.LinkedHashMap;
import java.util.Map;

public class Board {

    private Map<Position, Piece> state = new LinkedHashMap<>();

    public Board() {
    }

    public void placePieces(Side side, Placement placement) {
        placeDefaultPieceBy(side);
        placeHorseAndElephant(side, placement);
    }

    public Piece findBy(Position position) {
        return state.get(position);
    }

    private void placeDefaultPieceBy(Side side) {
        if (side == Side.HAN) placeBySide(side, 10, -1);
        if (side == Side.CHO) placeBySide(side, 1, 1);
    }

    private void placeBySide(Side side, int startRow, int dy) {
        placeChariot(side, startRow);
        placeCounselor(side, startRow);
        placeGeneral(side, startRow, dy);
        placeCanon(side, startRow, dy);
        placePawn(side, startRow, dy);
    }

    private void placeChariot(Side side, int startRow) {
        state.put(Position.of(startRow, 1), Piece.of(side, PieceType.CHARIOT));
        state.put(Position.of(startRow, 9), Piece.of(side, PieceType.CHARIOT));
    }

    private void placeCounselor(Side side, int startRow) {
        state.put(Position.of(startRow, 4), Piece.of(side, PieceType.COUNSELOR));
        state.put(Position.of(startRow, 6), Piece.of(side, PieceType.COUNSELOR));
    }

    private void placeGeneral(Side side, int startRow, int dy) {
        state.put(Position.of(startRow + dy, 5), Piece.of(side, PieceType.GENERAL));
    }

    private void placeCanon(Side side, int startRow, int dy) {
        state.put(Position.of(startRow + dy * 2, 2), Piece.of(side, PieceType.CANON));
        state.put(Position.of(startRow + dy * 2, 8), Piece.of(side, PieceType.CANON));
    }

    private void placePawn(Side side, int startRow, int dy) {
        state.put(Position.of(startRow + dy * 3, 1), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 3), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 5), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 7), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 9), Piece.of(side, PieceType.PAWN));
    }

    private void placeHorseAndElephant(Side side, Placement placement) {
        if (side == Side.CHO) placeHorseAndElephantBySide(side, placement, 1);
        if (side == Side.HAN) placeHorseAndElephantBySide(side, placement, 10);
    }

    private void placeHorseAndElephantBySide(Side side, Placement placement, int row) {
        state.put(Position.of(row, 2), Piece.of(side, placement.getColumnTwoType()));
        state.put(Position.of(row, 3), Piece.of(side, placement.getColumnThreeType()));
        state.put(Position.of(row, 7), Piece.of(side, placement.getColumnSevenType()));
        state.put(Position.of(row, 8), Piece.of(side, placement.getColumnEightType()));
    }
}
