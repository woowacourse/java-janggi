package team.janggi.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import team.janggi.application.JdbcExecutor;
import team.janggi.domain.BoardSize;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.BoardPieces;
import team.janggi.domain.board.BoardStateReader;
import team.janggi.domain.board.EmptyBoardPiecesInitializer;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class BoardRepository {
    private final JdbcExecutor jdbcExecutor;

    public BoardRepository(JdbcExecutor jdbcExecutor) {
        this.jdbcExecutor = jdbcExecutor;
    }

    public Board findById(long gameRoomId) {
        final String sql = """
                              SELECT 
                                   game_room_id,
                                   position_x,
                                   position_y,
                                   piece_type,
                                   team,
                                   created_dt,
                                   updated_dt  
                              FROM BOARD
                              WHERE game_room_id = ?
                           """;
        return jdbcExecutor.execute(sql, statement -> {
            try {
                statement.setLong(1, gameRoomId);

                final BoardPieces boardPieces = new BoardPieces();
                final Board board = new Board(boardPieces, EmptyBoardPiecesInitializer.instance);
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final int positionX = resultSet.getInt("position_x");
                    final int positionY = resultSet.getInt("position_y");
                    final PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    final Team team = Team.valueOf(resultSet.getString("team"));

                    final Position position = new Position(positionX, positionY);
                    final Piece piece = Piece.of(pieceType, team);
                    boardPieces.setPiece(position, piece);
                }

                return board;
            } catch (Exception e) {
                throw new RuntimeException("보드 조회에 실패하였습니다.", e);
            }
        });
    }

    public void save(long gameRoomId, Board board) {
        final String sql = """
                              MERGE INTO BOARD (
                                   game_room_id,
                                   position_x,
                                   position_y,
                                   piece_type,
                                   team,
                                   created_dt,
                                   updated_dt  
                              )
                              KEY (game_room_id, position_x, position_y)
                              VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                           """;
        jdbcExecutor.execute(sql, statement -> {
            try {
                // 기물 정보 저장
                for (int y = 0; y < BoardSize.Y; y++) {
                    setBoardParameter(statement, y, gameRoomId, board.getStateReader());
                }
                statement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException("보드 저장에 실패하였습니다.", e);
            }

            return null;
        });
    }

    // statement에 기물 정보를 세팅하는 메서드
    private void setBoardParameter(PreparedStatement statement,
                                   int y,
                                   long gameRoomId,
                                   BoardStateReader reader) throws SQLException {
        for (int x = 0; x < BoardSize.X; x++) {
            final Position position = new Position(x, y);
            final Piece piece = reader.getPiece(position);

            statement.setLong(1, gameRoomId);
            statement.setInt(2, x);
            statement.setInt(3, y);
            statement.setString(4, piece.getPieceType().name());
            statement.setString(5, piece.getTeam().name());
            statement.addBatch();
        }
    }

}
