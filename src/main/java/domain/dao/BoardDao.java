package domain.dao;

import static util.DBConnectionUtil.getConnection;

import domain.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamType;
import domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class BoardDao {

    public void save(Board board) {
        Map<Position, Piece> alivePieces = board.getAlivePieces();
        try (Connection connection = getConnection()) {
            savePiecesInfo(alivePieces, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Board> findBoard() {
        Map<Position, Piece> alivePieces = new HashMap<>();
        String sql = "select * from board";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int rowIndex = resultSet.getInt("row_index");
                int colIndex = resultSet.getInt("column_index");
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                TeamType team = TeamType.valueOf(resultSet.getString("team"));
                alivePieces.put(Position.of(rowIndex, colIndex), PieceType.createPiece(pieceType, team));
            }
            if (alivePieces.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new Board(alivePieces));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoard(Position startPosition, Position endPosition) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                safeModeQuit(connection);
                deletePieceInfo(connection, endPosition);
                updatePieceInfo(connection, startPosition, endPosition);
                safeModeSet(connection);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw new RuntimeException(ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBoard() {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                safeModeQuit(connection);
                deleteAll(connection);
                safeModeSet(connection);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw new RuntimeException(ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePiecesInfo(Map<Position, Piece> alivePieces, Connection connection) {
        String sql = "insert into board(row_index, column_index, piece_type, team) values (?,?,?,?)";
        for (Entry<Position, Piece> positionPieceEntry : alivePieces.entrySet()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                Position position = positionPieceEntry.getKey();
                Piece piece = positionPieceEntry.getValue();
                preparedStatement.setInt(1, position.getRow());
                preparedStatement.setInt(2, position.getColumn());
                preparedStatement.setString(3, piece.getType().name());
                preparedStatement.setString(4, piece.getTeamType().name());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteAll(Connection connection) {
        String sql = "delete from board";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeQuit(Connection connection) {
        String sql = "SET SQL_SAFE_UPDATES = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePieceInfo(Connection connection, Position startPosition, Position endPosition) {
        String sql = "update board set row_index = ?, column_index = ? where row_index = ? and column_index = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, endPosition.getRow());
            preparedStatement.setInt(2, endPosition.getColumn());
            preparedStatement.setInt(3, startPosition.getRow());
            preparedStatement.setInt(4, startPosition.getColumn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeSet(Connection connection) {
        String sql = "SET SQL_SAFE_UPDATES = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePieceInfo(Connection connection, Position endPosition) {
        String sql = "delete from board where row_index = ? and column_index = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, endPosition.getRow());
            preparedStatement.setInt(2, endPosition.getColumn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
