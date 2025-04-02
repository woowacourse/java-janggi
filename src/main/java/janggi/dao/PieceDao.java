package janggi.dao;

import janggi.dto.PieceDto;
import java.sql.Connection;
import java.util.List;

public interface PieceDao {

    List<PieceDto> findPiecesByGameId(final Connection connection, int gameId);

    void addPieces(final Connection connection, final int gameId, final List<PieceDto> pieceDtos);

    void deletePiecesByGameId(final Connection connection, final int gameId);
}
