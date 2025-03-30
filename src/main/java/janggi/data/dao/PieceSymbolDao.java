package janggi.data.dao;

import janggi.piece.PieceSymbol;

public interface PieceSymbolDao {

    void save(PieceSymbol pieceSymbol);

    void saveAll(PieceSymbol... pieceSymbols);

    int findIdByName(String name);
}
