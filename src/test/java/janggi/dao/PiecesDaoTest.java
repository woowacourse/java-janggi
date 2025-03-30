package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.HorseSide;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesDaoTest {

    private PiecesDao piecesDao = null;

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess_test"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root";

    @BeforeEach
    void setUp() {
        // 드라이버 연결
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                USERNAME, PASSWORD);
            piecesDao = new PiecesDao(connection);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterEach
    void deleteAll() throws SQLException {
        piecesDao.deletePieces();
    }

    @Test
    @DisplayName("저장된 게임이 없으면 빈 map을 반환한다")
    void noPieces() throws SQLException {
        assertThat(piecesDao.loadPieces()).isEmpty();
    }

    @Test
    @DisplayName("게임이 잘 저장된다")
    void piecesSave() throws SQLException {
        Board board = BoardFactory.getInitializedBoard(
            HorseSide.LEFT,
            HorseSide.LEFT,
            HorseSide.LEFT,
            HorseSide.LEFT
        );
        piecesDao.savePieces(board.getPieces());
        assertThat(piecesDao.loadPieces()).isEqualTo(board.getPieces());
    }

    @Test
    @DisplayName("게임이 잘 지워진다")
    void piecesDelete() throws SQLException {
        Board board = BoardFactory.getInitializedBoard(
            HorseSide.LEFT,
            HorseSide.LEFT,
            HorseSide.LEFT,
            HorseSide.LEFT
        );
        piecesDao.savePieces(board.getPieces());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(piecesDao.loadPieces()).isEqualTo(board.getPieces());
        piecesDao.deletePieces();
        softly.assertThat(piecesDao.loadPieces()).isEmpty();
        softly.assertAll();
    }
}
