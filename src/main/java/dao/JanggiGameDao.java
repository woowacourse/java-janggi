package dao;

import domain.TeamType;
import domain.game.dto.JanggiGameResponseDto;
import domain.player.Players;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JanggiGameDao {
    private final Connection connection;

    public JanggiGameDao(Connection connection) {
        this.connection = connection;
    }

    public List<JanggiGameResponseDto> findInProgressGames() {
        String query = "SELECT game_id, cho_player_name, han_player_name FROM janggi_game WHERE game_status = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, GameState.IN_PROGRESS.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapInProgressGameResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public Optional<TurnState> findTurnStateById(Long gameId) {
        String query = "SELECT * FROM janggi_game WHERE game_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapTurnState(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public long saveJanggiGame(Players players, TurnState turnState, GameState gameState) {
        String query = "INSERT INTO janggi_game (game_status, turn, cho_player_name, han_player_name, undo_last) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, gameState.name());
            preparedStatement.setString(2, turnState.playerTeam().name());
            preparedStatement.setString(3, players.getChoPlayerName());
            preparedStatement.setString(4, players.getHanPlayerName());
            preparedStatement.setBoolean(5, turnState.undoLast());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private List<JanggiGameResponseDto> mapInProgressGameResultSet(ResultSet resultSet) {
        List<JanggiGameResponseDto> inProgressGames = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long gameId = resultSet.getLong("game_id");
                String choPlayerName = resultSet.getString("cho_player_name");
                String hanPlayerName = resultSet.getString("han_player_name");
                inProgressGames.add(
                        new JanggiGameResponseDto(gameId, choPlayerName, hanPlayerName)
                );
            }
            return inProgressGames;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private Optional<TurnState> mapTurnState(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                boolean undoLast = resultSet.getBoolean("undo_last");
                TeamType teamType = TeamType.valueOf(resultSet.getString("turn"));
                return Optional.of(new TurnState(undoLast, teamType));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }
}
