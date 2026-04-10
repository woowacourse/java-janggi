package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.piece.strategy.MovingCondition;
import domain.position.Position;
import janggigame.ScoreBoard;

import java.util.LinkedHashMap;
import java.util.Map;

public class Board implements BoardState {

    private final Map<Position, Piece> state;

    public Board() {
        state = new LinkedHashMap<>();
    }

    public void put(Position position, Piece piece) {
        state.put(position, piece);
    }

    @Override
    public boolean isBlocked(Position currentPosition) {
        return state.containsKey(currentPosition);
    }

    @Override
    public Piece findBy(Position position) {
        return state.get(position);
    }

    public Map<Position, Piece> getState() {
        return Map.copyOf(state);
    }

    public void move(Position from, Position to, Side side) {
        validatePosition(from, to);
        Piece fromPiece = state.get(from);
        Piece toPiece = state.get(to);
        validateMoveBySide(side, fromPiece, toPiece);
        validatePieceCanMove(from, to, fromPiece);
        state.put(to, fromPiece);
        state.remove(from);
    }

    public boolean isJangGun(Side currentTurnSide) {
        Position generalPosition = findGeneralPosition(currentTurnSide);

        return state.entrySet().stream()
                .filter(entry -> !entry.getValue().isSameSide(currentTurnSide))
                .anyMatch(entry ->
                        entry.getValue().getMovingCondition().canMove(boardStateBySide(currentTurnSide), entry.getKey(), generalPosition)
                );
    }

    public boolean isEmptyGeneral(Side currentTurnSide) {
        return state.values().stream()
                .noneMatch(piece -> piece.isSameSide(currentTurnSide) && piece.isSamePieceType(PieceType.GENERAL));
    }

    public ScoreBoard calculateScore() {
        return ScoreBoard.from(getState());
    }

    private Position findGeneralPosition(Side currentTurnSide) {
        return state.entrySet().stream()
                .filter(entry -> entry.getValue().isSameSide(currentTurnSide) && entry.getValue().isSamePieceType(PieceType.GENERAL))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(currentTurnSide.getName() + "진영의 장군이 존재하지 않습니다."));
    }

    private void validatePieceCanMove(Position from, Position to, Piece fromPiece) {
        Position adjustedFrom = adjustPositionBySide(fromPiece.getSide(), from);
        Position adjustedTo = adjustPositionBySide(fromPiece.getSide(), to);

        if (!canMove(fromPiece, adjustedFrom, adjustedTo)) {
            throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
        }
    }

    private Position adjustPositionBySide(Side side, Position position) {
        if (side == Side.HAN) {
            return Position.rotate180from(position);
        }
        return position;
    }

    private boolean canMove(Piece piece, Position from, Position to) {
        MovingCondition movingCondition = piece.getMovingCondition();
        return movingCondition.canMove(boardStateBySide(piece.getSide()), from, to);
    }

    private BoardState boardStateBySide(Side side) {
        if (side == Side.CHO) {
            return this;
        }
        return new RotatedBoard(this);
    }

    private void validatePosition(Position from, Position to) {
        validateSamePosition(from, to);
        validateExistPiece(from);
    }

    private void validateSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("출발지와 목적지가 같을 수 없습니다.");
        }
    }

    private void validateExistPiece(Position from) {
        if (!state.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
    }

    private void validateMoveBySide(Side side, Piece fromPiece, Piece toPiece) {
        validateCanMoveSameSidePiece(side, fromPiece);
        validateDestinationIsNotSameSide(side, toPiece);
    }

    private void validateCanMoveSameSidePiece(Side side, Piece fromPiece) {
        if (!fromPiece.isSameSide(side)) {
            throw new IllegalArgumentException("본인 진영의 말만 이동할 수 있습니다.");
        }
    }

    private void validateDestinationIsNotSameSide(Side side, Piece toPiece) {
        if (toPiece != null && toPiece.isSameSide(side)) {
            throw new IllegalArgumentException("본인 진영의 말이 위치한 곳으로는 갈 수 없습니다.");
        }
    }

    public void placePieces(Side side, Placement placement) {
        placeDefaultPieceBy(side);
        placeHorseAndElephant(side, placement);
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
        state.put(adjustPositionBySide(side, Position.of(1, 2)), Piece.of(side, placement.getFirstPieceType()));
        state.put(adjustPositionBySide(side, Position.of(1, 3)), Piece.of(side, placement.getSecondPieceType()));
        state.put(adjustPositionBySide(side, Position.of(1, 7)), Piece.of(side, placement.getThirdPieceType()));
        state.put(adjustPositionBySide(side, Position.of(1, 8)), Piece.of(side, placement.getFourthPieceType()));
    }
}
