package janggi.domain;

import janggi.domain.movestorage.*;
import janggi.exception.BusinessException;

public class PieceFactory {

    public static Piece create(String type, Team team) {
        if (type.equals("車")) {
            return new Piece(new ChaMoveStorage(), team, 13, "車");
        }
        if (type.equals("包")) {
            return new Piece(new PoMoveStorage(), team, 7, "包");
        }
        if (type.equals("馬")) {
            return new Piece(new MaMoveStorage(), team, 5, "馬");
        }
        if (type.equals("象")) {
            return new Piece(new SangMoveStorage(), team, 3, "象");
        }
        if (type.equals("士")) {
            return new Piece(new GungAndSaMoveStorage(), team, 3, "士");
        }
        if (type.equals("楚")) {
            return new Piece(new GungAndSaMoveStorage(), team, 0, "楚");
        }
        if (type.equals("漢")) {
            return new Piece(new GungAndSaMoveStorage(), team, 0, "楚");
        }
        if (type.equals("卒")) {
            return new Piece(new JolMoveStorage(), team, 2, "卒");
        }
        if (type.equals("兵")) {
            return new Piece(new JolMoveStorage(), team, 2, "卒");
        }
        throw new BusinessException("알 수 없는 기물 타입입니다: " + type);
    }
}
