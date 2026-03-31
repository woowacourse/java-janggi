package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(List<PieceType> choHan) {
        this.board = BoardFactory.createInitBoard(choHan);
    }

    public void move(Position start, Position end) {
        Piece startPiece = board.getOrDefault(start, Piece.getEmptyPiece());
        Piece endPiece = board.getOrDefault(end, Piece.getEmptyPiece());

        if (!(startPiece.canMovePosition(start, end) && startPiece.isDifferentCountry(endPiece.getCountry())
                && startPiece.isAvailableRoute(getSameLine(startPiece, start, end), endPiece.getPieceType()))) {
            throw new IllegalArgumentException("말을 이동할 수 없습니다.");
        }

        removePiece(start);
        removePiece(end);
        board.put(end, startPiece);
    }

    private List<Piece> getSameLine(Piece startPiece, Position start, Position end) {
        if (!startPiece.getPieceType().equals(PieceType.PO)) {
            return Collections.emptyList();
        }

        List<Piece> pieces = new ArrayList<>();
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        if (!(startX == endX || startY == endY)) {
            throw new IllegalArgumentException("포는 같은 줄만 이동 가능합니다.");
        }

        if (startX == endX) {
            int minY = Math.min(startY, endY);
            int maxY = Math.max(startY, endY);
            for (int i = minY + 1; i < maxY; i++) {
                pieces.add(board.getOrDefault(Position.create(startX, i), Piece.getEmptyPiece()));
            }
        }

        if (startY == endY) {
            int minX = Math.min(startX, endX);
            int maxX = Math.max(startX, endX);
            for (int i = minX + 1; i < maxX; i++) {
                pieces.add(board.getOrDefault(Position.create(i, startY), Piece.getEmptyPiece()));
            }
        }

        return pieces;
    }

    private void removePiece(Position endPosition) {
        board.remove(endPosition);
    }

    public PieceType getPieceType(Position position) {
        if (!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).getPieceType();
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(board);
    }

    public List<Position> getPiecesNowPosition(Country country, PieceType pieceType) {
        List<Position> positions = board.entrySet().stream()
                .filter(entry ->
                        entry.getValue().getPieceType() == pieceType &&
                                entry.getValue().getCountry() == country)
                .map(Map.Entry::getKey)
                .toList();
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 기물입니다. 장기판 위의 기물을 입력해주세요.");
        }
        return positions;
    }
}
