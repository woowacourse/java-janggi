package persistence;

import piece.Piece;
import piece.Pieces;

public interface JanggiPieceDao {

    void savePiece(Piece piece, int turn);

    Pieces findPiecesByTeamTurn(int turnId);

    void deleteAll();
}

