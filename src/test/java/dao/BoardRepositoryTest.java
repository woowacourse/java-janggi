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
import infra.db.DbBootstrap;
import infra.db.DbConnectionFactory;
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
        DbBootstrap.initialize();
    }

    @Test
    void 보드를_저장하면_None을_제외한_기물_정보가_DB에_저장된다() throws SQLException {
        long gameId = gameRoom.createGame();
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

        boardRepository.save(gameId, board);

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) AS piece_count FROM board WHERE game_id = ?")) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt("piece_count")).isEqualTo(32);
            }
        }
    }

    @Test
    void 보드를_저장하면_좌표별_팀과_기물종류가_정확히_저장된다() throws SQLException {
        long gameId = gameRoom.createGame();
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
    void 저장된_좌표가_사라지면_DB에서도_삭제된다() throws SQLException {
        long gameId = gameRoom.createGame();
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
                assertThat(resultSet.getInt("piece_count")).isZero();
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
