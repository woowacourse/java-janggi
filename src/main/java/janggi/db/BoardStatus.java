package janggi.db;

import janggi.piece.Piece;
import janggi.piece.PieceFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class BoardStatus {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pazz4321";

    private static final int EMPTY_BOARD_STATUS = 0;
    private static final int BOARD_STATUS_COUNT_INDEX = 1;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBoardStatusEmpty() {
        String sql = "SELECT COUNT(*) FROM board_status";

        try (final var connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(BOARD_STATUS_COUNT_INDEX) == EMPTY_BOARD_STATUS;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void saveBoardStatus(List<Piece> allPieces) {
        String sql = "INSERT INTO board_status (piece_name, team_name, piece_status, position_x, position_y) VALUES (?, ?, ?, ?, ?)";

        try (final var connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Piece piece : allPieces) {
                preparedStatement.setString(BoardStatusColumn.NAME_PIECE.getIndex(), piece.getName());
                preparedStatement.setString(BoardStatusColumn.NAME_TEAM.getIndex(), piece.getTeamName());
                preparedStatement.setString(BoardStatusColumn.NAME_STATUS.getIndex(), piece.getStatus().name());
                preparedStatement.setInt(BoardStatusColumn.POSITION_X.getIndex(), piece.getPosition().x());
                preparedStatement.setInt(BoardStatusColumn.POSITION_Y.getIndex(), piece.getPosition().y());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> loadBoardStatus() {
        String sql = "SELECT piece_name, team_name, piece_status, position_x, position_y FROM board_status";

        List<Piece> pieces = new ArrayList<>();
        try (final var connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String pieceName = resultSet.getString("piece_name");
                String teamName = resultSet.getString("team_name");
                String pieceStatus = resultSet.getString("piece_status");
                int positionX = resultSet.getInt("position_x");
                int positionY = resultSet.getInt("position_y");

                pieces.add(PieceFactory.createPiece(pieceName, teamName, pieceStatus, positionX, positionY));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pieces;
    }

    public void clearBoardStatus() {
        String sql = "TRUNCATE TABLE board_status";

        try (final var connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
