package janggi.domain.piece;

import janggi.domain.FakeBoard;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {
    private final Piece cannon = new Cannon(Team.HAN);
    private FakeBoard board;

    @Test
    void 하나의_기물을_넘는_정상_이동_테스트() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO));

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isTrue();
    }

    @Test
    void 하나의_기물을_넘어_상대_기물_잡기_테스트() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                to, new Advisor(Team.HAN));

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isTrue();
    }

    @Test
    void 넘을_기물이_없으면_이동_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = new FakeBoard();

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isFalse();
    }


    @Test
    void 경로에_기물이_2개_이상이면_이동_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 5);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                new Position(0, 4), new Advisor(Team.HAN));

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isFalse();
    }

    @Test
    void 포는_다리로_사용할_수_없다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Cannon(Team.CHO));

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isFalse();
    }

    @Test
    void 포는_잡을_수_없다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                to, new Cannon(Team.HAN));

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest(name = "직선 이동이 아닌 경우 실패: {0}, {1} -> {2}, {3}")
    @CsvSource({
            "0, 0, 2, 2", // 대각선 이동
            "0, 0, 1, 2", // 마(Horse)와 같은 이동
            "5, 5, 7, 6", // 상(Elephant)과 같은 이동
            "3, 3, 4, 5"  // 무작위 좌표
    })
    void 직선_이동이_아닌_경우_이동_불가하다(int fR, int fC, int tR, int tC) {
        // given
        Position from = new Position(fR, fC);
        Position to = new Position(tR, tC);
        board = new FakeBoard();

        // when, then
        assertThat(cannon.moveRule().canMove(from, to, board)).isFalse();
    }
}