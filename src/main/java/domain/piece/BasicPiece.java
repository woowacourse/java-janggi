package domain.piece;

import domain.player.Team;

public interface BasicPiece {
    boolean isDifferentTeam(BasicPiece other);
    boolean isDifferentTeam(Team team);
    boolean isType(PieceType type);
    boolean isNone();
    Team getTeam();
    PieceType getPieceType();
}

