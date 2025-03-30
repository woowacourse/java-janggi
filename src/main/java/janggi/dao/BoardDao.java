package janggi.dao;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root";

    private static final Map<PieceType, String> PIECE_TYPES = Map.of(
            PieceType.KING, "왕",
            PieceType.GUARD, "사",
            PieceType.HORSE, "마",
            PieceType.ELEPHANT, "상",
            PieceType.CANNON, "포",
            PieceType.CHARIOT, "차",
            PieceType.SOLDIER, "병"
    );

    private static final Map<Team, String> TEAMS = Map.of(
            Team.CHO, "초",
            Team.HAN, "한"
    );

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void addBoardPiece(Piece piece) {
        final String query = "INSERT INTO board_piece (`piece_type`, `live_status`, `team`, `column_position`, `row_position`) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, PIECE_TYPES.get(piece.getPieceType()));
            preparedStatement.setBoolean(2, piece.isLive());
            preparedStatement.setString(3, TEAMS.get(piece.getTeam()));
            preparedStatement.setInt(4, piece.getPosition().column());
            preparedStatement.setInt(5, piece.getPosition().row());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAllBoardPiece(List<Piece> pieces) {
        final String query = "INSERT INTO board_piece (`piece_type`, `live_status`, `team`, `column_position`, `row_position`) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            for (Piece piece : pieces) {
                preparedStatement.setString(1, PIECE_TYPES.get(piece.getPieceType()));
                preparedStatement.setBoolean(2, piece.isLive());
                preparedStatement.setString(3, TEAMS.get(piece.getTeam()));
                preparedStatement.setInt(4, piece.getPosition().column());
                preparedStatement.setInt(5, piece.getPosition().row());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsBoardPiece() {
        final String query = "SELECT COUNT(*) FROM board_piece";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet result = preparedStatement.executeQuery();
            int count = 0;
            if(result.next()) {
                count = result.getInt(0);
            }
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
