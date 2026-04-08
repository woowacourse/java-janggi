package janggi.infrastructure.repository;

import janggi.domain.Game;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.repository.GameRepository;
import janggi.domain.space.Position;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dao.PieceDao;
import janggi.infrastructure.dao.dto.GameEntity;
import janggi.infrastructure.dao.dto.PieceEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JdbcGameRepository(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    @Override
    public Long save(Game game) {
        Long gameId = saveGameEntity(game);
        savePieceEntities(gameId, game.getBoard());
        return gameId;
    }

    private Long saveGameEntity(Game game) {
        String choName = game.getPlayerNameBySide(Side.CHO).name();
        String hanName = game.getPlayerNameBySide(Side.HAN).name();
        String currentTurn = game.getCurrentSide().name();

        return gameDao.insertGame(choName, hanName, currentTurn);
    }

    private void savePieceEntities(Long gameId, Map<Position, Piece> board) {
        pieceDao.deleteAllByGameId(gameId);

        board.forEach(((position, piece) -> pieceDao.insertPiece(
                gameId,
                position.getX(),
                position.getY(),
                piece.getSide().name(),
                piece.getType().name()
        )));
    }

    @Override
    public Optional<Game> findById(Long gameId) {
        return gameDao.findById(gameId)
                .map(gameEntity -> restoreGame(gameEntity, pieceDao.findAllByGameId(gameId)));
    }

    private Game restoreGame(GameEntity gameEntity, List<PieceEntity> pieceEntities) {
        Board board = restoreBoard(pieceEntities);
        Players players = restorePlayers(gameEntity);
        return new Game(board, players);
    }

    private Board restoreBoard(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> boardMap = pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> Position.of(entity.x(), entity.y()),
                        entity -> PieceFactory.create(
                                PieceType.valueOf(entity.pieceType()),
                                Side.valueOf(entity.side())
                        )
                ));
        return new Board(boardMap);
    }

    private Players restorePlayers(GameEntity gameEntity) {
        Name choName = new Name(gameEntity.choPlayerName());
        Name hanName = new Name(gameEntity.hanPlayerName());
        Side currentTurn = Side.valueOf(gameEntity.currentTurn());

        return Players.createRestored(choName, hanName, currentTurn);
    }
}
