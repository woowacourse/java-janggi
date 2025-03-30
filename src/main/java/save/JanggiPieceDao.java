package save;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

public class JanggiPieceDao {

    private static final String CANNOT_CREATE_TABLE = "테이블을 생성하는데 실패하였습니다";

    private final DatabaseConnection connection;

    public JanggiPieceDao(DatabaseConnection mySQConnection) {
        this.connection = mySQConnection;
        initiateTable();
    }

    private void initiateTable() {
        String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS piece (
                        id INT NOT NULL AUTO_INCREMENT,
                        janggi_turn_fk INT NOT NULL,
                        `row` INT NOT NULL,
                        `column` INT NOT NULL,
                        type VARCHAR(30) NOT NULL,
                        team VARCHAR(30) NOT NULL,
                        PRIMARY KEY (id),
                        KEY piece_janggi_turn_fk (janggi_turn_fk),
                        CONSTRAINT piece_janggi_turn_fk FOREIGN KEY (janggi_turn_fk) 
                            REFERENCES janggi_turn(id) 
                            ON DELETE CASCADE 
                            ON UPDATE CASCADE
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
                """;

        try (final var connection = this.connection.getConnection();
             final var statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(CANNOT_CREATE_TABLE, e);
        }
    }

    public void savePiece(Piece piece, int turn) {
        final var query = "INSERT INTO piece (janggi_turn_fk, `row`, `column`, type, team) VALUES("
                + "(SELECT id FROM janggi_turn WHERE janggi_turn.turn = ?),"
                + " ?, ?, ?, ?)";
        try (final var connection = this.connection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            JanggiPosition position = piece.position();
            int row = position.getRow();
            int column = position.getColumn();

            preparedStatement.setInt(1, turn);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, column);
            preparedStatement.setString(4, piece.type().name());
            preparedStatement.setString(5, piece.team().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
    }

    public Pieces findPiecesByTeamTurn(int turnId) {
        List<Piece> resultPieces = new ArrayList<>();
        final var query = "SELECT * FROM piece WHERE janggi_turn_fk = ?";
        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, turnId);
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("row");
                int column = resultSet.getInt("column");
                String type = resultSet.getString("type");
                String team = resultSet.getString("team");
                resultPieces.add(new Piece(new JanggiPosition(row, column), JanggiTypeMoveBehaviorMapper.from(type),
                        Team.from(team)));
            }
            return new Pieces(resultPieces);
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
    }

    public void deleteAll() {
        final var query = "DELETE FROM piece";
        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            final var resultSet = preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
    }
}
