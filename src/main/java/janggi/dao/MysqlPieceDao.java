package janggi.dao;

import janggi.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class MysqlPieceDao implements PieceDao {

    @Override
    public List<PieceDto> findPiecesByGameId(final Connection connection, final int gameId) {
        String selectQuery = "SELECT pieceType, team, col_num, row_num FROM piece WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

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

    @Override
    public void addPieces(final Connection connection, final int gameId, final List<PieceDto> pieceDtos) {
        String insertPieceQuery = "INSERT INTO piece (game_id, pieceType, team, col_num, row_num) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPieceQuery)) {

            for (PieceDto pieceDto : pieceDtos) {
                preparedStatement.setInt(1, gameId);
                preparedStatement.setString(2, pieceDto.pieceType());
                preparedStatement.setString(3, pieceDto.team());
                preparedStatement.setInt(4, pieceDto.colNum());
                preparedStatement.setInt(5, pieceDto.rowNum());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePiecesByGameId(final Connection connection, final int gameId) {
        String deleteQuery = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
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
