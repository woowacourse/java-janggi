package repository;

import domain.board.Board;
import domain.board.Team;
import domain.game.Game;
import domain.game.Status;
import repository.dto.GameDto;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class GameJdbcDao implements GameDao {

    @Override
    public Game save(Connection con, Game game) {
        String sql = "insert into games(current_turn, status, created_at, updated_at) values(?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            OffsetDateTime now = OffsetDateTime.now();

            pstmt.setString(1, game.getCurrentTeam().name());
            pstmt.setString(2, game.getStatus().name());
            pstmt.setObject(3, now);
            pstmt.setObject(4, now);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Long generatedId = rs.getLong(1);
                    return Game.loadGame(generatedId, game.getBoard(), game.getCurrentTeam(), game.getStatus());
                }
            }
            throw new RuntimeException("ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Connection con, Long gameId, String turnName, String status) {
        String sql = "update games set current_turn = ?, status = ?, updated_at = ? where id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, turnName);
            pstmt.setString(2, status);
            pstmt.setObject(3, OffsetDateTime.now());
            pstmt.setLong(4, gameId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<GameDto> findAll(Connection con) {
        String sql = "select * from games";

        try (PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            try (ResultSet rs = pstmt.executeQuery()) {
                List<GameDto> gameDtos = new ArrayList<>();
                while (rs.next()) {
                    GameDto game = new GameDto(
                            rs.getLong("id"),
                            rs.getTimestamp("updated_at")
                                    .toInstant()
                                    .atZone(ZoneId.of("Asia/Seoul"))
                                    .toOffsetDateTime()
                    );
                    gameDtos.add(game);
                }
                return gameDtos;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Game findById(Connection con, Long gameId, Board board) {
        String sql = "select * from games where id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Game.loadGame(rs.getLong("id"),
                                board,
                                Team.valueOf(rs.getString("current_turn")),
                                Status.valueOf(rs.getString("status")
                            ));
                }
            }
            throw new RuntimeException("Game이 존재하지 않습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
