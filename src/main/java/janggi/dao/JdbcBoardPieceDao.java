package janggi.dao;

import janggi.dto.BoardPiece;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcBoardPieceDao implements BoardPieceDao {
    private static final String INSERT_SQL = """
            INSERT INTO board_piece (game_room_id, row_pos, col_pos, piece_type, side)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String FIND_ALL_BY_GAME_ROOM_ID_SQL = """
            SELECT game_room_id, row_pos, col_pos, piece_type, side
            FROM board_piece
            WHERE game_room_id = ?
            ORDER BY row_pos, col_pos
            """;

    private static final String UPDATE_POSITION_SQL = """
            UPDATE board_piece
            SET row_pos = ?, col_pos = ?
            WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?
            """;

    private static final String DELETE_BY_POSITION_SQL = """
            DELETE FROM board_piece
            WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?
            """;

    @Override
    public void insertAll(List<BoardPiece> boardPieces, Connection connection) {
        if (boardPieces.isEmpty()) {
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            addInsertBatch(boardPieces, statement);
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("board_piece 전체 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<BoardPiece> findAllByGameRoomId(long gameRoomId, Connection connection) {
        try (PreparedStatement statement = prepareFindAllStatement(gameRoomId, connection);
             ResultSet resultSet = statement.executeQuery()) {
            return toBoardPieces(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("board_piece 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void updatePosition(long gameRoomId, int sourceRow, int sourceCol, int destinationRow, int destinationCol, Connection connection) {
        try (PreparedStatement statement = prepareUpdatePositionStatement(
                gameRoomId, sourceRow, sourceCol, destinationRow, destinationCol, connection
        )) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("board_piece 위치 수정 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void deleteByPosition(long gameRoomId, int rowPos, int colPos, Connection connection) {
        try (PreparedStatement statement = prepareDeleteByPositionStatement(gameRoomId, rowPos, colPos, connection)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("board_piece 삭제 중 오류가 발생했습니다.", e);
        }
    }

    private void addInsertBatch(List<BoardPiece> boardPieces, PreparedStatement statement) throws SQLException {
        for (BoardPiece boardPiece : boardPieces) {
            bindInsertParameters(statement, boardPiece);
            statement.addBatch();
        }
    }

    private void bindInsertParameters(PreparedStatement statement, BoardPiece boardPiece) throws SQLException {
        statement.setLong(1, boardPiece.gameRoomId());
        statement.setInt(2, boardPiece.rowPos());
        statement.setInt(3, boardPiece.colPos());
        statement.setString(4, boardPiece.pieceType());
        statement.setString(5, boardPiece.side());
    }

    private PreparedStatement prepareFindAllStatement(long gameRoomId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_GAME_ROOM_ID_SQL);
        statement.setLong(1, gameRoomId);
        return statement;
    }

    private List<BoardPiece> toBoardPieces(ResultSet resultSet) throws SQLException {
        List<BoardPiece> boardPieces = new ArrayList<>();
        while (resultSet.next()) {
            boardPieces.add(toBoardPiece(resultSet));
        }
        return boardPieces;
    }

    private BoardPiece toBoardPiece(ResultSet resultSet) throws SQLException {
        return new BoardPiece(
                resultSet.getLong("game_room_id"),
                resultSet.getInt("row_pos"),
                resultSet.getInt("col_pos"),
                resultSet.getString("piece_type"),
                resultSet.getString("side")
        );
    }

    private PreparedStatement prepareUpdatePositionStatement(
            long gameRoomId,
            int sourceRow,
            int sourceCol,
            int destinationRow,
            int destinationCol,
            Connection connection
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_POSITION_SQL);
        statement.setInt(1, destinationRow);
        statement.setInt(2, destinationCol);
        statement.setLong(3, gameRoomId);
        statement.setInt(4, sourceRow);
        statement.setInt(5, sourceCol);
        return statement;
    }

    private PreparedStatement prepareDeleteByPositionStatement(
            long gameRoomId,
            int rowPos,
            int colPos,
            Connection connection
    ) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_BY_POSITION_SQL);
        statement.setLong(1, gameRoomId);
        statement.setInt(2, rowPos);
        statement.setInt(3, colPos);
        return statement;
    }
}
