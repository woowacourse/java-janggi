package repository;

import domain.board.Position;
import domain.piece.PieceInfo;
import dto.PieceSaveInfo;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface BoardRepository {
    void saveAll(Connection connection, Long gameId, List<PieceSaveInfo> pieceSaveInfos);

    void save(Connection connection, Long gameId, Position to, PieceInfo pieceInfo);

    void delete(Connection connection, Long gameId, Position from);

    Map<Position, PieceInfo> findAllByGameId(Connection connection, Long gameId);
}
