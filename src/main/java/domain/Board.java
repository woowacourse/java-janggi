package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import domain.pieces.Cha;
import domain.pieces.Jang;
import domain.pieces.Jol;
import domain.pieces.Ma;
import domain.pieces.None;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Sa;
import domain.pieces.Sang;

public class Board {
    private final Map<Position, Piece> board;

    public Board(List<PieceType> choHan) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type == PieceType.MA || type == PieceType.SANG || type == PieceType.NONE) {
                continue;
            }

            for (Position p : type.getChoPosition()) {
                pieces.put(p, createPiece(type, Country.CHO));
            }

            for (Position p : type.getHanPosition()) {
                pieces.put(p, createPiece(type, Country.HAN));
            }
        }

        for (MaSang maSang : MaSang.values()) {
            PieceType pieceType = choHan.get(maSang.getIndex());
            pieces.put(maSang.getPosition(), createPiece(pieceType, maSang.getCountry()));
        }

        this.board = pieces;
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
        if (board.get(start).getPieceType() != PieceType.PO ) {
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

    private Piece createPiece(PieceType type, Country country) {
        return switch (type) {
            case CHA -> new Cha(country);
            case MA -> new Ma(country);
            case SANG -> new Sang(country);
            case SA -> new Sa(country);
            case JANG -> new Jang(country);
            case PO -> new Po(country);
            case JOL -> new Jol(country);
            case NONE -> None.INSTANCE;
        };
    }
}
