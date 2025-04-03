package repository.dao;

import janggi.board.Board;
import janggi.board.Position;
import janggi.board.strategy.NormalPlaceStrategy;
import janggi.piece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.connection.ConnectionManager;
import repository.connection.H2ConnectManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class BoardDAOTest {

    private static ConnectionManager connectionManager;
    private static BoardDAO boardDAO;
    private static Connection connection;

    @BeforeEach
    void setUpTestDatabase() throws SQLException {
        connectionManager = new H2ConnectManager();
        boardDAO = new BoardDAO(connectionManager);
        connection = connectionManager.getConnection();

        String pieceTable = """
                CREATE TABLE IF NOT EXISTS piece (
                    id              BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    piece_type_name VARCHAR(20) NOT NULL,
                    team_name       VARCHAR(20) NOT NULL,
                    position_column INT         NOT NULL,
                    position_row    INT         NOT NULL,
                    UNIQUE (position_column, position_row)
                );
                """;

        connection.prepareStatement(pieceTable).execute();

        boardDAO.resetPieces();
    }

    @Test
    void 초기_보드를_초기화한다() {
        Board board = new Board(new NormalPlaceStrategy());

        assertThatCode(() -> boardDAO.saveInitialBoard(board.getBoard()))
                .doesNotThrowAnyException();
    }

    @Test
    void 저장한_피스의_개수를_확인한다() {
        initializeBoard();
        Map<Position, Piece> allPiecesOnBoard = boardDAO.findAllPiecesOnBoard();

        assertThat(allPiecesOnBoard.keySet().size()).isEqualTo(32);
    }

    @Test
    void 기물의_위치를_업데이트한다() {
        initializeBoard();
        Position start = new Position(0, 0);
        Position goal = new Position(0, 1);

        Piece pieceBeforeMove = boardDAO.findAllPiecesOnBoard().get(start);

        boardDAO.updatePiecePosition(start, goal);

        Map<Position, Piece> allPiecesOnBoard = boardDAO.findAllPiecesOnBoard();

        assertThat(allPiecesOnBoard.get(start)).isEqualTo(null);
        assertThat(allPiecesOnBoard.get(goal).getType()).isEqualTo(pieceBeforeMove.getType());
    }

    @Test
    void 목표_위치에_있던_상대_기물이_삭제되는지_확인한다() {
        initializeBoard();
        Position start = new Position(0, 0);
        Position goal = new Position(0, 1);

        boardDAO.updatePiecePosition(start, goal);
        Map<Position, Piece> allPiecesOnBoard = boardDAO.findAllPiecesOnBoard();

        assertThat(allPiecesOnBoard.get(goal)).isNotNull();
        assertThat(allPiecesOnBoard.get(start)).isNull();
    }

    @Test
    void 존재하지_않는_기물을_이동시키면_예외를_던진다() {
        assertThatCode(() -> boardDAO.updatePiecePosition(new Position(5, 5), new Position(5, 6)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("기물 이동 실패");
    }

    @Test
    void 보드를_초기화하면_모든_기물이_삭제된다() {
        initializeBoard();
        boardDAO.resetPieces();
        Map<Position, Piece> allPiecesOnBoard = boardDAO.findAllPiecesOnBoard();

        assertThat(allPiecesOnBoard).isEmpty();
    }

    private void initializeBoard() {
        Board board = new Board(new NormalPlaceStrategy());
        boardDAO.saveInitialBoard(board.getBoard());
    }
}
