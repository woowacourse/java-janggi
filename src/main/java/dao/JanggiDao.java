package dao;

import domain.JanggiPosition;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Empty;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.Soldier;
import domain.piece.state.EmptyState;
import domain.piece.state.MovedCannon;
import domain.piece.state.MovedChariot;
import domain.piece.state.MovedElephant;
import domain.piece.state.MovedGeneral;
import domain.piece.state.MovedGuard;
import domain.piece.state.MovedHorse;
import domain.piece.state.MovedSoldierByeong;
import domain.piece.state.MovedSoldierJol;
import domain.piece.state.PieceState;
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

    public void initializeJanggiBoard() {
        if (!isDatabaseEmpty()) {
            return;
        }
        Connection connection = getConnection();
        String sql = "INSERT INTO janggi (rank_position, file_position, piece_type, side) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            // 초(Cho) 진영 초기 기물 배치
            addInitialPiece(ps, 5, 9, "General", "Cho");
            addInitialPiece(ps, 1, 0, "Chariot", "Cho");
            addInitialPiece(ps, 9, 0, "Chariot", "Cho");
            addInitialPiece(ps, 2, 8, "Cannon", "Cho");
            addInitialPiece(ps, 8, 8, "Cannon", "Cho");
            addInitialPiece(ps, 1, 7, "Jol", "Cho");
            addInitialPiece(ps, 3, 7, "Jol", "Cho");
            addInitialPiece(ps, 5, 7, "Jol", "Cho");
            addInitialPiece(ps, 7, 7, "Jol", "Cho");
            addInitialPiece(ps, 9, 7, "Jol", "Cho");
            addInitialPiece(ps, 4, 0, "Guard", "Cho");
            addInitialPiece(ps, 6, 0, "Guard", "Cho");
            addInitialPiece(ps, 2, 0, "Horse", "Cho");
            addInitialPiece(ps, 8, 0, "Horse", "Cho");
            addInitialPiece(ps, 3, 0, "Elephant", "Cho");
            addInitialPiece(ps, 7, 0, "Elephant", "Cho");
            // 한(Han) 진영 초기 기물 배치
            addInitialPiece(ps, 5, 2, "General", "Han");
            addInitialPiece(ps, 1, 1, "Chariot", "Han");
            addInitialPiece(ps, 9, 1, "Chariot", "Han");
            addInitialPiece(ps, 2, 3, "Cannon", "Han");
            addInitialPiece(ps, 8, 3, "Cannon", "Han");
            addInitialPiece(ps, 1, 4, "Byeong", "Han");
            addInitialPiece(ps, 3, 4, "Byeong", "Han");
            addInitialPiece(ps, 5, 4, "Byeong", "Han");
            addInitialPiece(ps, 7, 4, "Byeong", "Han");
            addInitialPiece(ps, 9, 4, "Byeong", "Han");
            addInitialPiece(ps, 4, 1, "Guard", "Han");
            addInitialPiece(ps, 6, 1, "Guard", "Han");
            addInitialPiece(ps, 2, 1, "Horse", "Han");
            addInitialPiece(ps, 8, 1, "Horse", "Han");
            addInitialPiece(ps, 3, 1, "Elephant", "Han");
            addInitialPiece(ps, 7, 1, "Elephant", "Han");
            ps.executeBatch();
            System.out.println("초기 장기판 데이터가 저장되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addInitialPiece(PreparedStatement ps, int rank, int file, String pieceType, String side) throws SQLException {
        ps.setInt(1, rank);
        ps.setInt(2, file);
        ps.setString(3, pieceType);
        ps.setString(4, side);
        ps.addBatch();
    }

    private boolean isDatabaseEmpty() {
        Connection connection = getConnection();
        try {
            String sql = "SELECT COUNT(*) FROM janggi";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.err.println("테이블 행 조회 오류. 비어있습니다.");
            e.printStackTrace();
        }
        return false;
    }

    public Map<JanggiPosition, Piece> loadJanggiBoard() {
        final Connection connection = getConnection();
        Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

        String sql = "SELECT rank_position, file_position, piece_type, side FROM janggi ORDER BY side";
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

        for (int rank = 1; rank < 10; rank++) {
            for (int file = 0; file < 10; file++) {
                JanggiPosition position = new JanggiPosition(rank, file);
                if (!janggiBoard.containsKey(position)) {
                    janggiBoard.put(position, new Empty());
                }
            }
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
        return switch (pieceType) {
            case "Cannon" -> new Cannon(side);
            case "Chariot" -> new Chariot(side);
            case "Elephant" -> new Elephant(side);
            case "Guard" -> new Guard(side);
            case "Horse" -> new Horse(side);
            case "General" -> new General(side);
            case "Byeong" -> new Soldier(side);
            case "Jol" -> new Soldier(side);
            default -> new Empty();
        };
    }

    public void updateJanggiBoard(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        final Connection connection = getConnection();

        String beforeSQL = "UPDATE janggi SET piece_type = 'Empty', side = '' WHERE rank_position = ? AND file_position = ?";
//        String afterSQL = "UPDATE janggi SET piece_type = ?, side = ? WHERE rank_position = ? AND file_position = ?";
        String afterSQL = "INSERT INTO janggi (rank_position, file_position, piece_type, side) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE piece_type = VALUES(piece_type), side = VALUES(side)";
        try {
            PreparedStatement beforePs = connection.prepareStatement(beforeSQL);
            beforePs.setInt(1, beforePosition.rank());
            beforePs.setInt(2, beforePosition.file());
            beforePs.executeUpdate();
            beforePs.close();

            PreparedStatement afterPs = connection.prepareStatement(afterSQL);
            afterPs.setInt(1, afterPosition.rank());
            afterPs.setInt(2, afterPosition.file());
            afterPs.setString(3, createTypeFromPiece(piece));
            afterPs.setString(4, createStringSide(piece.getSide()));
            afterPs.executeUpdate();
            afterPs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String createTypeFromPiece(Piece piece) {
        PieceState state = piece.getState();
        if (state instanceof EmptyState) {
            return "Empty";
        }
        if (state instanceof MovedCannon) {
            return "Cannon";
        } else if (state instanceof MovedChariot) {
            return "Chariot";
        } else if (state instanceof MovedElephant) {
            return "Elephant";
        } else if (state instanceof MovedGuard) {
            return "Guard";
        } else if (state instanceof MovedHorse) {
            return "Horse";
        } else if (state instanceof MovedGeneral) {
            return "General";
        } else if (state instanceof MovedSoldierByeong) {
            return "Byeong";
        } else if (state instanceof MovedSoldierJol) {
            return "Jol";
        }
        return "";
    }

    private String createStringSide(Side side) {
        if (side.equals(Side.HAN)) {
            return "Han";
        }
        if (side.equals(Side.CHO)) {
            return "Cho";
        }
        return "";
    }
}
