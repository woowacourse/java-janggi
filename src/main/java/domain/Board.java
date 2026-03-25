package domain;

import java.util.HashMap;
import java.util.Map;

import domain.pieces.Cha;
import domain.pieces.Jang;
import domain.pieces.Jol;
import domain.pieces.Ma;
import domain.pieces.None;
import domain.pieces.Po;
import domain.pieces.Sa;
import domain.pieces.Sang;

public class Board {
    private final Map<Position, Piece> board;

    public Board() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type == PieceType.NONE) continue;

            for (Position p : type.getChoPosition()) {
                pieces.put(p, createPiece(type, Country.CHO));
            }

            for (Position p : type.getHanPosition()) {
                pieces.put(p, createPiece(type, Country.HAN));
            }
        }

        this.board = pieces;
    }

    public PieceType getPiece(Position position) {
        if(!board.containsKey(position)) {
            return PieceType.NONE;
        }
        return board.get(position).getPieceType();
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
