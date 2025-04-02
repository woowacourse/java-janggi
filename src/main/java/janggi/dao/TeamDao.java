package janggi.dao;

import janggi.dto.TeamTypeDto;
import janggi.domain.team.TeamType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {

    private final ConnectionManager connectionManager;

    public TeamDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insertInitialTeam(TeamType currentTeam) {
        final String query = "INSERT INTO team(name, current) VALUES(?, ?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (TeamType teamType : TeamType.values()) {
                preparedStatement.setString(1, teamType.getTitle());
                preparedStatement.setBoolean(2, teamType == currentTeam);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }


    public TeamTypeDto findTeamById(int id) {
        final String query = "SELECT * FROM team WHERE id = ?";

        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new TeamTypeDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("current")
                );
            }
            throw new SQLException("[ERROR] 데이터 조회에 실패하였습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TeamTypeDto> findTeams() {
        final String query = "SELECT * FROM team ORDER BY current desc";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            List<TeamTypeDto> teamTypes = new ArrayList<>();

            while (resultSet.next()) {
                teamTypes.add(new TeamTypeDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("current")
                ));
            }

            return teamTypes;
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
        }
    }

    public void updateTeamOrder(TeamType currentTeam) {
        final String query = """
                    UPDATE Team SET current = CASE 
                        WHEN name = ? THEN 1 
                        ELSE 0 
                    END
                """;

        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, currentTeam.getTitle());
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 팀 순서 변경에 실패하였습니다");
        }
    }

    public void deleteAllTeamIfExists() {
        final String query = "DELETE FROM team";
        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삭제에 실패하였습니다.");
        }
    }
}
