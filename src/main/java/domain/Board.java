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
        if (pieceType.equals(PieceType.MA)) {
            return getMaRoute(start, end);
        }
        if (pieceType.equals(PieceType.SANG)) {
            return getSangRoute(start, end);
        }
        if (pieceType.equals(PieceType.CHA) || pieceType.equals(PieceType.PO)) {
            return getChaPoRoute(startPiece, start, end);
        }

        return Collections.emptyList();
    }

    private List<Piece> getChaPoRoute(Piece startPiece, Position start, Position end) {
        if (start.getRow() == end.getRow() || start.getCol() == end.getCol()) {
            return getStraightRoute(start, end);
        }
        return getDiagonalRoute(startPiece, start, end);
    }

    private List<Piece> getMaRoute(Position start, Position end) {
        List<Piece> pieces = new ArrayList<>();
        int diffRow = end.getRow() - start.getRow();
        int diffCol = end.getCol() - start.getCol();

        Position position;
        if (Math.abs(diffRow) == 2) {
            position = Position.create(start.getRow() + (diffRow / 2), start.getCol());
        } else {
            position = Position.create(start.getRow(), start.getCol() + (diffCol / 2));
        }

        if (board.containsKey(position)) {
            pieces.add(board.get(position));
        }

        return pieces;
    }

    private List<Piece> getSangRoute(Position start, Position end) {
        int diffRow = end.getRow() - start.getRow();
        int diffCol = end.getCol() - start.getCol();

        Position position;
        if (Math.abs(diffRow) == 3) {
            position = Position.create(start.getRow() + (diffRow / 3 * 2), start.getCol() + (diffCol / 2));
        } else {
            position = Position.create(start.getRow() + (diffRow / 2), start.getCol() + (diffCol / 3 * 2));
        }

        List<Piece> pieces = getMaRoute(start, position);
        if (board.containsKey(position)) {
            pieces.add(board.get(position));
        }

        return pieces;
    }

    private List<Piece> getStraightRoute(Position start, Position end) {
        if (start.getRow() == end.getRow()) {
            return getHorizontalRoute(start, end);
        }
        return getVerticalRoute(start, end);
    }

    private List<Piece> getHorizontalRoute(Position start, Position end) {
        List<Piece> pieces = new ArrayList<>();
        int row = start.getRow();
        int minCol = Math.min(start.getCol(), end.getCol());
        int maxCol = Math.max(start.getCol(), end.getCol());
        for (int i = minCol + 1; i < maxCol; i++) {
            Position position = Position.create(row, i);
            if (board.containsKey(position)) {
                pieces.add(board.get(position));
            }
        }

        return pieces;
    }

    private List<Piece> getVerticalRoute(Position start, Position end) {
        List<Piece> pieces = new ArrayList<>();
        int col = start.getCol();
        int minRow = Math.min(start.getRow(), end.getRow());
        int maxRow = Math.max(start.getRow(), end.getRow());
        for (int i = minRow + 1; i < maxRow; i++) {
            Position position = Position.create(i, col);
            if (board.containsKey(position)) {
                pieces.add(board.get(position));
            }
        }

        return pieces;
    }

    private List<Piece> getDiagonalRoute(Piece startPiece, Position start, Position end) {
        List<Piece> pieces = new ArrayList<>();
        Palace palace = Palace.from(startPiece.getCountry());

        if (palace.isDiagonalPath(start, end) && Math.abs(end.getRow() - start.getRow()) == 2) {
            Position center = palace.getCenter();
            if (board.containsKey(center)) {
                pieces.add(board.get(center));
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
