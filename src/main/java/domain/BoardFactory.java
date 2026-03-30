package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BoardFactory {
    public static Map<Position, Piece> createInitBoard(List<PieceType> choHan) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceType type : PieceType.values()) {
            if (type == PieceType.MA || type == PieceType.SANG || type == PieceType.NONE) {
                continue;
            }

            for (Position p : type.getChoPosition()) {
                board.put(p, new Piece(Country.CHO, type));
            }

            for (Position p : type.getHanPosition()) {
                board.put(p, new Piece(Country.HAN, type));
            }
        }

        for (MaSang maSang : MaSang.values()) {
            PieceType pieceType = choHan.get(maSang.getIndex());
            board.put(maSang.getPosition(), new Piece(maSang.getCountry(), pieceType));
        }

        return board;
    }
}
