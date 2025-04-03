package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Point;
import model.piece.Piece;
import vo.PieceVo;

public final class JanggiDao implements JanggiRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int settingNewJanggiBoard(List<PieceVo> janggiBoard) {
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
                for (PieceVo pieceVo : janggiBoard) {

                    String pieceName = pieceVo.getPieceName();
                    String pieceTeam = pieceVo.getTeam();

                    preparedStatementPiece.setInt(1, gameStateId);
                    preparedStatementPiece.setString(2, pieceName);
                    preparedStatementPiece.setString(3, pieceTeam);
                    preparedStatementPiece.setInt(4, pieceVo.getPointX());
                    preparedStatementPiece.setInt(5, pieceVo.getPointY());
                    preparedStatementPiece.addBatch();
                }
                    preparedStatementPiece.executeBatch();
                    return gameStateId;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기 보드 설정 중 오류 발생", e);
        }
    }

    @Override
    public List<PieceVo> settingBeforeJanggiBoard() {
        final String getPiecesQuery = "SELECT * FROM pieces WHERE game_id = ?";
        final int beforeGameId = getLatestGameId();
        List<PieceVo> pieceVos = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(getPiecesQuery)) {

            stmt.setInt(1, beforeGameId);

            try (ResultSet rs = stmt.executeQuery()) {
                placePiecesOnBoard(pieceVos, rs);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("기존 게임을 불러오는 중 오류 발생", e);
        }
        return pieceVos;
    }

    private void placePiecesOnBoard(List<PieceVo> pieceVos, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int pointX = rs.getInt("x_position");
            int pointY = rs.getInt("y_position");
            String pieceName = rs.getString("piece_name");
            String pieceTeam = rs.getString("team");

            pieceVos.add(new PieceVo(pieceName, pointX, pointY, pieceTeam));
        }
    }

    private int getLatestGameId() {
        final String query = "SELECT game_id FROM game_state ORDER BY game_id DESC LIMIT 1";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("game_id");
            } else {
                throw new IllegalArgumentException("이전 게임 아이디가 없습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 상태를 불러오는 중 오류 발생", e);
        }
    }


    @Override
    public void deletePiece(Point targetPoint) {
        final String deletePieceQuery = "DELETE FROM pieces WHERE x_position = ? AND y_position = ? AND game_id=?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(deletePieceQuery)) {
            int gameId = getLatestGameId();
            preparedStatement.setInt(1, targetPoint.x());
            preparedStatement.setInt(2, targetPoint.y());
            preparedStatement.setInt(3, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("기물 삭제 중 오류 발생", e);
        }
    }

    @Override
    public void changePieceLocation(Piece beforePiece, Point targetPoint) {
        final String updatePieceLocationQuery = "UPDATE pieces SET x_position = ?, y_position = ? WHERE piece_name = ? AND team = ? AND game_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updatePieceLocationQuery)) {
            int gameId = getLatestGameId();

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

    @Override
    public void updateTurn() {
        final String updateTurnQuery = "UPDATE game_state SET turn= turn+1 WHERE game_id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateTurnQuery)) {
            int gameId = getLatestGameId();

            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("장기 턴 업데이트 중 오류 발생", e);
        }
    }

    @Override
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
