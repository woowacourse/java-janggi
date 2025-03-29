package janggi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JanggiDatabase {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user"; //  MySQL 서버 아이디
    private static final String PASSWORD = "password"; // MySQL 서버 비밀번호
    private static final String PIECE_TABLE = "CREATE TABLE PIECE ("
            + "ID INT AUTO_INCREMENT PRIMARY KEY,"
            + "TYPE VARCHAR(20) NOT NULL,"
            + "COUNTRY VARCHAR(20) NOT NULL"
            + ")";
    private static final String POSITION_TABLE = "CREATE TABLE POSITION ("
            + "ID INT AUTO_INCREMENT PRIMARY KEY,"
            + "X INT NOT NULL,"
            + "Y INT NOT NULL,"
            + "PIECE_ID INT,"
            + "FOREIGN KEY (PIECE_ID)"
            + "REFERENCES PIECE(ID)"
            + ")";
    private static final String EXISTS_TABLES_QUERY = "SELECT COUNT(*) "
            + "FROM INFORMATION_SCHEMA.TABLES "
            + "WHERE TABLE_SCHEMA='" + DATABASE + "' "
            + "AND (TABLE_NAME='PIECE' "
            + "OR TABLE_NAME='POSITION')";


    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createJanggiTables() {
        try (final Connection connection = getConnection()) {
            final PreparedStatement pieceTable = connection.prepareStatement(PIECE_TABLE);
            final PreparedStatement positionTable = connection.prepareStatement(POSITION_TABLE);

            pieceTable.execute();
            positionTable.execute();
        } catch (final SQLException e) {
            throw new IllegalStateException();
        }
    }

    public boolean existsJanggiTable() {
        try (final Connection connection = getConnection()) {
            final PreparedStatement tableExists = connection.prepareStatement(EXISTS_TABLES_QUERY);

            final ResultSet rs = tableExists.executeQuery();
            rs.next();
            return rs.getInt(1) == 2;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
