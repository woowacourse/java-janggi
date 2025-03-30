package janggi.dao;

import janggi.dto.PieceDto;
import java.util.List;

public interface PieceDao {

    List<PieceDto> findPiecesByGameId(final int gameId);

    void addPieces(final int gameId, final List<PieceDto> pieceDtos);

    void deletePiecesByGameId(final int gameId);
}
