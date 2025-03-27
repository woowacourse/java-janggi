package janggi.db;

import janggi.dao.BoardDao;
import janggi.model.BoardInitializer;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBInitializer {

    public void init() {
        DBConnection dbConnection = new DBConnection();
        try (Connection connection = dbConnection.getConnection()) {
            connection.prepareStatement("DROP DATABASE IF EXISTS janggi;").executeUpdate();
            connection.prepareStatement("CREATE DATABASE IF NOT EXISTS janggi;").executeUpdate();
            connection.prepareStatement("USE janggi;").executeUpdate();
            connection.prepareStatement("CREATE TABLE turn(currentTeamColor VARCHAR(30));").executeUpdate();
            connection.prepareStatement("INSERT INTO turn(currentTeamColor) VALUES ('BLUE')").executeUpdate();
            connection.prepareStatement("""
                    CREATE TABLE board(
                        rowIndex int,
                        columnIndex int,
                        teamColor VARCHAR(30),
                        pieceType VARCHAR(30)
                    );
                    """).executeUpdate();
            BoardDao boardDao = new BoardDao();
            BoardInitializer boardInitializer = new BoardInitializer();
            boardDao.updateBoard(boardInitializer.init().generateOccupiedPositions());
            JOptionPane.showMessageDialog(null, "DB세팅 완료.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DBInitializer dbInitializer = new DBInitializer();
        dbInitializer.init();
    }
}
