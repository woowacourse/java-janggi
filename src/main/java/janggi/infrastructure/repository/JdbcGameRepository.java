package janggi.infrastructure.repository;

import janggi.domain.Game;
import janggi.domain.MoveEvent;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.player.Name;
import janggi.domain.player.Player;
import janggi.domain.player.Players;
import janggi.domain.repository.GameRepository;
import janggi.domain.space.Position;
import janggi.dto.GameDto;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dao.MoveHistoryDao;
import janggi.infrastructure.dao.dto.GameSaveRequest;
import janggi.infrastructure.dao.dto.MoveEntity;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final GameDao gameDao;
    private final MoveHistoryDao moveHistoryDao;

    public JdbcGameRepository(GameDao gameDao, MoveHistoryDao moveHistoryDao) {
        this.gameDao = gameDao;
        this.moveHistoryDao = moveHistoryDao;
    }

    @Override
    public Long save(Game game) {
        try {
            return gameDao.insertGame(
                    new GameSaveRequest(
                            game.getPlayerNameBySide(Side.CHO).name(),
                            game.getPlayerNameBySide(Side.HAN).name(),
                            game.getPlayerFormationBySide(Side.CHO).name(),
                            game.getPlayerFormationBySide(Side.HAN).name(),
                            game.getCurrentSide().name()
                    )
            );
        } catch (SQLException e) {
            throw new RuntimeException("새 게임 저장 실패", e);
        }
    }

    @Override
    public void update(Long gameId, Game game) {
        try {
            gameDao.updateGame(gameId, game.getCurrentSide().name(), game.isPlaying());
            saveUncommittedEvents(gameId, game);
        } catch (SQLException e) {
            throw new RuntimeException("게임 업데이트 실패", e);
        }
    }

    private void saveUncommittedEvents(Long gameId, Game game) throws SQLException {
        List<MoveEvent> events = game.getUncommittedEvents();
        for (MoveEvent event : events) {
            moveHistoryDao.insertMove(gameId, event.source(), event.target());
        }
        game.clearEvents();
    }

    @Override
    public Optional<Game> findById(Long gameId) {
        return gameDao.findById(gameId).map(gameEntity -> {
            Formation choFormation = Formation.valueOf(gameEntity.choFormation());
            Formation hanFormation = Formation.valueOf(gameEntity.hanFormation());
            Players players = new Players(
                    new Player(new Name(gameEntity.choPlayerName()), Side.CHO, choFormation),
                    new Player(new Name(gameEntity.hanPlayerName()), Side.HAN, hanFormation)
            );
            Board initialBoard = BoardFactory.create(choFormation, hanFormation);
            Game game = Game.startNew(initialBoard, players);

            List<MoveEntity> moves;
            try {
                moves = moveHistoryDao.findAllByGameId(gameId);
            } catch (SQLException e) {
                throw new RuntimeException("이력 조회 중 오류 발생", e);
            }
            for (MoveEntity entity : moves) {
                Position source = Position.of(entity.sourceX(), entity.sourceY());
                Position target = Position.of(entity.targetX(), entity.targetY());
                game.move(source, target);
            }
            game.clearEvents();
            return game;
        });
    }

    @Override
    public List<GameDto> findAllGames() {
        return gameDao.findAll().stream()
                .map(entity -> new GameDto(
                        entity.id(),
                        entity.choPlayerName(),
                        entity.hanPlayerName(),
                        entity.currentTurn()))
                .toList();
    }
}
