package janggi.domain;

import janggi.domain.piece.Piece;

public class Blank implements Space {

    @Override
    public boolean isBlank() {
        return true;
    }

    @Override
    public String displayValue() {
        return "●";
    }

    @Override
    public Piece asPiece() {
        throw new IllegalArgumentException("[ERROR] 빈칸은 기물이 아닙니다.");
    }
}
