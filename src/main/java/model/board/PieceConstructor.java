package model.board;

import model.Team;
import model.piece.Piece;

@FunctionalInterface
public interface PieceConstructor {

    Piece construct(int x, int y, Team team);
}
