package repository;

import domain.Player;
import domain.Team;
import domain.piece.Score;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    @Override
    public void save(final String gameName, final Player player) {
        final String query = "INSERT INTO player (team, game_name, score) VALUES (?, ?, ?)";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getTeam().name());
            preparedStatement.setString(2, gameName);
            preparedStatement.setInt(3, player.getScore().value());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Player> findAllByGameName(final String gameName) {
        final String query = "SELECT team, game_name, score FROM player WHERE game_name = ?";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameName);
            final var resultSet = preparedStatement.executeQuery();
            return mapResultSetToPlayers(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Player> mapResultSetToPlayers(final ResultSet result) {
        try {
            final List<Player> players = new ArrayList<>();
            while (result.next()) {
                final String team = result.getString("team");
                final int score = result.getInt("score");
                players.add(new Player(Team.valueOf(team), new Score(score)));
            }
            return players;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }
}
