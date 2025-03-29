package domain.dao;

import domain.Country;
import domain.JanggiCoordinate;
import domain.piece.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JanggiCoordinateDao {
    private final Connection connection;

    public JanggiCoordinateDao(Connection connection) {
        this.connection = connection;
    }

    public void createCoordinateTableIfNotExist() {
        String createTableSQL = """
                CREATE TABLE if not exists coordinate(
                coordinate_id INT AUTO_INCREMENT PRIMARY KEY,
                piece_id INT NOT NULL,
                game_id INT NOT NULL,
                row_coordinate INT NOT NULL,
                col_coordinate INT NOT NULL,
                CONSTRAINT fk_coordinate_game_id FOREIGN KEY (game_id) REFERENCES game(game_id) on delete cascade,
                CONSTRAINT fk_coordinate_piece_id FOREIGN KEY (piece_id) REFERENCES piece(piece_id) on delete cascade
                );
                """;

        try (final Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ERROR] COORDINATE 테이블을 생성할 수 없음");
        }
    }

    public void insertPieceToCoordinate(int pieceId, JanggiCoordinate coordinate, int gameId) {
        String insertCoordinateSQL = "INSERT INTO coordinate (row_coordinate, col_coordinate, piece_id, game_id) values(?,?,?,?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(insertCoordinateSQL)) {
            preparedStatement.setInt(1, coordinate.row());
            preparedStatement.setInt(2, coordinate.col());
            preparedStatement.setInt(3, pieceId);
            preparedStatement.setInt(4, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ERROR] COORDINATE INSERT 실패");
        }
    }

    public Map<JanggiCoordinate, Piece> finaAllPieces(int gameId) {
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
            e.printStackTrace();
            System.out.println("[ERROR] PIECE 정보들을 불러올 수 없습니다.");
        }
        return board;
    }


    private Piece createPiece(String pieceType, String countryString) {
        Country country = Country.fromName(countryString);

        switch (pieceType) {
            case "차":
                return new Cha(country);
            case "마":
                return new Ma(country);
            case "상":
                return new Sang(country);
            case "포":
                return new Pho(country);
            case "사":
                return new Sa(country);
            case "궁":
                return new Gung(country);
            case "병":
                return new Byeong(country);
            default:
                throw new IllegalArgumentException("알 수 없는 기물 타입: " + pieceType);
        }
    }

    public void deleteCoordinatesByGameId(int gameId) {
        String deleteCoordinatesSQL = "DELETE FROM coordinate WHERE game_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteCoordinatesSQL)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[ERROR] COORDINATE 삭제 실패");
        }
    }
}
