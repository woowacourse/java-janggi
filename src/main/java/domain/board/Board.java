package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.PieceDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> state;

    public Board() {
        state = new LinkedHashMap<>();
    }

    public Piece findBy(Position position) {
        return state.get(position);
    }

    public BoardResponseDto findState() {
        return new BoardResponseDto(state.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> PieceDto.from(entry.getValue()))
                ));
    }

    public void move(Position from, Position to, Side side) {
        validatePosition(from, to);

        Piece fromPiece = state.get(from);
        Piece toPiece = state.get(to);

        validateMoveBySide(side, fromPiece, toPiece);
        validateCanMove(from, to, side, fromPiece);

        state.put(to, fromPiece);
        state.remove(from);
    }

    public void placePieces(Side side, Placement placement) {
        placeDefaultPieceBy(side);
        placeHorseAndElephant(side, placement);
    }

    public boolean isFinished() {
        int generalCount = (int) state.values().stream()
                .filter(Piece::isGeneral)
                .count();
        return generalCount != 2;
    }

    // ============= private method ==============

    private void placeDefaultPieceBy(Side side) {
        if (side.isHan()) placeBySide(side, 10, -1);
        if (side.isCho()) placeBySide(side, 1, 1);
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
        state.put(adjustPositionBySide(side, Position.of(1, 2)), Piece.of(side, placement.getFirstPieceType()));
        state.put(adjustPositionBySide(side, Position.of(1, 3)), Piece.of(side, placement.getSecondPieceType()));
        state.put(adjustPositionBySide(side, Position.of(1, 7)), Piece.of(side, placement.getThirdPieceType()));
        state.put(adjustPositionBySide(side, Position.of(1, 8)), Piece.of(side, placement.getFourthPieceType()));
    }

    private void validatePosition(Position from, Position to) {
        validateSamePosition(from, to);
        validateExistPiece(from);
    }

    private static void validateSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("출발지와 목적지가 같을 수 없습니다.");
        }
    }

    private void validateExistPiece(Position from) {
        if (!state.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
    }

    private static void validateMoveBySide(Side side, Piece fromPiece, Piece toPiece) {
        validateCanMoveSameSidePiece(side, fromPiece);
        validateCanCatchSameSidePiece(side, toPiece);
    }

    private void validateCanMove(Position from, Position to, Side side, Piece fromPiece) {
        if (!fromPiece.canMove(adjustStateBySide(side), adjustPositionBySide(side, from), adjustPositionBySide(side, to))) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }
    }

    private static void validateCanMoveSameSidePiece(Side side, Piece fromPiece) {
        if (!fromPiece.isSameSide(side)) {
            throw new IllegalArgumentException("본인 진영의 말만 이동할 수 있습니다.");
        }
    }

    private static void validateCanCatchSameSidePiece(Side side, Piece toPiece) {
        if (toPiece != null && toPiece.isSameSide(side)) {
            throw new IllegalArgumentException("본인 진영의 말은 포획할 수 없습니다.");
        }
    }

    private Position adjustPositionBySide(Side side, Position from) {
        if (side.isHan()) {
            return Position.rotate180from(from);
        }
        return from;
    }

    private Map<Position, Piece> adjustStateBySide(Side side) {
        if (side.isHan()) {
            return state.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> Position.rotate180from(entry.getKey()),
                            Map.Entry::getValue
                    ));
        }
        return state;
    }
}
