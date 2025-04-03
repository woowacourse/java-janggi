package janggi.service;

import janggi.GameContext;
import janggi.GameId;
import janggi.GameStatus;
import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.Pieces;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Score;
import janggi.player.Team;
import janggi.player.Turn;
import janggi.repository.ConnectionProvider;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import janggi.repository.dto.GameDto;

import java.sql.Connection;
import java.util.List;

public class JanggiService {

    private final ConnectionProvider connectionProvider;
    private final Transaction transaction;
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public JanggiService(final ConnectionProvider connectionProvider,
                         final Transaction transaction,
                         final GameRepository gameRepository,
                         final PieceRepository pieceRepository) {
        this.connectionProvider = connectionProvider;
        this.transaction = transaction;
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public List<GameDto> getRunningGames() {
        final List<GameDto> runningGames = gameRepository.findAllRunning(connectionProvider.getConnection());
        if (runningGames.isEmpty()) {
            throw new IllegalStateException("저장된 게임이 없습니다");
        }
        return runningGames;
    }

    public GameContext createNewContext() {
        return GameContext.newGame(
                Players.create(Turn.start()));
    }

    public GameContext loadSavedContext(final GameId selectedId) {
        final Pieces pieces = Pieces.from(
                pieceRepository.findAllByGameId(connectionProvider.getConnection(), selectedId));

        final GameDto game = gameRepository.findById(connectionProvider.getConnection(), selectedId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디로 저장된 게임이 없습니다"));

        final Players players = Players.of(
                pieces,
                Turn.from(game.turn()),
                Score.from(game.choScore()),
                Score.from(game.hanScore()));

        return GameContext.loadGame(game, players);
    }

    public void movePiece(final Board board,
                          final Player player,
                          final Position departure,
                          final Position destination) {
        board.movePiece(player, departure, destination);
    }

    public void saveGameWithPieces(final GameContext gameContext) {
        transaction.execute(connectionProvider.getConnection(), connection -> {
            final GameId id = saveGameInfo(gameContext, connection);

            pieceRepository.deleteByGameId(connection, id);
            pieceRepository.saveAll(connection, id, gameContext.getAlivePieces().getPieces());
        });
    }

    public void finishGame(final GameId gameId) {
        gameRepository.updateStatusById(connectionProvider.getConnection(), gameId, GameStatus.FINISHED);
    }

    private GameId saveGameInfo(final GameContext gameContext, final Connection connection) {
        if (gameContext.isSaved()) {
            return gameRepository.save(
                    connection,
                    gameContext.getGameId(),
                    gameContext.getTurn(),
                    gameContext.getScore(Team.CHO),
                    gameContext.getScore(Team.HAN));
        }
        return gameRepository.save(
                connection,
                gameContext.getTurn(),
                gameContext.getScore(Team.CHO),
                gameContext.getScore(Team.HAN)
        );
    }


}
