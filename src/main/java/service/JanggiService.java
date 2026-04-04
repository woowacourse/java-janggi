package service;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Position;
import domain.board.BoardStates;
import domain.country.CountryType;
import domain.piece.Piece;
import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JanggiService {
    private static final String URL = "jdbc:mysql://localhost:3306/janggi";
    private static final String USER = "root";
    private static final String PASSWORD = "0502";

    public static void deleteAll() {
        String deleteBoardState = "TRUNCATE TABLE board_state";
        String deleteBoardSnapshot = "TRUNCATE TABLE board_snapshot";
        String deletePosition = "TRUNCATE TABLE position";
        String deletePiece = "TRUNCATE TABLE piece";
        String deleteBoard = "DELETE FROM board";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteBoardState);
            statement.executeUpdate(deleteBoardSnapshot);
            statement.executeUpdate(deletePosition);
            statement.executeUpdate(deletePiece);
            statement.executeUpdate(deleteBoard);
            statement.executeUpdate("ALTER TABLE board AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    // 포지션 초기화용
    public static void insertPositions() {
        String sql = "INSERT INTO `position` (`x`, `y`) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int y = INITIAL_POSITION; y <= Y_MAXIMUM_POSITION; y++) {
                for (int x = INITIAL_POSITION; x <= X_MAXIMUM_POSITION; x++) {
                    preparedStatement.setInt(1, x);
                    preparedStatement.setInt(2, y);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    // 기물 초기화용
    public static void insertPieces() {
        String sql = "INSERT INTO `piece` (type, country) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (PieceType pieceType : PieceType.values()) {
                for (CountryType countryType : CountryType.values()) {
                    preparedStatement.setString(1, pieceType.toString());
                    preparedStatement.setString(2, countryType.toString());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static int insertBoard() {
        String sql = "INSERT INTO board(turn, cho_score, han_score) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, CountryType.CHO.toString());
            preparedStatement.setDouble(2, 72d);
            preparedStatement.setDouble(3, 73.5d);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
//            // Query가 제대로 실행된 경우
//            if (result >= 1) {
//                System.out.println("보드 추가 완료");
//            }
//
//            // Query가 제대로 실행되지 않은 경우
//            else {
//                System.out.println("보드 추가 실패");
//            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
        return 0;
    }

    public static void updateBoard(CountryType countryType, int id) {
        String sql = "UPDATE board SET turn = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, countryType.toString());
            preparedStatement.setInt(2, id);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("보드 턴 업데이트 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("보드 턴 업데이트 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void readAllBoard() {
        String sql = "SELECT * FROM board";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            boolean isEmpty = true;

            while (resultSet.next()) {
                isEmpty = false;
                int id = resultSet.getInt("id");
                System.out.println("board id: " + id);
            }

            if (isEmpty) {
                throw new IllegalArgumentException("[ERROR] 저장된 보드가 없습니다.");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static CountryType readCountryTurn(int id) {
        String sql = "SELECT `turn` FROM board WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return CountryType.valueOf(resultSet.getString("turn"));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
        throw new IllegalArgumentException("[ERROR] 진영 턴을 불러오지 못했습니다.");
    }

    public static void readBoard(int id) {
        String sql = "SELECT * FROM board WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalArgumentException("해당 번호의 board가 없습니다.");
            }
            System.out.println(resultSet.getString("turn"));
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void deleteBoard(int id) {
        String sql = "DELETE FROM board WHERE `id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("board 삭제 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("board 삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void insertBoardState(Position position, PieceInfo pieceInfo, int boardId) {
        String sql = "INSERT INTO `board_state` (`position_x`, `position_y`, `piece_type`, `piece_country`, `board_id`) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setString(3, pieceInfo.pieceType().toString());
            preparedStatement.setString(4, pieceInfo.countryType().toString());
            preparedStatement.setInt(5, boardId);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("보드 상태 추가 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("보드 상태 추가 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void changeBoardStateToAndFrom(Position from, Position to, PieceInfos pieceInfos, int boardId) {
        if (isEmptyPosition(to, boardId)) {
            insertBoardState(to, pieceInfos.get(to), boardId);
            deleteBoardState(from, boardId);
            return;
        }
        updateBoardState(to, pieceInfos.get(to), boardId);
        deleteBoardState(from, boardId);
    }

    public static boolean isEmptyPosition(Position position, int boardId) {
        String sql = "SELECT * FROM `board_state` WHERE `position_x` = ? AND `position_y` = ? AND `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setInt(3, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("해당 좌표(" + position.x() + ", " + position.y() + ")는 비어있습니다.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
        return false;
    }

    public static void updateBoardState(Position position, PieceInfo pieceInfo, int boardId) {
        String sql = "UPDATE board_state SET `piece_type` = ?, `piece_country` = ? WHERE `position_x` = ? AND `position_y` = ? AND `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, pieceInfo.pieceType().toString());
            preparedStatement.setString(2, pieceInfo.countryType().toString());
            preparedStatement.setInt(3, position.x());
            preparedStatement.setInt(4, position.y());
            preparedStatement.setInt(5, boardId);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("to 좌표 상태 업데이트 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("to 좌표 상태 업데이트 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void deleteBoardState(Position position, int boardId) {
        String sql = "DELETE FROM board_state WHERE `position_x` = ? AND `position_y` = ? AND `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setInt(3, boardId);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("from 좌표 상태 삭제 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("from 좌표 상태 삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void deleteAllBoardStateInBoard(int boardId) {
        String sql = "DELETE FROM board_state WHERE `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, boardId);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("board state 삭제 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("board state 삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void insertBoardSnapshot(Position position, PieceInfo pieceInfo, int boardId) {
        String sql = "INSERT INTO board_snapshot (`position_x`, `position_y`, `piece_type`, `piece_country`, `board_id`) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setString(3, pieceInfo.pieceType().toString());
            preparedStatement.setString(4, pieceInfo.countryType().toString());
            preparedStatement.setInt(5, boardId);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("보드 스냅샷 추가 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("보드 스냅샷 추가 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static void deleteAllBoardSnapshotInBoard(int boardId) {
        String sql = "DELETE FROM board_snapshot WHERE `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, boardId);

            int result = preparedStatement.executeUpdate();

            // Query가 제대로 실행된 경우
            if (result >= 1) {
                System.out.println("board snapshot 삭제 완료");
            }

            // Query가 제대로 실행되지 않은 경우
            else {
                System.out.println("board snapshot 삭제 실패");
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
    }

    public static BoardStates loadBoardState(int boardId) {
        Map<Position, Piece> pieceInfos = new HashMap<>();
        String sql = "SELECT * FROM board_state WHERE board_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int x = resultSet.getInt("position_x");
                int y = resultSet.getInt("position_y");
                String pieceType = resultSet.getString("piece_type");
                String pieceCountry = resultSet.getString("piece_country");
                Piece piece = new Piece(new PieceInfo(PieceType.valueOf(pieceType), CountryType.valueOf(pieceCountry)));
                pieceInfos.put(new Position(x, y), piece);
            }
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        }
        return new BoardStates(pieceInfos);
    }
}
