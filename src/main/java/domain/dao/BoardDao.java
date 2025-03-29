package domain.dao;

import domain.Board;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
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
import util.DBConnectionUtil;

public class BoardDao {

    public void save(Board board) {
        Map<Position, Piece> alivePieces = board.getAlivePieces();
        String sql = "insert into board(row_index, column_index, piece_type, team) values (?,?,?,?)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        for (Entry<Position, Piece> positionPieceEntry : alivePieces.entrySet()) {
            try {
                Position position = positionPieceEntry.getKey();
                Piece piece = positionPieceEntry.getValue();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, position.getRow());
                preparedStatement.setInt(2, position.getColumn());
                preparedStatement.setString(3, piece.getType().name());
                preparedStatement.setString(4, piece.getTeamType().name());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        close(connection, preparedStatement, null);
    }

    public Optional<Board> findBoard() {
        Map<Position, Piece> alivePieces = new HashMap<>();
        String sql = "select * from board";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int rowIndex = resultSet.getInt("row_index");
                int colIndex = resultSet.getInt("column_index");
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                TeamType team = TeamType.valueOf(resultSet.getString("team"));
                alivePieces.put(Position.of(rowIndex, colIndex), makePiece(pieceType, team));
            }
            if (alivePieces.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new Board(alivePieces));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public void updateBoard(Position startPosition, Position endPosition) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            safeModeQuit(connection);
            deletePieceInfo(connection, endPosition);
            updatePieceInfo(connection, startPosition, endPosition);
            safeModeSet(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        close(connection, null, null);
    }

    public void deleteBoard() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            safeModeQuit(connection);
            deleteAll(connection);
            safeModeSet(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        close(connection, null, null);
    }

    private void deleteAll(Connection connection) {
        String sql = "delete from board";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void safeModeQuit(Connection connection) {
        String safeModeQuit = "SET SQL_SAFE_UPDATES = 0";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(safeModeQuit);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void updatePieceInfo(Connection connection, Position startPosition, Position endPosition) {
        String updateSql = "update board set row_index = ?, column_index = ? where row_index = ? and column_index = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setInt(1, endPosition.getRow());
            preparedStatement.setInt(2, endPosition.getColumn());
            preparedStatement.setInt(3, startPosition.getRow());
            preparedStatement.setInt(4, startPosition.getColumn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void safeModeSet(Connection connection) {
        String safeModeSet = "SET SQL_SAFE_UPDATES = 1";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(safeModeSet);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void deletePieceInfo(Connection connection, Position endPosition) {
        String deleteSql = "delete from board where row_index = ? and column_index = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, endPosition.getRow());
            preparedStatement.setInt(2, endPosition.getColumn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece makePiece(PieceType pieceType, TeamType teamType) {
        if (pieceType == PieceType.CANNON) {
            return new Cannon(teamType);
        }
        if (pieceType == PieceType.SOLDIER) {
            return new Soldier(teamType);
        }
        if (pieceType == PieceType.ELEPHANT) {
            return new Elephant(teamType);
        }
        if (pieceType == PieceType.GUARD) {
            return new Guard(teamType);
        }
        if (pieceType == PieceType.CHARIOT) {
            return new Chariot(teamType);
        }
        if (pieceType == PieceType.KING) {
            return new King(teamType);
        }
        if (pieceType == PieceType.HORSE) {
            return new Horse(teamType);
        }
        throw new IllegalArgumentException("존재 하지 않은 말입니다.");
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
