package domain.board;

import domain.piece.Piece;
import domain.piece.Side;
import domain.position.Position;
import dto.BoardResponseDto;
import dto.PieceDto;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> state;

    private Board(Map<Position, Piece> state) {
        this.state = state;
    }

    public static Board of(Map<Position, Piece> state) {
        return new Board(state);
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

    public Map<Position, Piece> getState() {
        return Map.copyOf(state);
    }

    public void move(Position startPosition, Position endPosition, Side side) {
        validatePosition(startPosition, endPosition);

        Piece fromPiece = state.get(startPosition);
        Piece toPiece = state.get(endPosition);

        validateMoveBySide(side, fromPiece, toPiece);
        validateCanMove(startPosition, endPosition, side, fromPiece);

        state.put(endPosition, fromPiece);
        state.remove(startPosition);
    }

    public double calculateScoreBy(Side side) {
        double score = state.values().stream()
                .filter(piece -> piece.isSameSide(side))
                .mapToDouble(Piece::getPieceScore)
                .sum();

        if (side.isHan()) return score + 1.5;
        return score;
    }

    public Side getWinner() {
        return state.values().stream()
                .filter(Piece::isGeneral)
                .map(Piece::getSide)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("승자가 존재하지 않습니다."));
    }

    public boolean isFinished() {
        int generalCount = (int) state.values().stream()
                .filter(Piece::isGeneral)
                .count();
        return generalCount != 2;
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
