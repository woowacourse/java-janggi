package domain.dao;

import domain.participants.Player;
import domain.participants.Players;
import domain.piece.TeamType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import util.DBConnectionUtil;

public class PlayerDao {

    public void save(Player player){
        Connection connection = getConnection();
        String sql = "insert into player(username,team) values (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,player.getName());
            preparedStatement.setString(2,player.getTeamType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayers(Players players){
        save(players.getChoPlayer());
        save(players.getHanPlayer());
    }

    public Optional<Players> findPlayers(){
        Player choPlayer = findPlayer(TeamType.CHO);
        Player hanPlayer = findPlayer(TeamType.HAN);
        if(choPlayer!=null && hanPlayer != null){
            return Optional.of(Players.initialize(choPlayer,hanPlayer));
        }
        return Optional.empty();
    }

    private Player findPlayer(TeamType teamType){
        Connection connection = getConnection();
        String sql = "select * from player where team = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teamType.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Player(resultSet.getString("username"),TeamType.valueOf(resultSet.getString("team")));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection(){
        return DBConnectionUtil.getConnection();
    }
}
