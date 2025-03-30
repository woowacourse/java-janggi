package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Team;
import janggi.domain.Turn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private TurnDao turnDao = null;

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
            turnDao = new TurnDao(connection);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterEach
    void deleteAll() throws SQLException {
        turnDao.deleteTurn();
    }

    @Test
    @DisplayName("저장된 턴이 없으면 예외를 던진다")
    void noPieces() {
        Assertions.assertThatThrownBy(
                () -> turnDao.loadTurn())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("턴이 잘 저장된다")
    void piecesSave() throws SQLException {
        turnDao.saveTurn(new Turn(Team.BLUE));
        assertThat(turnDao.loadTurn().getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    @DisplayName("턴이 잘 지워진다")
    void piecesDelete() throws SQLException {
        turnDao.saveTurn(new Turn(Team.RED));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(turnDao.loadTurn().getTeam()).isEqualTo(Team.RED);
        turnDao.deleteTurn();
        softly.assertThatThrownBy(
                () -> turnDao.loadTurn())
            .isInstanceOf(IllegalArgumentException.class);
        softly.assertAll();
    }
}
