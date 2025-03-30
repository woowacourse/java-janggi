package repository;

import domain.Team;
import domain.piece.Piece;
import domain.piece.Pieces;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PieceRepositoryImpl implements PieceRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    @Override
    public void saveAll(final String gameName, final Team team, final Pieces pieces) {
        for (final Piece piece : pieces.pieces()) {
            save(gameName, team, piece);
        }
    }

    @Override
    public void save(final String gameName, final Team team, final Piece piece) {
        final String query = "INSERT INTO piece (game_name, player_team, piece_type, row_value, column_value) VALUES (?, ?, ?, ?, ?)";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, team.name());
            preparedStatement.setString(3, piece.getCategory().name());
            preparedStatement.setInt(4, piece.getPosition().row());
            preparedStatement.setInt(5, piece.getPosition().column());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
