package domain.dao;

import domain.janggiboard.customstrategy.*;
import domain.piece.JanggiSide;
import util.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcJanggiGameDao implements JanggiGameDao {

    private final DatabaseConnector connector;

    public JdbcJanggiGameDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void addGame(final BoardArrangementStrategy strategyOfCho, final BoardArrangementStrategy strategyOfHan) {
        final String query = "INSERT INTO janggi_game(cho_strategy, han_strategy) VALUES(?, ?)";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, parseStrategy(strategyOfCho));
            preparedStatement.setInt(2, parseStrategy(strategyOfHan));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public int getGame() {
        final String query = "SELECT game_id FROM janggi_game";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("game_id");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 탐색에 실패했습니다.");
        }
        return -1;
    }

    public BoardArrangementStrategy findChoStrategyById(int gameId) {
        final String query = "SELECT cho_strategy FROM janggi_game where game_id = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseStrategy(resultSet.getInt("cho_strategy"), JanggiSide.CHO);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 탐색에 실패했습니다.");
        }
        throw new IllegalStateException("게임이 존재하지 않습니다.");
    }

    public BoardArrangementStrategy findHanStrategyById(int gameId) {
        final String query = "SELECT han_strategy FROM janggi_game where game_id = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseStrategy(resultSet.getInt("han_strategy"), JanggiSide.HAN);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 탐색에 실패했습니다.");
        }
        throw new IllegalStateException("게임이 존재하지 않습니다.");
    }

    public void deleteAll() {
        final String query = "DELETE FROM janggi_game";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삭제에 실패했습니다.");
        }
    }

    private BoardArrangementStrategy parseStrategy(int option, JanggiSide side) {
        if (option == 1) {
            return new LeftBoardArrangementStrategy(side);
        }
        if (option == 2) {
            return new RightBoardArrangementStrategy(side);
        }
        if (option == 3) {
            return new InnerBoardArrangementStrategy(side);
        }
        if (option == 4) {
            return new OuterBoardArrangementStrategy(side);
        }
        throw new IllegalArgumentException("상차림 입력이 올바르지 않습니다.");
    }

    private int parseStrategy(BoardArrangementStrategy strategy) {
        if (strategy instanceof LeftBoardArrangementStrategy) {
            return 1;
        }
        if (strategy instanceof RightBoardArrangementStrategy) {
            return 2;
        }
        if (strategy instanceof InnerBoardArrangementStrategy) {
            return 3;
        }
        if (strategy instanceof OuterBoardArrangementStrategy) {
            return 4;
        }
        throw new IllegalArgumentException("상차림 입력이 올바르지 않습니다.");
    }
}
