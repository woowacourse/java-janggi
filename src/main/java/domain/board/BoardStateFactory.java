package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoardStateFactory {

    private final Setup hanSetup;
    private final Setup choSetup;

    public BoardStateFactory(Setup hanSetup, Setup choSetup) {
        this.hanSetup = hanSetup;
        this.choSetup = choSetup;
    }

    public Map<Position, Piece> create() {
        Map<Position, Piece> state = new LinkedHashMap<>();
        placePieces(state, Side.HAN, hanSetup);
        placePieces(state, Side.CHO, choSetup);
        return state;
    }

    private void placePieces(Map<Position, Piece> state, Side side, Setup setup) {
        placeDefaultPieceBy(state, side);
        placeHorseAndElephant(state, side, setup);
    }

    private void placeDefaultPieceBy(Map<Position, Piece> state, Side side) {
        if (side.isHan()) placeBySide(state, side, 10, -1);
        if (side.isCho()) placeBySide(state, side, 1, 1);
    }

    private void placeBySide(Map<Position, Piece> state, Side side, int startRow, int dy) {
        placeChariot(state, side, startRow);
        placeCounselor(state, side, startRow);
        placeGeneral(state, side, startRow, dy);
        placeCanon(state, side, startRow, dy);
        placePawn(state, side, startRow, dy);
    }

    private void placeChariot(Map<Position, Piece> state, Side side, int startRow) {
        state.put(Position.of(startRow, 1), Piece.of(side, PieceType.CHARIOT));
        state.put(Position.of(startRow, 9), Piece.of(side, PieceType.CHARIOT));
    }

    private void placeCounselor(Map<Position, Piece> state, Side side, int startRow) {
        state.put(Position.of(startRow, 4), Piece.of(side, PieceType.COUNSELOR));
        state.put(Position.of(startRow, 6), Piece.of(side, PieceType.COUNSELOR));
    }

    private void placeGeneral(Map<Position, Piece> state, Side side, int startRow, int dy) {
        state.put(Position.of(startRow + dy, 5), Piece.of(side, PieceType.GENERAL));
    }

    private void placeCanon(Map<Position, Piece> state, Side side, int startRow, int dy) {
        state.put(Position.of(startRow + dy * 2, 2), Piece.of(side, PieceType.CANON));
        state.put(Position.of(startRow + dy * 2, 8), Piece.of(side, PieceType.CANON));
    }

    private void placePawn(Map<Position, Piece> state, Side side, int startRow, int dy) {
        state.put(Position.of(startRow + dy * 3, 1), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 3), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 5), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 7), Piece.of(side, PieceType.PAWN));
        state.put(Position.of(startRow + dy * 3, 9), Piece.of(side, PieceType.PAWN));
    }

    private void placeHorseAndElephant(Map<Position, Piece> state, Side side, Setup setup) {
        List<PieceType> arrange = setup.arrange();
        state.put(adjustPositionBySide(side, Position.of(1, 2)), Piece.of(side, arrange.get(0)));
        state.put(adjustPositionBySide(side, Position.of(1, 3)), Piece.of(side, arrange.get(1)));
        state.put(adjustPositionBySide(side, Position.of(1, 7)), Piece.of(side, arrange.get(2)));
        state.put(adjustPositionBySide(side, Position.of(1, 8)), Piece.of(side, arrange.get(3)));
    }

    private Position adjustPositionBySide(Side side, Position from) {
        if (side.isHan()) {
            return Position.rotate180from(from);
        }
        return from;
    }
}
