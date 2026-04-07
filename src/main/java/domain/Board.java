package domain;

import domain.constant.Country;
import domain.constant.Palace;
import domain.constant.PieceType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(List<PieceType> choMaSang, List<PieceType> hanMaSang) {
        this.board = BoardFactory.createInitBoard(choMaSang, hanMaSang);
    }

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean move(Position start, Position end) {
        Piece startPiece = board.getOrDefault(start, Piece.getEmptyPiece());
        Piece endPiece = board.getOrDefault(end, Piece.getEmptyPiece());

        if (startPiece.isPalacePiece()) {
            Palace palace = Palace.from(startPiece.getCountry());
            if (!palace.isPalace(end)) {
                throw new IllegalArgumentException("장과 사는 궁성 내부에서만 이동 가능합니다.");
            }
        }

        if (!(startPiece.canMovePosition(start, end) && startPiece.isDifferentCountry(endPiece.getCountry())
                && startPiece.isAvailableRoute(getSameLine(startPiece, start, end), endPiece.getPieceType()))) {
            throw new IllegalArgumentException("말을 이동할 수 없습니다.");
        }

        boolean isEnd = checkJangRemove(end);
        removePiece(start);
        removePiece(end);
        board.put(end, startPiece);

        return isEnd;
    }

    private List<Piece> getSameLine(Piece startPiece, Position start, Position end) {
        PieceType pieceType = startPiece.getPieceType();
        if (!pieceType.equals(PieceType.PO) && !pieceType.equals(PieceType.CHA)) {
            return Collections.emptyList();
        }

        List<Piece> pieces = new ArrayList<>();
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            int startRow = start.getRow();
            int startCol = start.getCol();
            int endRow = end.getRow();
            int endCol = end.getCol();
            if (!(startRow == endRow || startCol == endCol)) {
                throw new IllegalArgumentException("포는 같은 줄만 이동 가능합니다.");
            }

            if (startRow == endRow) {
                int minCol = Math.min(startCol, endCol);
                int maxCol = Math.max(startCol, endCol);
                for (int i = minCol + 1; i < maxCol; i++) {
                    Position position = Position.create(startRow, i);
                    if (board.containsKey(position)) {
                        pieces.add(board.get(position));
                    }
                }
            }

            if (startCol == endCol) {
                int minRow = Math.min(startRow, endRow);
                int maxRow = Math.max(startRow, endRow);
                for (int i = minRow + 1; i < maxRow; i++) {
                    Position position = Position.create(i, startCol);
                    if (board.containsKey(position)) {
                        pieces.add(board.get(position));
                    }
                }
            }

            return pieces;
        }

        Palace palace = Palace.from(startPiece.getCountry());
        if (palace.isDiagonalPath(start, end)) {
            int diffRow = Math.abs(end.getRow() - start.getRow());
            if (diffRow == 2) {
                Position center = palace.getCenter();
                if (board.containsKey(center)) {
                    pieces.add(board.get(center));
                }
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

    public double calculateScore(Country country) {
        return board.values().stream()
                .filter(piece -> piece.getCountry() == country)
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private boolean checkJangRemove(Position end) {
        Piece piece = board.getOrDefault(end, Piece.getEmptyPiece());
        return piece.getPieceType() == PieceType.JANG;
    }

    public boolean checkEndPosition(Position end) {
        return board.getOrDefault(end, Piece.getEmptyPiece()).isEmpty();
    }
}
