package janggi.domain.piece;

import janggi.domain.PieceType;
import janggi.domain.team.TeamType;

public interface Piece {
    PieceType getPieceType();
    TeamType getTeamType();
}
