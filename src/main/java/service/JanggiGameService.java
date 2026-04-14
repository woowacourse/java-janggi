package service;

import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.JanggiGameRepository;
import domain.piece.PieceRepository;
import domain.piece.Position;
import domain.player.Player;
import domain.player.PlayerRepository;
import domain.player.Team;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiGameService {

    private final DataSource dataSource;
    private final JanggiGameRepository janggiGameRepository;
    private final PlayerRepository playerRepository;
    private final PieceRepository pieceRepository;

    private static final String SAVE_NEW_GAME_FAILED = "새 게임 저장에 실패했습니다.";
    private static final String DB_CONNECTION_FAILED = "DB 연결에 실패했습니다.";
    private static final String LOAD_GAME_NOT_FOUND = "불러올 게임이 없습니다.";
    private static final String LOAD_GAME_FAILED = "게임 불러오기에 실패했습니다.";
    private static final String SAVE_PIECE_MOVE_FAILED = "기물 이동 저장에 실패했습니다.";
    private static final String PLAYER_NOT_FOUND = "플레이어를 찾을 수 없습니다.";
    private static final String TRANSACTION_ROLLBACK_FAILED = "트랜잭션 롤백에 실패했습니다.";

    public JanggiGameService(
            final DataSource dataSource,
            final JanggiGameRepository janggiGameRepository,
            final PlayerRepository playerRepository,
            final PieceRepository pieceRepository
    ) {
        this.dataSource = dataSource;
        this.janggiGameRepository = janggiGameRepository;
        this.playerRepository = playerRepository;
        this.pieceRepository = pieceRepository;
    }

    public JanggiGame startNewGame(
            final Player choPlayer,
            final Player hanPlayer,
            final Board board
    ) {
        try (Connection connection = dataSource.getConnection()) {
            beginTransaction(connection);

            try {
                final long gameId = janggiGameRepository.save(connection);
                final List<Player> savedPlayers = playerRepository.saveAll(
                        connection,
                        gameId,
                        List.of(choPlayer, hanPlayer)
                );

                final Player savedChoPlayer = findByTeam(savedPlayers, Team.CHO);
                final Player savedHanPlayer = findByTeam(savedPlayers, Team.HAN);
                final JanggiGame game = JanggiGame.newGame(gameId, board, savedChoPlayer, savedHanPlayer);

                pieceRepository.saveAll(connection, gameId, board);

                commit(connection);
                return game;
            } catch (final Exception exception) {
                rollback(connection);
                throw new RuntimeException(SAVE_NEW_GAME_FAILED, exception);
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(DB_CONNECTION_FAILED, exception);
        }
    }

    public JanggiGame loadLatestGame() {
        try (Connection connection = dataSource.getConnection()) {
            final long gameId = latestGameId(connection);
            return janggiGameRepository.findById(connection, gameId);
        } catch (final SQLException exception) {
            throw new RuntimeException(LOAD_GAME_FAILED, exception);
        }
    }


    public void move(final JanggiGame game, final Position from, final Position to) {
        try (Connection connection = dataSource.getConnection()) {
            beginTransaction(connection);

            try {
                game.movePiece(from, to);

                janggiGameRepository.update(connection, game);
                playerRepository.update(connection, game.getGameId(), game.getPlayers());
                pieceRepository.deleteAllByGameId(connection, game.getGameId());
                pieceRepository.saveAll(connection, game.getGameId(), game.getBoard());

                commit(connection);
            } catch (final Exception exception) {
                rollback(connection);
                throw new RuntimeException(SAVE_PIECE_MOVE_FAILED, exception);
            }
        } catch (final SQLException exception) {
            throw new RuntimeException(DB_CONNECTION_FAILED, exception);
        }
    }


    private long latestGameId(final Connection connection) {
        return janggiGameRepository.findLatestGameId(connection)
                .orElseThrow(() -> new IllegalStateException(LOAD_GAME_NOT_FOUND));
    }

    private Player findByTeam(final List<Player> players, final Team team) {
        return players.stream()
                .filter(player -> player.getTeam() == team)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(PLAYER_NOT_FOUND));
    }


    private void beginTransaction(final Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    private void commit(final Connection connection) throws SQLException {
        connection.commit();
    }

    private void rollback(final Connection connection) {
        try {
            connection.rollback();
        } catch (final SQLException exception) {
            throw new RuntimeException(TRANSACTION_ROLLBACK_FAILED, exception);
        }
    }
}
