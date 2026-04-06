package service;

import static domain.Position.INITIAL_POSITION;
import static domain.Position.X_MAXIMUM_POSITION;
import static domain.Position.Y_MAXIMUM_POSITION;

import domain.Position;
import domain.board.Board;
import domain.board.BoardSnapshot;
import domain.board.BoardSnapshots;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiService {
    private static final String URL = "jdbc:mysql://localhost:3306/janggi";
    private static final String USER = "root";
    private static final String PASSWORD = "0502";

    public int insertBoard() {
        String sql = "INSERT INTO board(`turn`, `cho_score`, `han_score`) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, CountryType.CHO.toString());
            preparedStatement.setDouble(2, CountryType.CHO.getInitScore());
            preparedStatement.setDouble(3, CountryType.HAN.getInitScore());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 생성된 보드 ID를 가져오는 데 실패했습니다.");
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 생성에 실패했습니다.", e);
        }
    }

    public void updateBoard(CountryType countryType, Map<CountryType, Double> scores, int id) {
        String sql = "UPDATE board SET `turn` = ?, `cho_score` = ?, `han_score` = ? WHERE `id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, countryType.toString());
            preparedStatement.setDouble(2, scores.get(CountryType.CHO));
            preparedStatement.setDouble(3, scores.get(CountryType.HAN));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 업데이트에 실패했습니다.", e);
        }
    }

    public List<Integer> readAllBoardId() {
        String sql = "SELECT `id` FROM board";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Integer> boardIds = new ArrayList<>();

            boolean isEmpty = true;

            while (resultSet.next()) {
                isEmpty = false;
                boardIds.add(resultSet.getInt("id"));
            }

            if (isEmpty) {
                throw new IllegalArgumentException("[ERROR] 저장된 보드가 없습니다.");
            }
            return boardIds;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 모든 보드를 불러오는 데 실패했습니다.");
        }
    }

    public CountryType readCountryTurn(int id) {
        String sql = "SELECT `turn` FROM board WHERE `id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalArgumentException("[ERROR] 해당 번호의 board가 없습니다.");
            }
            return CountryType.valueOf(resultSet.getString("turn"));
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 진영 턴을 불러오는 데 실패했습니다.", e);
        }
    }

    public Board readBoard(int id) {
        String sql = "SELECT * FROM board WHERE `id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalArgumentException("[ERROR] 해당 번호의 board가 없습니다.");
            }
            System.out.println(resultSet.getString("turn"));
            return new Board(loadBoardState(id), resultSet.getDouble("cho_score"),
                    resultSet.getDouble("han_score"));
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드를 불러오는 데 실패했습니다.", e);
        }
    }

    public void deleteBoard(int id) {
        String sql = "DELETE FROM board WHERE `id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 삭제에 실패했습니다.", e);
        }
    }

    public void insertBoardState(Position position, PieceInfo pieceInfo, int boardId) {
        String sql = "INSERT INTO `board_state` (`position_x`, `position_y`, `piece_type`, `piece_country`, `board_id`) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setString(3, pieceInfo.pieceType().toString());
            preparedStatement.setString(4, pieceInfo.countryType().toString());
            preparedStatement.setInt(5, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 생성에 실패했습니다.", e);
        }
    }

    public void changeBoardStateToAndFrom(Position from, Position to, PieceInfos pieceInfos, int boardId) {
        if (isEmptyPosition(to, boardId)) {
            insertBoardState(to, pieceInfos.get(to), boardId);
            deleteBoardState(from, boardId);
            return;
        }
        updateBoardState(to, pieceInfos.get(to), boardId);
        deleteBoardState(from, boardId);
    }

    public boolean isEmptyPosition(Position position, int boardId) {
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
            throw new IllegalStateException("[ERROR] 포지션을 확인하는 데 실패했습니다.", e);
        }
        return false;
    }

    private BoardStates loadBoardState(int boardId) {
        Map<Position, Piece> pieceInfos = new HashMap<>();
        String sql = "SELECT * FROM board_state WHERE `board_id` = ?";
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
            return new BoardStates(pieceInfos);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태를 불러오는 데 실패했습니다.");
        }
    }

    public void updateBoardState(Position position, PieceInfo pieceInfo, int boardId) {
        String sql = "UPDATE board_state SET `piece_type` = ?, `piece_country` = ? WHERE `position_x` = ? AND `position_y` = ? AND `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, pieceInfo.pieceType().toString());
            preparedStatement.setString(2, pieceInfo.countryType().toString());
            preparedStatement.setInt(3, position.x());
            preparedStatement.setInt(4, position.y());
            preparedStatement.setInt(5, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 업데이트에 실패했습니다.", e);
        }
    }

    public void deleteBoardState(Position position, int boardId) {
        String sql = "DELETE FROM board_state WHERE `position_x` = ? AND `position_y` = ? AND `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.setInt(3, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 상태 삭제에 실패했습니다.", e);
        }
    }

    public void deleteAllBoardStateInBoard(int boardId) {
        String sql = "DELETE FROM board_state WHERE `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 특정 보드의 모든 보드 상태 삭제에 실패했습니다.", e);
        }
    }

    public void insertBoardSnapshot(PieceInfos pieceInfos, int boardId, CountryType turn) {
        String sql = "INSERT INTO board_snapshot (`id`, `position_x`, `position_y`, `piece_type`, `piece_country`, `board_id`, `turn`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int snapshotId = getNextSnapshotId();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Position position : pieceInfos.getKeys()) {
                PieceInfo pieceInfo = pieceInfos.get(position);
                preparedStatement.setInt(1, snapshotId);
                preparedStatement.setInt(2, position.x());
                preparedStatement.setInt(3, position.y());
                preparedStatement.setString(4, pieceInfo.pieceType().toString());
                preparedStatement.setString(5, pieceInfo.countryType().toString());
                preparedStatement.setInt(6, boardId);
                preparedStatement.setString(7, turn.toString());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷 생성에 실패했습니다.", e);
        }
    }

    private int getNextSnapshotId() {
        String sql = "SELECT MAX(id) FROM board_snapshot";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int maxId = resultSet.getInt(1);
                return maxId + 1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷의 모든 ID를 불러오는 데 실패했습니다.", e);
        }
        return 1;
    }

    public BoardSnapshots loadBoardSnapshot(int boardId) {
        BoardSnapshots boardSnapshots = new BoardSnapshots();
        List<BoardSnapshot> snapshots = new ArrayList<>();
        String sql = "SELECT * FROM board_snapshot WHERE `id` = ? AND `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            addSnapshots(boardId, getBoardSnapshotIds(), preparedStatement, snapshots);
            for (BoardSnapshot boardSnapshot : snapshots) {
                boardSnapshots.addBoardSnapshot(boardSnapshot);
            }

//            System.out.println("스냅샷 로드 완료");
            return boardSnapshots;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷을 불러오는 데 실패했습니다.", e);
        }
    }

    private static void addSnapshots(int boardId, List<Integer> ids, PreparedStatement preparedStatement,
                                     List<BoardSnapshot> snapshots) throws SQLException {
        for (int id : ids) {
            Map<Position, PieceInfo> pieceInfos = new HashMap<>();
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String turn = addPieceInfos(resultSet, pieceInfos);
            if (turn.isEmpty()) {
                continue;
            }
            snapshots.add(new BoardSnapshot(new PieceInfos(pieceInfos), CountryType.valueOf(turn)));
        }
    }

    private static String addPieceInfos(ResultSet resultSet, Map<Position, PieceInfo> pieceInfos)
            throws SQLException {
        String turn = "";
        while (resultSet.next()) {
            int x = resultSet.getInt("position_x");
            int y = resultSet.getInt("position_y");
            String pieceType = resultSet.getString("piece_type");
            String pieceCountry = resultSet.getString("piece_country");
            PieceInfo pieceInfo = new PieceInfo(PieceType.valueOf(pieceType),
                    CountryType.valueOf(pieceCountry));
            pieceInfos.put(new Position(x, y), pieceInfo);
            turn = resultSet.getString("turn");
        }
        return turn;
    }

    private List<Integer> getBoardSnapshotIds() {
        String sql = "SELECT DISTINCT `id` FROM board_snapshot";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Integer> ids = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
            return ids;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 스냅샷 ID를 불러오는 데 실패했습니다.");
        }
    }

    public void deleteAllBoardSnapshotInBoard(int boardId) {
        String sql = "DELETE FROM board_snapshot WHERE `board_id` = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, boardId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 특정 보드의 모든 보드 스냅샷 삭제에 실패했습니다.", e);
        }
    }

    public void deleteAll() {
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
            throw new IllegalStateException("[ERROR] 모든 테이블 삭제에 실패했습니다.", e);
        }
    }

    // 포지션 초기화용
    public void insertPositions() {
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
            throw new IllegalStateException("[ERROR] 포지션 생성에 실패했습니다.", e);
        }
    }

    // 기물 초기화용
    public void insertPieces() {
        String sql = "INSERT INTO `piece` (`type`, `country`) VALUES (?, ?)";
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
            throw new IllegalStateException("[ERROR] 기물 생성에 실패했습니다.", e);
        }
    }
}
