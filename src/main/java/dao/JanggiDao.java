package dao;

import domain.JanggiPosition;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Empty;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Side;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class JanggiDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Map<JanggiPosition, Piece> loadBoard() {
        final Connection connection = getConnection();
        Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

        String sql = "SELECT rank_position, file_position, piece_type, side FROM janggi_board ORDER BY side";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String rankPosition = rs.getString("rank_position");
                String filePosition = rs.getString("file_position");
                String pieceType = rs.getString("piece_type");
                String side = rs.getString("side");

                JanggiPosition position = new JanggiPosition(Integer.parseInt(rankPosition),
                        Integer.parseInt(filePosition));
                Piece piece = createPieceFromType(pieceType, createSide(side));

                janggiBoard.put(position, piece);
            }
        } catch (SQLException e) {
            System.err.println("장기판을 로딩하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return janggiBoard;
    }

    private Side createSide(String side) {
        if (side.equals("Han")) {
            return Side.HAN;
        }
        if (side.equals("Cho")) {
            return Side.CHO;
        }
        return Side.EMPTY;
    }

    private Piece createPieceFromType(String pieceType, Side side) {
        switch (pieceType) {
            case "Cannon":
                return new Cannon(side);
            case "Chariot":
                return new Chariot(side);
            case "Elephant":
                return new Elephant(side);
            case "Guard":
                return new Guard(side);
            case "Horse":
                return new Horse(side);
            default:
                return new Empty();
        }
    }

    public void updateJanggiPosition(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        final Connection connection = getConnection();

//        String beforeSQL

    }
}
