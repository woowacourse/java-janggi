package repository;

import static domain.board.Board.MAX_COLUMN;
import static domain.board.Board.MAX_ROW;
import static domain.board.Board.MIN_COLUMN;
import static domain.board.Board.MIN_ROW;

import common.DatabaseException;
import domain.board.Board;
import domain.game.Game;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class H2GameRepository implements GameRepository {

    private static final String INSERT_GAME_SQL = """
            insert into games(cho_player_name, han_player_name, current_team)
            values (?, ?, ?)
            """;
    private static final String UPDATE_GAME_SQL = """
            update games
            set cho_player_name = ?, han_player_name = ?, current_team = ?
            where id = ?
            """;
    private static final String DELETE_BOARD_PIECES_SQL = "delete from board_pieces where game_id = ?";
    private static final String DELETE_CAUGHT_PIECES_SQL = "delete from caught_pieces where game_id = ?";
    private static final String INSERT_BOARD_PIECE_SQL = """
            insert into board_pieces(game_id, row_num, column_num, piece_type, team)
            values (?, ?, ?, ?, ?)
            """;
    private static final String INSERT_CAUGHT_PIECE_SQL = """
            insert into caught_pieces(game_id, sequence_num, piece_type, team)
            values (?, ?, ?, ?)
            """;
    private static final String FIND_GAME_SQL = """
            select cho_player_name, han_player_name, current_team
            from games
            where id = ?
            """;
    private static final String FIND_BOARD_PIECES_SQL = """
            select row_num, column_num, piece_type, team
            from board_pieces
            where game_id = ?
            """;
    private static final String FIND_CAUGHT_PIECES_SQL = """
            select sequence_num, piece_type, team
            from caught_pieces
            where game_id = ?
            order by sequence_num
            """;
    private static final String COUNT_GAMES_SQL = "select count(*) from games";

    private final ConnectionManager connectionManager;
    private final H2TableInitializer tableInitializer;

    public H2GameRepository() {
        this(new H2ConnectionManager());
    }

    public H2GameRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.tableInitializer = new H2TableInitializer(connectionManager);
        initialize();
    }

    @Override
    public long create(Game game) {
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            long gameId = saveNewGame(connection, game);
            saveBoardPieces(connection, gameId, game);
            saveCaughtPieces(connection, gameId, game);
            connection.commit();
            return gameId;
        } catch (SQLException e) {
            rollback(connection);
            throw new DatabaseException("게임 저장에 실패했습니다.");
        } finally {
            close(connection);
        }
    }

    @Override
    public void update(Game game, long gameId) {
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            updateGame(connection, gameId, game);
            deletePieces(connection, gameId);
            saveBoardPieces(connection, gameId, game);
            saveCaughtPieces(connection, gameId, game);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DatabaseException("게임 저장에 실패했습니다.");
        } finally {
            close(connection);
        }
    }

    @Override
    public Game findBy(long gameId) {
        try (Connection connection = connectionManager.getConnection()) {
            return createGame(connection, gameId);
        } catch (SQLException e) {
            throw new DatabaseException("저장된 게임을 불러올 수 없습니다.");
        }
    }

    @Override
    public List<GameInformation> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from games order by id");
             ResultSet resultSet = statement.executeQuery()) {
            List<GameInformation> gameInformations = new ArrayList<>();
            while (resultSet.next()) {
                gameInformations.add(createGameInformation(resultSet));
            }
            return gameInformations;
        } catch (SQLException e) {
            throw new DatabaseException("저장된 게임 목록을 읽을 수 없습니다.");
        }
    }

    private GameInformation createGameInformation(ResultSet resultSet) throws SQLException {
        return new GameInformation(
                resultSet.getLong("id"),
                resultSet.getString("cho_player_name"),
                resultSet.getString("han_player_name"),
                resultSet.getString("current_team")
        );
    }

    @Override
    public long count() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_GAMES_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DatabaseException("저장된 게임 목록을 읽을 수 없습니다.");
        }
    }

    private void initialize() {
        tableInitializer.initialize();
    }

    private long saveNewGame(Connection connection, Game game) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.getChoPlayerName());
            statement.setString(2, game.getHanPlayerName());
            statement.setString(3, game.getCurrentTeam().name());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DatabaseException("생성된 게임 ID를 찾을 수 없습니다.");
            }
            return generatedKeys.getLong(1);
        }
    }

    private void updateGame(Connection connection, long gameId, Game game) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_SQL)) {
            statement.setString(1, game.getChoPlayerName());
            statement.setString(2, game.getHanPlayerName());
            statement.setString(3, game.getCurrentTeam().name());
            statement.setLong(4, gameId);
            statement.executeUpdate();
        }
    }

    private void deletePieces(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement deleteBoardPiecesStatement = connection.prepareStatement(DELETE_BOARD_PIECES_SQL);
             PreparedStatement deleteCaughtPiecesStatement = connection.prepareStatement(DELETE_CAUGHT_PIECES_SQL)) {
            deleteBoardPiecesStatement.setLong(1, gameId);
            deleteBoardPiecesStatement.executeUpdate();

            deleteCaughtPiecesStatement.setLong(1, gameId);
            deleteCaughtPiecesStatement.executeUpdate();
        }
    }

    private void saveBoardPieces(Connection connection, long gameId, Game game) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BOARD_PIECE_SQL)) {
            for (Entry<Position, Piece> entry : game.getBoardMap().entrySet()) {
                Piece piece = entry.getValue();
                if (piece.isNone()) {
                    continue;
                }

                Position position = entry.getKey();
                statement.setLong(1, gameId);
                statement.setInt(2, position.row());
                statement.setInt(3, position.column());
                statement.setString(4, piece.getPieceType().name());
                statement.setString(5, piece.getTeam().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void saveCaughtPieces(Connection connection, long gameId, Game game) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CAUGHT_PIECE_SQL)) {
            List<Piece> caughtPieces = game.getCaughtPieces();

            for (int index = 0; index < caughtPieces.size(); index++) {
                Piece piece = caughtPieces.get(index);
                statement.setLong(1, gameId);
                statement.setInt(2, index);
                statement.setString(3, piece.getPieceType().name());
                statement.setString(4, piece.getTeam().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private Players createPlayers(String choPlayerName, String hanPlayerName) {
        return new Players(List.of(
                Player.of(choPlayerName, Team.CHO),
                Player.of(hanPlayerName, Team.HAN)
        ));
    }

    private Board createBoard(Connection connection, long gameId) throws SQLException {
        Map<Position, Piece> boardMap = createEmptyBoard();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BOARD_PIECES_SQL)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Position position = new Position(
                        resultSet.getInt("row_num"),
                        resultSet.getInt("column_num")
                );
                Piece piece = createPiece(
                        resultSet.getString("piece_type"),
                        resultSet.getString("team")
                );
                boardMap.put(position, piece);
            }
        }

        return new Board(boardMap);
    }

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), PieceType.NONE.create(null));
            }
        }
        return board;
    }

    private List<Piece> findCaughtPieces(Connection connection, long id) throws SQLException {
        List<Piece> caughtPieces = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_CAUGHT_PIECES_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                caughtPieces.add(createPiece(
                        resultSet.getString("piece_type"),
                        resultSet.getString("team")
                ));
            }
        }

        return caughtPieces;
    }

    private Game createGame(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_GAME_SQL)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DatabaseException("저장된 게임을 찾을 수 없습니다.");
            }

            Players players = createPlayers(
                    resultSet.getString("cho_player_name"),
                    resultSet.getString("han_player_name")
            );
            Team currentTeam = Team.valueOf(resultSet.getString("current_team"));
            Board board = createBoard(connection, gameId);
            List<Piece> caughtPieces = findCaughtPieces(connection, gameId);
            return Game.restore(players, board, caughtPieces, currentTeam);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

    private Piece createPiece(String pieceTypeName, String teamName) {
        Team team = Team.valueOf(teamName);
        PieceType pieceType = PieceType.valueOf(pieceTypeName);
        return pieceType.create(team);
    }
}
