package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.MaSangPosition;
import domain.enums.PieceType;
import domain.pieces.None;
import domain.pieces.Piece;
import domain.pieces.PieceFactory;
import domain.pieces.Po;

public class Board {
    private final Map<Position, Piece> board;

    public Board(List<PieceType> choHan) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type == PieceType.MA || type == PieceType.SANG || type == PieceType.NONE) {
                continue;
            }

            for (Position p : type.getChoPosition()) {
                pieces.put(p, PieceFactory.createPiece(type, Country.CHO));
            }

            for (Position p : type.getHanPosition()) {
                pieces.put(p, PieceFactory.createPiece(type, Country.HAN));
            }
        }

        for (MaSangPosition maSangPosition : MaSangPosition.values()) {
            PieceType pieceType = choHan.get(maSangPosition.getIndex());
            pieces.put(maSangPosition.getPosition(), PieceFactory.createPiece(pieceType, maSangPosition.getCountry()));
        }

        this.board = pieces;
    }

    public List<Position> findAvailablePositions(Position start) {
        List<Position> positions = new ArrayList<>();
        List<Position> possiblePosition = new ArrayList<>();
        Piece startPiece = board.getOrDefault(start, None.INSTANCE);

        Piece piece=Objects.requireNonNull(board.get(start));
        for (Direction direction : Direction.getCardinalDirections()){
            for(Position end : piece.getAvailableRoute(start,direction)){
                Piece endPiece = board.getOrDefault(end, None.INSTANCE);
                if (!(startPiece.canMovePosition(start, end) && startPiece.isDifferentCountry(endPiece.getCountry())
                        && startPiece.isAvailableRoute(getSameLine(start, end), endPiece.getPieceType()))) {
                    break;
                }
                possiblePosition.add(end);
            }
        }



        return possiblePosition;
    }

    public void move(Position start, Position end) {
        Piece startPiece = board.getOrDefault(start, None.INSTANCE);
        Piece endPiece = board.getOrDefault(end, None.INSTANCE);

        if (!(startPiece.canMovePosition(start, end) && startPiece.isDifferentCountry(endPiece.getCountry())
                && startPiece.isAvailableRoute(getSameLine(start, end), endPiece.getPieceType()))) {
            throw new IllegalArgumentException("말을 이동할 수 없습니다.");
        }

        killPiece(start);
        killPiece(end);
        board.put(end, startPiece);
    }

    private List<Piece> getSameLine(Position start, Position end) {
        List<Piece> pieces = new ArrayList<>();
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        if (!(startX == endX || startY == endY)) {
            return Collections.emptyList();
        }

        if (startX == endX) {
            int minY = Math.min(startY, endY);
            int maxY = Math.max(startY, endY);
            for (int i = minY + 1; i < maxY; i++) {
                pieces.add(board.getOrDefault(Position.create(startX, i), None.INSTANCE));
            }
        }

        if (startY == endY) {
            int minX = Math.min(startX, endX);
            int maxX = Math.max(startX, endX);
            for (int i = minX + 1; i < maxX; i++) {
                pieces.add(board.getOrDefault(Position.create(i, startY), None.INSTANCE));
            }
        }

        return pieces;
    }

    private void killPiece(Position endPosition) {
        board.remove(endPosition);
    }

    public PieceType getPiece(Position position) {
        if (!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).getPieceType();
    }

    public Country getPieceCountry(Position position) {
        if (!board.containsKey(position)) {
            return Country.NONE;
        }
        return board.get(position).getCountry();
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
