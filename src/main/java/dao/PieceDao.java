package dao;

import game.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import location.Position;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.GreenSoldier;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.RedSoldier;

public class PieceDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 오류: " + e.getMessage(), e);
        }
    }

    public void resetPieces() {
        deleteAllPieces();
        addAllPieces();
    }

    public Pieces findByTeam(Team team) {
        List<Piece> pieces = new ArrayList<>();
        String query = "SELECT * FROM piece WHERE team_id = ?";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, team.getId());
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int pieceId = resultSet.getInt("piece_id");
                    PieceType pieceType = PieceType.findById(resultSet.getInt("piece_type_id"));
                    Position position = new Position(resultSet.getInt("x"), resultSet.getInt("y"));

                    Piece piece = createByPieceType(pieceId, team, pieceType, position);
                    pieces.add(piece);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생하였습니다.", e);
        }
        return new Pieces(pieces);
    }

    public Pieces findCatchAllBy(Team team) {
        List<Piece> pieces = new ArrayList<>();
        String query = "SELECT * FROM piece WHERE team_id = ? AND is_catch = true";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, team.getId());
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int pieceId = resultSet.getInt("piece_id");
                    PieceType pieceType = PieceType.findById(resultSet.getInt("piece_type_id"));
                    Position position = new Position(resultSet.getInt("x"), resultSet.getInt("y"));

                    Piece piece = createByPieceType(pieceId, team, pieceType, position);
                    pieces.add(piece);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생하였습니다.", e);
        }
        return new Pieces(pieces);
    }

    public Pieces findAll() {
        List<Piece> pieces = new ArrayList<>();
        String query = "SELECT * FROM piece";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int pieceId = resultSet.getInt("piece_id");
                    Team team = Team.findById(resultSet.getInt("team_id"));
                    PieceType pieceType = PieceType.findById(resultSet.getInt("piece_type_id"));
                    Position position = new Position(resultSet.getInt("x"), resultSet.getInt("y"));

                    Piece piece = createByPieceType(pieceId, team, pieceType, position);
                    pieces.add(piece);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생하였습니다.", e);
        }
        return new Pieces(pieces);
    }

    public void update(Piece piece, Position destination) {
        var query = "UPDATE piece SET x = ?, y = ? WHERE piece_id = ?";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, destination.x());
            preparedStatement.setInt(2, destination.y());
            preparedStatement.setInt(3, piece.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAllPieces() {
        var query = "TRUNCATE TABLE piece";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAllPieces() {
        // GREEN
        add(new Chariot(1, Team.GREEN, new Position(1, 10)));
        add(new Chariot(2, Team.GREEN, new Position(9, 10)));

        add(new Elephant(3, Team.GREEN, new Position(2, 10)));
        add(new Elephant(4, Team.GREEN, new Position(7, 10)));

        add(new Horse(5, Team.GREEN, new Position(3, 10)));
        add(new Horse(6, Team.GREEN, new Position(8, 10)));

        add(new Guard(7, Team.GREEN, new Position(4, 10)));
        add(new Guard(8, Team.GREEN, new Position(6, 10)));

        add(new General(9, Team.GREEN, new Position(5, 9)));

        add(new Cannon(10, Team.GREEN, new Position(2, 8)));
        add(new Cannon(11, Team.GREEN, new Position(8, 8)));

        add(new GreenSoldier(12, Team.GREEN, new Position(1, 7)));
        add(new GreenSoldier(13, Team.GREEN, new Position(3, 7)));
        add(new GreenSoldier(14, Team.GREEN, new Position(5, 7)));
        add(new GreenSoldier(15, Team.GREEN, new Position(7, 7)));
        add(new GreenSoldier(16, Team.GREEN, new Position(9, 7)));

        // red
        add(new Chariot(17, Team.RED, new Position(1, 1)));
        add(new Chariot(18, Team.RED, new Position(9, 1)));

        add(new Elephant(19, Team.RED, new Position(3, 1)));
        add(new Elephant(20, Team.RED, new Position(7, 1)));

        add(new Horse(21, Team.RED, new Position(2, 1)));
        add(new Horse(22, Team.RED, new Position(8, 1)));

        add(new Guard(23, Team.RED, new Position(4, 1)));
        add(new Guard(24, Team.RED, new Position(6, 1)));

        add(new General(25, Team.RED, new Position(5, 2)));

        add(new Cannon(26, Team.RED, new Position(2, 3)));
        add(new Cannon(27, Team.RED, new Position(8, 3)));

        add(new RedSoldier(28, Team.RED, new Position(1, 4)));
        add(new RedSoldier(29, Team.RED, new Position(3, 4)));
        add(new RedSoldier(30, Team.RED, new Position(5, 4)));
        add(new RedSoldier(31, Team.RED, new Position(7, 4)));
        add(new RedSoldier(32, Team.RED, new Position(9, 4)));
    }

    private void add(Piece piece) {
        var query = "INSERT INTO piece(piece_id, x, y, team_id, is_catch, piece_type_id) VALUES(?, ?, ?, ?, ?, ?)";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piece.getId());
            preparedStatement.setInt(2, piece.getCurrentPosition().x());
            preparedStatement.setInt(3, piece.getCurrentPosition().y());
            preparedStatement.setInt(4, piece.getTeam().getId());
            preparedStatement.setBoolean(5, piece.isCatch());
            preparedStatement.setInt(6, piece.getPieceType().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createByPieceType(int pieceId, Team team, PieceType pieceType, Position position) {
        if (pieceType == PieceType.CANNON) {
            return new Cannon(pieceId, team, position);
        }
        if (pieceType == PieceType.CHARIOT) {
            return new Chariot(pieceId, team, position);
        }
        if (pieceType == PieceType.ELEPHANT) {
            return new Elephant(pieceId, team, position);
        }
        if (pieceType == PieceType.GENERAL) {
            return new General(pieceId, team, position);
        }
        if (pieceType == PieceType.SOLDIER) {
            if (team == Team.GREEN) {
                return new GreenSoldier(pieceId, team, position);
            }
            if (team == Team.RED) {
                return new RedSoldier(pieceId, team, position);
            }
        }
        if (pieceType == PieceType.GUARD) {
            return new Guard(pieceId, team, position);
        }
        if (pieceType == PieceType.HORSE) {
            return new Horse(pieceId, team, position);
        }
        throw new IllegalArgumentException("[ERROR] 해당하는 기물 종류가 없습니다.");
    }
}
