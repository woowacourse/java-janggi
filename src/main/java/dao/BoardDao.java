package dao;

import domain.Player;
import domain.Team;
import domain.board.Board;
import domain.board.BoardPoint;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import execptions.JanggiArgumentException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BoardDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Board getBoard() {
        final var query =
                "SELECT * FROM board " +
                        "INNER JOIN piece ON board.piece_id = piece.id " +
                        "INNER JOIN team ON piece.team_id = team.id";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            Map<BoardPoint, Piece> locations = new HashMap<>();

            while (resultSet.next()) {
                BoardPoint boardPoint = new BoardPoint(
                        Integer.parseInt(resultSet.getString("row_index")),
                        Integer.parseInt(resultSet.getString("column_index"))
                );

                String type = resultSet.getString("type");
                String teamName = resultSet.getString("name");

                Team team = teamName.equals("HAN") ? Team.HAN : Team.CHO;
                Piece piece = getPieceByType(type, team);

                locations.put(boardPoint, piece);
            }
            return new Board(locations);

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Piece getPieceByType(String type, Team team) {
        return switch (type) {
            case "Soldier" -> new Soldier(team);
            case "Elephant" -> new Elephant(team);
            case "Horse" -> new Horse(team);
            case "Cannon" -> new Cannon(team);
            case "Chariot" -> new Chariot(team);
            case "General" -> new General(team);
            case "Guard" -> new Guard(team);
            default -> throw new JanggiArgumentException("타입에 해당하는 기물이 존재하지 않습니다.");
        };
    }

    public List<Player> getPlayers() {
        final var query =
                "SELECT * FROM player " +
                        "INNER JOIN team ON team.id = player.team_id ";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Player> players = new ArrayList<>();

            while (resultSet.next()) {
                String teamName = resultSet.getString("name");
                Team team = teamName.equals("HAN") ? Team.HAN : Team.CHO;

                players.add(new Player(team));
            }
            return players;

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
