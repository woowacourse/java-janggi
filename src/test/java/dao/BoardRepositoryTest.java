package dao;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;
import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.BasicPiece;
import domain.piece.None;
import domain.position.Position;
import db.DbBootstrap;
import db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardRepositoryTest {
    private final GameRoom gameRoom = new GameRoom();
    private final BoardRepository boardRepository = new BoardRepository();

    @BeforeEach
    void setUp() {
        DbBootstrap.initializeForTest();
    }

    @Test
    void 보드를_저장하면_모든_좌표가_DB에_저장된다() throws SQLException {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

        boardRepository.save(gameId, board);

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) AS piece_count FROM board WHERE game_id = ?")) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt("piece_count")).isEqualTo(90);
            }
        }
    }

    @Test
    void 보드를_저장하면_좌표별_팀과_기물종류가_정확히_저장된다() throws SQLException {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

        boardRepository.save(gameId, board);

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT team, piece_type FROM board WHERE game_id = ? AND row_idx = ? AND col_idx = ?")) {
            statement.setLong(1, gameId);
            statement.setInt(2, 8);
            statement.setInt(3, 4);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("team")).isEqualTo("CHO");
                assertThat(resultSet.getString("piece_type")).isEqualTo("JANG");
            }
        }
    }

    @Test
    void 저장된_보드를_빈보드로_덮어써도_모든_좌표가_유지된다() throws SQLException {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board initialBoard = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId, initialBoard);

        Board emptyBoard = new Board(createEmptyBoard());
        boardRepository.save(gameId, emptyBoard);

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) AS piece_count FROM board WHERE game_id = ?")) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt("piece_count")).isEqualTo(90);
            }
        }
    }

    @Test
    void loadBoard_저장된_보드를_정확히_복원한다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board originalBoard = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId, originalBoard);

        Map<Position, BasicPiece> loadedBoardMap = boardRepository.loadBoard(gameId);
        Board loadedBoard = new Board(loadedBoardMap);

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                Position position = new Position(row, column);
                BasicPiece originalPiece = originalBoard.findPiece(position);
                BasicPiece loadedPiece = loadedBoard.findPiece(position);

                if (originalPiece.isNone()) {
                    assertThat(loadedPiece.isNone()).isTrue();
                } else {
                    assertThat(loadedPiece.getPieceType()).isEqualTo(originalPiece.getPieceType());
                    assertThat(loadedPiece.getTeam()).isEqualTo(originalPiece.getTeam());
                }
            }
        }
    }

    @Test
    void loadBoard_저장되지_않은_보드는_모두_None으로_초기화된다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");

        Map<Position, BasicPiece> loadedBoardMap = boardRepository.loadBoard(gameId);
        Board loadedBoard = new Board(loadedBoardMap);

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                Position position = new Position(row, column);
                assertThat(loadedBoard.findPiece(position).isNone()).isTrue();
            }
        }
    }

    @Test
    void 빈칸은_NONE으로_저장된다() throws SQLException {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

        boardRepository.save(gameId, board);

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT team, piece_type FROM board WHERE game_id = ? AND row_idx = ? AND col_idx = ?")) {
            statement.setLong(1, gameId);
            statement.setInt(2, 4);
            statement.setInt(3, 4);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("team")).isEqualTo("NONE");
                assertThat(resultSet.getString("piece_type")).isEqualTo("NONE");
            }
        }
    }

    private Map<Position, BasicPiece> createEmptyBoard() {
        Map<Position, BasicPiece> board = new HashMap<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), None.getInstance());
            }
        }
        return board;
    }
}
