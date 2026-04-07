package janggi.dao.piece;

import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PieceDao {

    void saveBoard(Connection con, Map<Position, Piece> boardInfo, Long gameId);

    Long savePiece(Connection con, Long gameId, String pieceType, int positionRow, int positionColumn, String team);

    List<PieceEntity> findAllPiecesByGameId(Connection con, long gameId);

    Optional<PieceEntity> findPieceByPosition(Connection con, Position position);

    void updatePieceOfPosition(Connection con, Long pieceId, Position to);

    void deletePieceByPosition(Connection con, Position position);
}


