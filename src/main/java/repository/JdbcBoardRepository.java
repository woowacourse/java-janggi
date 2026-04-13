package repository;

import domain.Position;
import domain.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.strategy.CannonStrategy;
import domain.strategy.ChariotStrategy;
import domain.strategy.ElephantStrategy;
import domain.strategy.HorseStrategy;
import domain.strategy.PalaceStrategy;
import domain.strategy.PawnStrategy;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class JdbcBoardRepository implements BoardRepository {

    private final DataSource dataSource;

    public JdbcBoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Map<Position, Piece> board) {
        deleteAll();

        String sql = "INSERT INTO board (row_index, col_index, team, piece_type) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Piece piece = entry.getValue();
                if (piece.getPieceType() == PieceType.BLANK) {
                    continue;
                }
                preparedStatement.setInt(1, entry.getKey().getRows());
                preparedStatement.setInt(2, entry.getKey().getColumns());
                preparedStatement.setString(3, piece.getTeam().name());
                preparedStatement.setString(4, piece.getPieceType().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("보드의 데이터를 저장하는 과정에서 문제가 발생하였습니다." + e);
        }
    }

    @Override
    public Map<Position, Piece> findAll() {
        String sql = "SELECT row_index, col_index, team, piece_type FROM board";
        Map<Position, Piece> board = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int row = resultSet.getInt("row_index");
                int column = resultSet.getInt("col_index");
                Position position = new Position(row, column);
                Team team = Team.valueOf(resultSet.getString("team"));
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));

                board.put(position, createPiece(team, pieceType));
            }
            return board;
        } catch (SQLException e) {
            throw new RuntimeException("보드의 데이터를 불러오는 과정에서 문제가 발생하였습니다." + e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM board";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("보드의 데이터를 초기화하는 과정에서 문제가 발생하였습니다.", e);
        }
    }

    private Piece createPiece(Team team, PieceType pieceType) {
        return switch (pieceType) {
            case KING -> new King(team, new PalaceStrategy());
            case PAWN -> new Pawn(team, new PawnStrategy());
            case CANNON -> new Cannon(team, new CannonStrategy());
            case CHARIOT -> new Chariot(team, new ChariotStrategy());
            case ELEPHANT -> new Elephant(team, new ElephantStrategy());
            case HORSE -> new Horse(team, new HorseStrategy());
            case GUARD -> new Guard(team, new PalaceStrategy());
            case BLANK -> throw new IllegalArgumentException("복구할 수 없는 기물입니다. " + pieceType);
        };
    }


}
