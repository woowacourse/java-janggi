package domain.piece;

import domain.PieceType;

public class Byeong implements Piece {

    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.BYEONG;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
