package janggi.dao;

import janggi.board.Board;
import janggi.dto.BoardPieceDto;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
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

    public void updateBoardPiece(Piece previousPiece, Piece updatePiece) {
        final var query = "UPDATE board_piece SET column_position = ?, row_position = ?, live_status = ? WHERE column_position = ? AND row_position = ? AND team = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, updatePiece.getPosition().column());
            preparedStatement.setInt(2, updatePiece.getPosition().row());
            preparedStatement.setBoolean(3, updatePiece.isLive());
            preparedStatement.setInt(4, previousPiece.getPosition().column());
            preparedStatement.setInt(5, previousPiece.getPosition().row());
            preparedStatement.setString(6, TEAMS.get(previousPiece.getTeam()));
            preparedStatement.executeUpdate();
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
            if (result.next()) {
                count = result.getInt(1);
            }
            return count != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> findAllBoardPiece() {
        final String query = "SELECT * FROM board_piece";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet result = preparedStatement.executeQuery();
            List<Piece> pieces = new ArrayList<>();
            while (result.next()) {
                String pieceTypeData = result.getString("piece_type");
                PieceType pieceType = PIECE_TYPES.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(pieceTypeData))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다"))
                        .getKey();
                String teamData = result.getString("team");
                Team team = TEAMS.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(teamData))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물입니다"))
                        .getKey();
                int columnPosition = result.getInt("column_position");
                int rowPosition = result.getInt("row_position");
                boolean liveStatus = result.getBoolean("live_status");
                Piece piece = pieceType.createInstance(new BoardPieceDto(team, new Position(rowPosition, columnPosition), liveStatus));
                pieces.add(piece);
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
