package model.piece;

import model.Team;

@FunctionalInterface
public interface PieceConstructor {

    Piece construct(int x, int y, Team team);
}
