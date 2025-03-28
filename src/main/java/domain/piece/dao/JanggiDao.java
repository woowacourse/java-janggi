package domain.piece.dao;

import domain.Country;
import domain.JanggiCoordinate;
import domain.piece.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JanggiDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void createTableIfNotExist() {
        String pieceCreateSql = """
                create table if not exists piece(
                piece_id int primary key auto_increment,
                country varchar(5) not null,
                piece_type varchar(5) not null
                );
                                
                """;
        String coordinateCreateSql = """
                create table if not exists coordinate(
                coordinate_id int primary key auto_increment, 
                row_coordinate int not null,
                col_coordinate int not null,
                piece_id int unique,
                foreign key (piece_id) references piece(piece_id) on delete cascade
                );
                """;

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(pieceCreateSql);
            statement.execute(coordinateCreateSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPiece(JanggiCoordinate coordinate, Piece piece) {
        String pieceInsertSql = "insert into piece (country, piece_type) values(?,?);";
        String coordinateInsertSql = "insert into coordinate (row_coordinate, col_coordinate, piece_id) values(?, ?, last_insert_id());";

        try (Connection connection = getConnection();
             PreparedStatement pieceStmt = connection.prepareStatement(pieceInsertSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement coordinateStmt = connection.prepareStatement(coordinateInsertSql)) {

            // piece 테이블에 데이터 삽입
            pieceStmt.setString(1, piece.getCountry().getName());
            pieceStmt.setString(2, piece.getPieceType().getName());

            // piece 데이터 삽입
            pieceStmt.executeUpdate();

            // piece_id를 얻기 위한 처리
            try (ResultSet generatedKeys = pieceStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // 좌표 데이터 삽입
                    coordinateStmt.setInt(1, coordinate.row());
                    coordinateStmt.setInt(2, coordinate.col());

                    // coordinate 데이터 삽입 실행
                    coordinateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Piece findPieceByCoordinate(JanggiCoordinate coordinate) {
        String findPieceSql = """
                select p.country, p.piece_type
                from piece p
                join coordinate c on p.piece_id = c.piece_id
                where c.row_coordinate = ? and c.col_coordinate = ?;
                """;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPieceSql)) {
            preparedStatement.setInt(1, coordinate.row());
            preparedStatement.setInt(2, coordinate.col());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String countryString = resultSet.getString(1);
                String pieceType = resultSet.getString(2);
                return createPiece(pieceType, countryString);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteTable() {
        String foreignKeyConstraints = "ALTER TABLE coordinate DROP FOREIGN KEY coordinate_ibfk_1";
        String coordinateDropSql = "drop table coordinate";
        String pieceDropSql = "drop table piece;";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(foreignKeyConstraints);
            statement.execute(pieceDropSql);
            statement.execute(coordinateDropSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<JanggiCoordinate, Piece> loadBoard() {
        String loadGameSql = """
                    SELECT c.row_coordinate, c.col_coordinate, p.country, p.piece_type
                    FROM coordinate c
                    JOIN piece p ON c.piece_id = p.piece_id;
                """;

        Map<JanggiCoordinate, Piece> board = new HashMap<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(loadGameSql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int row = resultSet.getInt("row_coordinate");
                int col = resultSet.getInt("col_coordinate");
                String countryString = resultSet.getString("country");
                String pieceType = resultSet.getString("piece_type");

                // ✅ PieceType 변환 및 객체 생성
                Piece piece = createPiece(pieceType, countryString);

                // ✅ Map에 추가
                board.put(new JanggiCoordinate(row, col), piece);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return board;
    }

    public void saveGame(Map<JanggiCoordinate, Piece> board) {
        board.entrySet()
                .stream().forEach(entry -> addPiece(entry.getKey(), entry.getValue()));
    }

    private Piece createPiece(String pieceType, String countryString) {
        Country country = Country.fromName(countryString); // 문자열을 열거형으로 변환

        return switch (pieceType) {
            case "차" -> new Cha(country);
            case "마" -> new Ma(country);
            case "상" -> new Sang(country);
            case "포" -> new Pho(country);
            case "사" -> new Sa(country);
            case "궁" -> new Gung(country);
            case "병" -> new Byeong(country);
            default -> throw new IllegalArgumentException("알 수 없는 기물 타입: " + pieceType);
        };
    }

    public void clearBoard() {
        String deleteCoordinatesSql = "delete from coordinate;";
        String deletePiecesSql = "delete from piece;";

        Map<JanggiCoordinate, Piece> board = new HashMap<>();

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteCoordinatesSql);
            statement.executeUpdate(deletePiecesSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
