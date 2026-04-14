package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    private final Piece king = new King(Team.HAN);
    private Board board;

    @ParameterizedTest
    @CsvSource({
            "1, 4, 0, 4", // 북
            "1, 4, 2, 4", // 남
            "1, 4, 1, 3", // 서
            "1, 4, 1, 5", // 동
            "2, 3, 1, 4", // 북동
            "2, 5, 1, 4", // 북서
            "0, 3, 1, 4", // 남동
            "0, 5, 1, 4" // 남서
    })
    void 정상_이동_true_반환_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        board = new Board(Map.of(from, new King(Team.HAN)));

        // when, then
        assertThat(king.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, 0, 4"
    })
    void 모서리에서의_정상_이동_true_반환_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        board = new Board(Map.of(from, new Advisor(Team.HAN)));

        // when, then
        assertThat(king.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 2, 4", // 남쪽 두 칸
            "0, 4, 0, 7", // 동쪽 세 칸
            "0, 0, 2, 0"  // 경계에서 두 칸 이동
    })
    void 한_칸_초과_이동시_false_반환_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        board = new Board(Map.of(from, new Advisor(Team.HAN)));

        // when, then
        assertThat(king.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 1, 3", // 잘못된 대각선
            "0, 4, 1, 5",
            "2, 4, 1, 3",
            "2, 4, 1, 5",
            "0, 3, 0, 2", // 궁성 바깥
            "1, 3, 1, 2",
            "2, 3, 2, 2",
            "2, 3, 3, 3",
            "2, 4, 3, 4",
            "2, 5, 3, 5",
            "2, 5, 2, 6",
            "1, 5, 1, 6",
            "0, 5, 0, 6"
    })
    void 허용되지_않은_이동시_예외가_발생한다(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        board = new Board(Map.of(from, new Advisor(Team.HAN)));

        // when, then
        assertThat(king.canMove(from, to, board)).isFalse();
    }
}
