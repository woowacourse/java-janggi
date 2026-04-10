package repository;

import domain.board.BoardPiece;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table if not exists piece (
                        game_id bigint not null,
                        camp varchar(20) not null,
                        piece_type varchar(20) not null,
                        position_x int not null,
                        position_y int not null,
                        primary key (game_id, position_x, position_y),
                        constraint fk_piece_game
                            foreign key (game_id) references game(id)
                            on delete cascade
                    )
                    """);
        }
    }

    public void replace(Connection connection, long gameId, List<BoardPiece> boardPieces) throws SQLException {
        deleteByGameId(connection, gameId);
        insert(connection, gameId, boardPieces);
    }

    public List<BoardPiece> findByGameId(Connection connection, long gameId) throws SQLException {
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

    private void deleteByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "delete from piece where game_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private void insert(Connection connection, long gameId, List<BoardPiece> boardPieces) throws SQLException {
        String sql = """
                insert into piece(game_id, camp, piece_type, position_x, position_y)
                values (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BoardPiece boardPiece : boardPieces) {
                statement.setLong(1, gameId);
                statement.setString(2, boardPiece.camp().name());
                statement.setString(3, boardPiece.pieceType().name());
                statement.setInt(4, boardPiece.position().x());
                statement.setInt(5, boardPiece.position().y());
                statement.addBatch();
            }

            statement.executeBatch();
        }
    }
}
