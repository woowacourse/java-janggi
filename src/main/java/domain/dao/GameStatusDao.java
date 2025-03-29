package domain.dao;

import domain.GameStatus;
import domain.piece.TeamType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import util.DBConnectionUtil;

public class GameStatusDao {

    public void save(GameStatus gameStatus){
        String sql = "insert into game(room_name,turn) values(?,?)";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,gameStatus.getRoomName());
            preparedStatement.setString(2,gameStatus.getTurn().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<GameStatus> findGameStatusByRoomName(String roomName){
        String sql = "select * from game where room_name = ?";
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,roomName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(new GameStatus(resultSet.getString("room_name"),TeamType.valueOf(resultSet.getString("turn"))));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(String roomName, TeamType turn){
        Connection connection = getConnection();
        safeModeQuit(connection);
        updateGameStatus(connection,roomName,turn);
        safeModeSet(connection);
    }

    private void updateGameStatus(Connection connection,String roomName, TeamType turn){
        String sql = "update game set turn = ? where room_name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,turn.name());
            preparedStatement.setString(2,roomName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeQuit(Connection connection) {
        String safeModeQuit = "SET SQL_SAFE_UPDATES = 0";
        try {
            Statement statement = connection.createStatement();
            statement.execute(safeModeQuit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeSet(Connection connection) {
        String safeModeSet = "SET SQL_SAFE_UPDATES = 1";
        try {
            Statement statement = connection.createStatement();
            statement.execute(safeModeSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection(){
        return DBConnectionUtil.getConnection();
    }
}
