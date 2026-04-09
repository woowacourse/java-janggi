package dao;

import db.ConfigLoader;
import db.DbBootstrap;
import db.DbConnectionFactory;
import db.TransactionExecutor;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.BasicPiece;
import domain.piece.None;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardDaoTest {
    private final ConfigLoader configLoader = new ConfigLoader("application-test.properties");
    private final DbConnectionFactory dbConnectionFactory = new DbConnectionFactory(configLoader);
    private final TransactionExecutor transactionExecutor = new TransactionExecutor(dbConnectionFactory);
    private final BoardDao boardDao = new BoardDao(dbConnectionFactory, transactionExecutor);
    private final JanggiGameDao janggiGameDao = new JanggiGameDao(dbConnectionFactory);

    @BeforeEach
    void setUp() {
        DbBootstrap dbBootstrap = new DbBootstrap(dbConnectionFactory);
        dbBootstrap.initialize();
    }

    @Test
    void 보드를_저장하면_모든_좌표가_DB에_저장된다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

            boardDao.saveFullBoard(gameId, board);

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) AS piece_count FROM board WHERE game_id = ?")) {
                statement.setLong(1, gameId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    assertThat(resultSet.next()).isTrue();
                    assertThat(resultSet.getInt("piece_count")).isEqualTo(90);
                }
            }
        }
    }

    @Test
    void 보드를_저장하면_좌표별_팀과_기물종류가_정확히_저장된다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

            boardDao.saveFullBoard(gameId, board);

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT team, piece_type FROM board WHERE game_id = ? AND row_idx = ? AND col_idx = ?")) {
                statement.setLong(1, gameId);
                statement.setInt(2, 8); // 초나라 진영
                statement.setInt(3, 4); // 장(궁) 위치

                try (ResultSet resultSet = statement.executeQuery()) {
                    assertThat(resultSet.next()).isTrue();
                    assertThat(resultSet.getString("team")).isEqualTo("CHO");
                    assertThat(resultSet.getString("piece_type")).isEqualTo("JANG");
                }
            }
        }
    }

    @Test
    void loadBoard_저장된_보드를_정확히_복원한다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            Board originalBoard = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
            boardDao.saveFullBoard(gameId, originalBoard);

            Map<Position, BasicPiece> loadedBoardMap = boardDao.loadBoard(gameId);
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
    }

    @Test
    void loadBoard_저장되지_않은_보드는_모두_None으로_초기화된다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            // 게임만 만들고 보드는 저장하지 않은 상태
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");

            Map<Position, BasicPiece> loadedBoardMap = boardDao.loadBoard(gameId);
            Board loadedBoard = new Board(loadedBoardMap);

            for (int row = MIN_ROW; row <= MAX_ROW; row++) {
                for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                    Position position = new Position(row, column);
                    assertThat(loadedBoard.findPiece(position).isNone()).isTrue();
                }
            }
        }
    }

    @Test
    void 빈칸은_NONE으로_저장된다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

            boardDao.saveFullBoard(gameId, board);

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT team, piece_type FROM board WHERE game_id = ? AND row_idx = ? AND col_idx = ?")) {
                statement.setLong(1, gameId);
                statement.setInt(2, 4); // 한가운데 빈 공간
                statement.setInt(3, 4);

                try (ResultSet resultSet = statement.executeQuery()) {
                    assertThat(resultSet.next()).isTrue();
                    assertThat(resultSet.getString("team")).isEqualTo("NONE");
                    assertThat(resultSet.getString("piece_type")).isEqualTo("NONE");
                }
            }
        }
    }

    @Test
    void 이미_저장된_보드는_다시_저장할_수_없다() throws SQLException {
        try (Connection connection = dbConnectionFactory.createConnection()) {
            long gameId = janggiGameDao.createGame(connection, "CHO Player", "HAN Player");
            Board initialBoard = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));

            boardDao.saveFullBoard(gameId, initialBoard);
            Board emptyBoard = new Board(createEmptyBoard());
            assertThrows(IllegalStateException.class, () -> boardDao.saveFullBoard(gameId, emptyBoard));
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