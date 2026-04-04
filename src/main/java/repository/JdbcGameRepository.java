package repository;

import domain.board.BoardPiece;
import domain.board.Position;
import domain.game.Game;
import domain.piece.Camp;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final String url;
    private final GameMapper gameMapper;

    public JdbcGameRepository(String url) {
        this.url = url;
        this.gameMapper = new GameMapper();
        initialize();
    }

    @Override
    public void save(Game game) {
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);

            try {
                Optional<Long> foundGameId = findInProgressGameId(connection);

                long gameId = -1;

                if (foundGameId.isPresent()) {
                    gameId = foundGameId.get();
                    updateGame(connection, gameId, game);
                }

                if (foundGameId.isEmpty()) {
                    gameId = insertGame(connection, game);
                }

                replacePieces(connection, gameId, gameMapper.toBoardPieces(game));
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                throw new IllegalStateException("[ERROR] 게임 저장에 실패했습니다.");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] DB 연결에 실패했습니다.");
        }
    }


    @Override
    public Optional<Game> findInProgressGame() {
        String sql = """
                select id, current_turn, finished
                from game
                where finished = false
                order by id desc
                limit 1
                """;

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.next()) {
                return Optional.empty();
            }

            long gameId = resultSet.getLong("id");
            Camp currentTurn = Camp.valueOf(resultSet.getString("current_turn"));
            boolean finished = resultSet.getBoolean("finished");
            List<BoardPiece> boardPieces = findBoardPieces(connection, gameId);

            return Optional.of(gameMapper.toGame(currentTurn, finished, boardPieces));
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 게임 조회에 실패했습니다.");
        }
    }

    private void initialize() {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            statement.execute("""
                    create table if not exists game (
                        id bigint auto_increment primary key,
                        current_turn varchar(20) not null,
                        finished boolean not null
                    )
                    """);

            statement.execute("""
                    create table if not exists piece (
                        game_id bigint not null,
                        camp varchar(20) not null,
                        piece_type varchar(20) not null,
                        position_x int not null,
                        position_y int not null
                    )
                    """);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 테이블 생성에 실패했습니다.", exception);
        }
    }

    private Optional<Long> findInProgressGameId(Connection connection) throws SQLException {
        String sql = """
                select id
                from game
                where finished = false
                order by id desc
                limit 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(resultSet.getLong("id"));
        }
    }

    private long insertGame(Connection connection, Game game) throws SQLException {
        String sql = """
                insert into game(current_turn, finished)
                values (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.currentTurn().name());
            statement.setBoolean(2, game.isFinished());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    private void updateGame(Connection connection, long gameId, Game game) throws SQLException {
        String sql = """
                update game
                set current_turn = ?, finished = ?
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, game.currentTurn().name());
            statement.setBoolean(2, game.isFinished());
            statement.setLong(3, gameId);
            statement.executeUpdate();
        }
    }

    private void replacePieces(Connection connection, long gameId, List<BoardPiece> boardPieces) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement("delete from piece where game_id = ?")) {
            deleteStatement.setLong(1, gameId);
            deleteStatement.executeUpdate();
        }

        String insertSql = """
                insert into piece(game_id, camp, piece_type, position_x, position_y)
                values (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            for (BoardPiece boardPiece : boardPieces) {
                insertStatement.setLong(1, gameId);
                insertStatement.setString(2, boardPiece.camp().name());
                insertStatement.setString(3, boardPiece.pieceType().name());
                insertStatement.setInt(4, boardPiece.position().x());
                insertStatement.setInt(5, boardPiece.position().y());
                insertStatement.addBatch();
            }

            insertStatement.executeBatch();
        }
    }

    private List<BoardPiece> findBoardPieces(Connection connection, long gameId) throws SQLException {
        String sql = """
                select camp, piece_type, position_x, position_y
                from piece
                where game_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<BoardPiece> boardPieces = new ArrayList<>();

                while (resultSet.next()) {
                    Camp camp = Camp.valueOf(resultSet.getString("camp"));
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    Position position = new Position(
                            resultSet.getInt("position_x"),
                            resultSet.getInt("position_y")
                    );

                    boardPieces.add(new BoardPiece(position, camp, pieceType));
                }

                return boardPieces;
            }
        }
    }
}
