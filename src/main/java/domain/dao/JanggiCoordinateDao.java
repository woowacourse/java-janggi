package domain.dao;

import domain.Country;
import domain.JanggiCoordinate;
import domain.PieceType;
import domain.piece.Piece;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiCoordinateDao {
    private final Connection connection;

    public JanggiCoordinateDao(Connection connection) {
        this.connection = connection;
    }

    public void addPieceToCoordinateBatch(List<Integer> pieceIds, List<JanggiCoordinate> coordinates, int gameId) {
        String insertCoordinateSQL = "INSERT INTO coordinate (row_coordinate, col_coordinate, piece_id, game_id) values(?,?,?,?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(insertCoordinateSQL)) {
            for (int i = 0; i < pieceIds.size(); i++) {
                JanggiCoordinate coordinate = coordinates.get(i);

                preparedStatement.setInt(1, coordinate.row());
                preparedStatement.setInt(2, coordinate.col());
                preparedStatement.setInt(3, pieceIds.get(i));
                preparedStatement.setInt(4, gameId);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] COORDINATE INSERT 실패");
        }
    }

    public Map<JanggiCoordinate, Piece> findAllPieces(int gameId) {
        String findAllPieceSQL = """
                SELECT c.row_coordinate, c.col_coordinate, p.country, p.piece_type
                FROM coordinate c
                JOIN piece p ON c.piece_id = p.piece_id
                WHERE c.game_id = ?;
                """;
        Map<JanggiCoordinate, Piece> board = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(findAllPieceSQL)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int row = resultSet.getInt("row_coordinate");
                int col = resultSet.getInt("col_coordinate");

                String country = resultSet.getString("country");
                String pieceType = resultSet.getString("piece_type");

                Piece piece = createPiece(pieceType, country);
                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                board.put(coordinate, piece);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] PIECE 정보들을 불러올 수 없습니다.");
        }
        return board;
    }

    private Piece createPiece(String stringPieceType, String countryString) {
        Country country = Country.fromName(countryString);
        PieceType pieceType = PieceType.convertToPieceType(stringPieceType);

        return pieceType.createPiece(country);
    }

    public void deleteCoordinatesByGameId(int gameId) {
        String deleteCoordinatesSQL = "DELETE FROM coordinate WHERE game_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteCoordinatesSQL)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] COORDINATE 삭제 실패");
        }
    }
}
