package dao;

import static model.janggiboard.JanggiBoard.HORIZONTAL_SIZE;
import static model.janggiboard.JanggiBoard.VERTICAL_SIZE;

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
import java.util.function.Function;
import model.Point;
import model.Team;
import model.janggiboard.Dot;
import model.piece.Byeong;
import model.piece.Cha;
import model.piece.Jang;
import model.piece.Ma;
import model.piece.Pho;
import model.piece.Piece;
import model.piece.Sa;
import model.piece.Sang;

public final class JanggiDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private static final Map<String, Function<Team, Piece>> PIECE_FACTORY = new HashMap<>() {{
        put("漢", Jang::new);
        put("士", Sa::new);
        put("象", Sang::new);
        put("馬", Ma::new);
        put("車", Cha::new);
        put("包", Pho::new);
        put("兵", Byeong::new);
    }};

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public int settingNewJanggiBoard(List<List<Dot>> janggiBoard) {
        final var insertGameStateQuery = "INSERT INTO game_state VALUES(null,0)";
        final var insertPieceQuery = "INSERT INTO pieces VALUES(null, ?, ?, ?, ?, ?)";

        try (final var connection = getConnection();
             final var preparedStatementGameState = connection.prepareStatement(insertGameStateQuery,
                     Statement.RETURN_GENERATED_KEYS);
             final var preparedStatementPiece = connection.prepareStatement(insertPieceQuery)) {

            preparedStatementGameState.executeUpdate();
            int gameStateId;
            try (final var generatedKeys = preparedStatementGameState.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gameStateId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("game_state의 자동 생성된 키를 가져오지 못했습니다.");
                }
                for (int i = 0; i < janggiBoard.size(); i++) {
                    for (int j = 0; j < 9; j++) {
                        if (janggiBoard.get(i).get(j).isPlaced()) {
                                Piece piece = janggiBoard.get(i).get(j).getPiece();
                                String pieceName = piece.getPieceName().getName();
                                String pieceTeam = piece.getTeam().getTeam();

                                preparedStatementPiece.setInt(1, gameStateId);
                                preparedStatementPiece.setString(2, pieceName);
                                preparedStatementPiece.setString(3, pieceTeam);
                                preparedStatementPiece.setInt(4, j);
                                preparedStatementPiece.setInt(5, i);
                                preparedStatementPiece.addBatch();
                            }
                        }
                    }
                    preparedStatementPiece.executeBatch();
                    return gameStateId;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기 보드 설정 중 오류 발생", e);
        }
    }

    public List<List<Dot>> settingBeforeJanggiBoard() {
        final String getGameStateQuery = "SELECT * FROM game_state ORDER BY game_id DESC LIMIT 1";
        final String getPiecesQuery = "SELECT * FROM pieces WHERE game_id = ?";

        List<List<Dot>> board = initializeJanggiBoard();

        try (Connection connection = getConnection();
             Statement stmtGameState = connection.createStatement();
             ResultSet gameStateResultSet = stmtGameState.executeQuery(getGameStateQuery)) {

            if (gameStateResultSet.next()) {
                int gameStateId = gameStateResultSet.getInt("game_id");

                try (PreparedStatement stmtPieces = connection.prepareStatement(getPiecesQuery)) {
                    stmtPieces.setInt(1, gameStateId);
                    try (ResultSet piecesResultSet = stmtPieces.executeQuery()) {
                        while (piecesResultSet.next()) {
                            int x = piecesResultSet.getInt("x_position");
                            int y = piecesResultSet.getInt("y_position");
                            String pieceName = piecesResultSet.getString("piece_name");
                            String teamName = piecesResultSet.getString("team");

                            Team team = Team.findTeamByName(teamName);
                            Piece piece = PIECE_FACTORY.getOrDefault(pieceName, t -> null).apply(team);
                            Dot dot = new Dot(piece);
                            board.get(y).set(x, dot);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("기존 게임을 불러오는 중 오류 발생", e);
        }
        return board;
    }

    private List<List<Dot>> initializeJanggiBoard() {
        List<List<Dot>> dots = new ArrayList<>();
        for (int i = 0; i < VERTICAL_SIZE; i++) {
            List<Dot> dotLine = getHorizontalDotsLine();
            dots.add(dotLine);
        }
        return dots;
    }

    private static List<Dot> getHorizontalDotsLine() {
        List<Dot> dotLine = new ArrayList<>();
        for (int i = 0; i < HORIZONTAL_SIZE; i++) {
            dotLine.add(new Dot());
        }
        return dotLine;
    }

    private int getLatestGameId(Connection connection) throws SQLException {
        final String query = "SELECT game_id FROM game_state ORDER BY game_id DESC LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new IllegalArgumentException("장기판 정보가 존재하지 않습니다.");
        }
    }

    public void deletePiece(Point targetPoint) {
        final String deletePieceQuery = "DELETE FROM pieces WHERE x_position = ? AND y_position = ? AND game_id=?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(deletePieceQuery)) {
            int gameId = getLatestGameId(connection);

            preparedStatement.setInt(1, targetPoint.x());
            preparedStatement.setInt(2, targetPoint.y());
            preparedStatement.setInt(3, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("기물 삭제 중 오류 발생", e);
        }
    }

    public void changePieceLocation(Piece beforePiece, Point targetPoint) {
        final String updatePieceLocationQuery = "UPDATE pieces SET x_position = ?, y_position = ? WHERE piece_name = ? AND team = ? AND game_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updatePieceLocationQuery)) {
            int gameId = getLatestGameId(connection);

            preparedStatement.setInt(1, targetPoint.x());
            preparedStatement.setInt(2, targetPoint.y());
            preparedStatement.setString(3, beforePiece.getPieceName().getName());
            preparedStatement.setString(4, beforePiece.getTeam().getTeam());
            preparedStatement.setInt(5, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("기물 위치 업데이트 중 오류 발생", e);
        }
    }

    public void updateTurn() {
        final String updateTurnQuery = "UPDATE game_state SET turn= turn+1 WHERE game_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateTurnQuery)) {
            int gameId = getLatestGameId(connection);

            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기 턴 업데이트 중 오류 발생", e);
        }
    }

    public int getGameTurn() {
        final String query = "SELECT turn FROM game_state ORDER BY game_id DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new IllegalArgumentException("game_state 테이블에 데이터가 없습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 차례를 가져오는 중 오류가 발생", e);
        }
    }
}
