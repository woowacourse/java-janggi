package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {
    private final Board board = new Board();
    private Piece soldier;

    @ParameterizedTest
    @CsvSource({
            "CHO, 6, 4, 5, 4",
            "HAN, 3, 4, 4, 4"
    })
    void 각_진영_전진_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        // given
        soldier = new Soldier(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(soldier.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 6, 4, 7, 4",
            "HAN, 3, 4, 2, 4"
    })
    void 각_진영_후진_불가_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        // given
        soldier = new Soldier(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(soldier.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 5, 4, 5, 3", // 초 서쪽 이동
            "CHO, 5, 4, 5, 5", // 초 동쪽 이동
            "HAN, 4, 4, 4, 3", // 한 서쪽 이동
            "HAN, 4, 4, 4, 5"  // 한 동쪽 이동
    })
    void 각_진영_좌우_이동_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        soldier = new Soldier(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        assertThat(soldier.canMove(from, to, board)).isTrue();
    }
}
