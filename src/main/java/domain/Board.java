package domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (!(startPiece.canMovePosition(start, end) && startPiece.isDifferentCountry(endPiece.getCountry()))) {
            throw new IllegalArgumentException("말을 이동할 수 없습니다.");
        }
        ;
        // TODO: 포와 졸 이동 로직 추가 필요
        killPiece(start);
        killPiece(end);
        board.put(end, startPiece);
    }

    private void killPiece(Position endPosition) {
        board.put(endPosition, None.INSTANCE);
    }

    public PieceType getPiece(Position position) {
        if (!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).getPieceType();
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

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
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
