package janggi.dao;

import janggi.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PieceDao {

    private final MysqlConnection mysqlConnection;

    public PieceDao(final MysqlConnection mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    public List<PieceDto> getPiecesByGameId(final int gameId) {
        final String selectQuery = "SELECT pieceType, team, col_num, row_num FROM piece WHERE game_id = ?";
        
        try (final Connection connection = mysqlConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            List<PieceDto> pieceDtos = new ArrayList<>();
            while (resultSet.next()) {
                PieceDto pieceDto = toPieceDto(resultSet);
                pieceDtos.add(pieceDto);
            }
            return pieceDtos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PieceDto toPieceDto(final ResultSet resultSet) throws SQLException {
        String pieceType = resultSet.getString("pieceType");
        String team = resultSet.getString("team");
        int colNum = resultSet.getInt("col_num");
        int rowNum = resultSet.getInt("row_num");
        return new PieceDto(pieceType, team, colNum, rowNum);
    }
}
