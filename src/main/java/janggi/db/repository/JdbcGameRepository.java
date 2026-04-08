package janggi.db.repository;

import janggi.db.dao.JdbcGameDao;
import janggi.db.dao.JdbcPieceDao;
import janggi.db.entity.GameEntity;
import janggi.db.entity.PieceEntity;
import janggi.domain.board.Board;
import janggi.domain.board.MoveResult;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JdbcGameRepository implements GameRepository {

    private final JdbcGameDao gameDao;
    private final JdbcPieceDao pieceDao;

    public JdbcGameRepository(JdbcGameDao gameDao, JdbcPieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public Long save(JanggiGame game) {
        Long gameId = gameDao.insert(game.getTurnName());
        saveAllPieces(gameId, game.getBoard());

        return gameId;
    }

    private void saveAllPieces(Long gameId, Board board) {
        Map<Position, Piece> boardPieces = board.getBoard();
        List<PieceEntity> pieceEntities = boardPieces.entrySet().stream()
                .map(entry -> new PieceEntity(
                        null,
                        gameId,
                        entry.getValue().getPieceTypeName(),
                        entry.getValue().getTeamName(),
                        entry.getKey().getX(),
                        entry.getKey().getY()
                ))
                .toList();
        pieceDao.insertAll(gameId, pieceEntities);
    }

    @Override
    public void updateGame(Long gameId, JanggiGame game, MoveResult result) {
        gameDao.update(gameId, game.getTurnName());
        if (result.isCaptured()) {
            pieceDao.deleteByGameId(gameId, result.getTo().getX(), result.getTo().getY());
        }
        pieceDao.update(gameId,
                result.getFrom().getX(), result.getFrom().getY(),
                result.getTo().getX(), result.getTo().getY());
    }

    @Override
    public JanggiGame load(Long gameId) {
        GameEntity gameEntity = gameDao.selectById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("진행 중인 게임이 없습니다."));

        List<PieceEntity> pieceEntities = pieceDao.selectByGameId(gameEntity.getId());

        Map<Position, Piece> pieces = new LinkedHashMap<>();
        for (PieceEntity entity : pieceEntities) {
            Position position = new Position(entity.getPositionX(), entity.getPositionY());
            PieceType pieceType = PieceType.valueOf(entity.getPieceType());
            Team team = Team.valueOf(entity.getTeam());
            Piece piece = PieceFactory.create(pieceType, team);
            pieces.put(position, piece);
        }

        Board board = new Board(pieces);
        Team turnTeam = Team.valueOf(gameEntity.getTurn());
        return new JanggiGame(board, turnTeam);
    }

    @Override
    public List<Long> findAllGameIds() {
        return gameDao.selectAll().stream()
                .map(GameEntity::getId)
                .toList();
    }

    @Override
    public void delete(Long gameId) {
        gameDao.deleteById(gameId);
    }
}
