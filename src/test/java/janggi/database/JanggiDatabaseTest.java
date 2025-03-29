package janggi.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import janggi.piece.Chariot;
import janggi.piece.Country;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.sql.Connection;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiDatabaseTest {

    private static final JanggiDatabase database = new JanggiDatabase();

    @BeforeEach
    void initDatabase(){
        database.removeAllJanggiRows();
    }

    @AfterAll
    static void initDatabaseForEndTest(){
        database.removeAllJanggiRows();
    }

    @DisplayName("커넥션 생성")
    @Test
    void getConnection() {
        // given & when
        final Connection connection = database.getConnection();

        // then
        assertThat(connection).isNotNull();
    }

    @DisplayName("테이블 생성")
    @Test
    void createTables() {
        // given & when & then
        assertThatCode(() -> {
            database.createJanggiTables();
        }).doesNotThrowAnyException();
    }

    @DisplayName("테이블 존재 여부 반환")
    @Test
    void existsTable() {
        // given
        createTablesForTest();

        // when
        final boolean actual = database.existsJanggiTable();

        // then
        assertThat(actual).isTrue();
    }

    private void createTablesForTest() {
        try {
            database.createJanggiTables();
        } catch (final IllegalStateException e){
        }
    }

    @DisplayName("Board 정보를 저장한다.")
    @Test
    void saveBoard() {
        // given
        final Board board = createBoard();

        // when & then
        assertThatCode(() -> {
            database.saveBoard(board);
        }).doesNotThrowAnyException();
    }

    private static Board createBoard() {
        final Map<JanggiPosition, Piece> janggiBoard = Map.of(
                new JanggiPosition(1, 1), new Chariot(Country.HAN),
                new JanggiPosition(1, 2), new Chariot(Country.HAN),
                new JanggiPosition(1, 3), new Chariot(Country.HAN)
        );
        final Board board = new Board(janggiBoard);
        return board;
    }

    @DisplayName("Board 정보를 불러온다.")
    @Test
    void readBoard(){
        // given
        final Board board = createBoard();
        database.saveBoard(board);

        // when
        final Board actual = database.readBoard();

        // then
        assertThat(actual.getJanggiBoard()).hasSize(3);
    }
    
    @DisplayName("장기 데이터가 존재하는지를 반환한다.")
    @Test
    void existsJanggiRows() {
        // given
        final Board board = createBoard();
        database.saveBoard(board);

        // when
        final boolean actual = database.existsJanggiRows();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("장기 데이터를 모두 제거한다.")
    @Test
    void removeAllJanggiRows() {
        // given
        final Board board = createBoard();
        database.saveBoard(board);

        // when
        database.removeAllJanggiRows();
        final boolean actual = database.existsJanggiRows();

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("특정 x, y를 가진 행을 제거한다.")
    @Test
    void removeJanggiRowByPosition() {
        // given
        final Board board = createBoard();
        database.saveBoard(board);
        final JanggiPosition janggiPosition = new JanggiPosition(1, 1);

        // when
        final boolean actual = database.removeJanggiRowByPosition(janggiPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 x, y를 가진 행을 수정한다.")
    @Test
    void updateJanggiRowByPosition() {
        // given
        final Board board = createBoard();
        database.saveBoard(board);
        final JanggiPosition janggiPosition = new JanggiPosition(1, 4);
        final Piece piece = new Soldier(Country.CHO);

        // when
        final boolean actual = database.updateJanggiRowByPosition(janggiPosition, piece);

        // then
        assertThat(actual).isTrue();
    }
}
