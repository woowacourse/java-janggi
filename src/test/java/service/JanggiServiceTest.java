package service;

import db.H2DatabaseConnector;
import domain.board.Point;
import domain.piece.Byeong;
import domain.piece.Piece;
import domain.piece.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("장기 서비스 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JanggiServiceTest {

    private static final int DEFAULT_BOARD_ID = 1;

    private JanggiService janggiService;

    @BeforeEach
    void setUp() {
        janggiService = new JanggiService(new H2DatabaseConnector());
        createBoardTable();
        createTurnTable();
        insertBoard();
        insertTurn();
    }

    @AfterEach
    void tearDown() {
        dropBoardTable();
        dropTurnTable();
    }

    private void createBoardTable() {
        String query = """
                    CREATE TABLE IF NOT EXISTS `board` (
                        board_id        INT NOT NULL,
                    	point_row       INT NOT NULL,
                    	point_column    INT NOT NULL,
                    	team            VARCHAR(3) NOT NULL,
                    	piece_type       VARCHAR(6) NOT NULL,
                    	PRIMARY KEY (board_id, point_row, point_column)
                    );
                    """;
        try (final Connection connection = new H2DatabaseConnector().getConnection();
             final Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 Board 테이블 생성 오류: " + e.getMessage(), e);
        }
    }

    private void insertBoard() {
        String query = "INSERT INTO board (board_id, point_row, point_column, team, piece_type) VALUES(1, 1, 1, 'CHO', 'PO')";
        try (final Connection connection = new H2DatabaseConnector().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 Board 초기 데이터 삽입 오류: " + e.getMessage(), e);
        }
    }

    private void createTurnTable() {
        String query = """
                    CREATE TABLE IF NOT EXISTS `turn` (
                        turn VARCHAR(3) PRIMARY KEY
                    );
                    """;
        try (final Connection connection = new H2DatabaseConnector().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 Turn 테이블 생성 오류: " + e.getMessage(), e);
        }
    }

    private void insertTurn() {
        String query = "INSERT INTO turn (turn) VALUES('CHO')";
        try (final Connection connection = new H2DatabaseConnector().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 Turn 초기 데이터 삽입 오류: " + e.getMessage(), e);
        }
    }

    private void dropBoardTable() {
        String query = "DROP table board";
        try (final Connection connection = new H2DatabaseConnector().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 Board 테이블 드랍 오류: " + e.getMessage(), e);
        }
    }

    private void dropTurnTable() {
        String query = "DROP table turn";
        try (final Connection connection = new H2DatabaseConnector().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] H2 Turn 테이블 드랍 오류: " + e.getMessage(), e);
        }
    }

    @Test
    void 저장된_데이터가_있다면_true를_반환한다() {
       assertThat(janggiService.hasSavedGame()).isTrue();
    }

    @Test
    void 저장된_데이터가_없다면_false를_반환한다() {
        janggiService.removeAllData(DEFAULT_BOARD_ID);
        assertThat(janggiService.hasSavedGame()).isFalse();
    }

    @Test
    void 보드를_조회할_수_있다() {
        assertThat(janggiService.findBoard(DEFAULT_BOARD_ID)).hasSize(1);
    }

    @Test
    void 턴을_조회할_수_있다() {
        assertThat(janggiService.findTurn()).isNotNull();
    }

    @Test
    void 보드와_턴을_저장할_수_있다() {
        final Team turn = Team.HAN;
        final Map<Point, Piece> board = Map.of(
                Point.of(1, 2), new Byeong(Team.CHO)
        );

        assertThatCode(() -> janggiService.saveAllData(board, turn, DEFAULT_BOARD_ID))
                .doesNotThrowAnyException();
    }

    @Test
    void 보드와_턴을_삭제할_수_있다() {
        assertThatCode(() -> janggiService.removeAllData(DEFAULT_BOARD_ID))
                .doesNotThrowAnyException();
    }
}
