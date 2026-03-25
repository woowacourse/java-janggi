package domain.game;

import domain.vo.Team;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team =  team;
        this.pieceType = pieceType;
    }
}
