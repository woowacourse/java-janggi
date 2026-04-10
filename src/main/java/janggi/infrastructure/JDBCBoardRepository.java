package janggi.infrastructure;

import janggi.domain.position.Position;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.PieceFactory;
import janggi.domain.space.piece.PieceType;
import janggi.domain.space.piece.Team;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dto.PieceDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCBoardRepository implements BoardRepository {
    private final GameDao gameDAO;

    public JDBCBoardRepository() {
        this.gameDAO = new GameDao();
    }

    @Override
    public void saveGame(long gameId, String turn, Map<Position, Piece> arrivePieces) {
        TransactionExecutor.execute(connection -> {
            // 기존 기물 정보 삭제
            gameDAO.deletePieceByGameId(connection, gameId);
            // 현재 턴 저장
            gameDAO.saveGameList(connection, gameId, turn);
            // 현재 기물 상태 정보 저장
            for (Map.Entry<Position, Piece> entry : arrivePieces.entrySet()) {
                PieceDto pieceDto = new PieceDto(
                        entry.getKey().x(),
                        entry.getKey().y(),
                        entry.getValue().toString(),
                        entry.getValue().getTeam().toString()
                );
                gameDAO.insertPieces(connection, gameId, pieceDto);
            }
        });
    }

    @Override
    public List<Long> findAllGameIds() {
        return TransactionExecutor.apply(gameDAO::findAllGameIds);
    }

    @Override
    public Map<Position, Piece> findPiecesByGameId(long gameId) {
        Map<Position, Piece> pieces = new HashMap<>();

        TransactionExecutor.execute(connection -> {
            List<PieceDto> pieceDtos = gameDAO.findPiecesByGameId(connection, gameId);
            for (PieceDto pieceDto : pieceDtos) {
                Position pos = new Position(pieceDto.x(), pieceDto.y());
                Piece piece = PieceFactory.createPiece(
                        Team.from(pieceDto.team()),
                        PieceType.from(pieceDto.pieceType())
                );
                pieces.put(pos, piece);
            }
        });

        return pieces;
    }

    @Override
    public String findTurnById(long gameId) {
        return TransactionExecutor.apply(connection -> gameDAO.findTurnByGameId(connection, gameId));
    }

    @Override
    public boolean existsById(long gameId) {
        return TransactionExecutor.apply(connection -> gameDAO.existsById(connection, gameId));
    }
}
