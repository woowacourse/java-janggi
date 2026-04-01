package janggi.domain.piece;

import janggi.domain.FakeBoard;
import janggi.domain.board.BoardView;
import janggi.domain.vo.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {
    private final Piece king = new King(Team.HAN);
    private BoardView fakeBoard;

    @ParameterizedTest
    @CsvSource({
            "1, 4, 0, 4",
            "1, 4, 2, 4",
            "1, 4, 1, 3",
            "1, 4, 1, 5"
    })
    void 정상_이동_true_반환_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        fakeBoard = new FakeBoard(Map.of(from, new King(Team.HAN)));

        // when, then
        assertThat(king.moveRule().canMove(from, to, fakeBoard)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, 0, 4"
    })
    void 모서리에서의_정상_이동_true_반환_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        fakeBoard = new FakeBoard(Map.of(from, new King(Team.HAN)));

        // when, then
        assertThat(king.moveRule().canMove(from, to, fakeBoard)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 2, 4", // 남쪽 두 칸
            "0, 4, 0, 7", // 동쪽 세 칸
            "0, 4, 1, 5", // 대각선 한 칸 (사이클 1 비허용)
            "0, 0, 2, 0"  // 경계에서 두 칸 이동
    })
    void 한_칸_초과_이동시_false_반환_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        fakeBoard = new FakeBoard(Map.of(from, new King(Team.HAN)));

        // when, then
        assertThat(king.moveRule().canMove(from, to, fakeBoard)).isFalse();
    }
}
