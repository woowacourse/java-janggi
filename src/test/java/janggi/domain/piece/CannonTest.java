package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {
    private final Piece cannon = new Cannon(Team.HAN);
    private Board board;

    @Test
    void 하나의_기물을_넘는_정상_이동_테스트() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = Board.createBoardWith(new Position(0, 2), new Soldier(Team.CHO));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isTrue();
    }

    @Test
    void 하나의_기물을_넘어_상대_기물_잡기_테스트() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = Board.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                to, new Advisor(Team.HAN));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isTrue();
    }

    @Test
    void 넘을_기물이_없으면_이동_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = new Board();

        // when, then
        assertThat(cannon.canMove(from, to, board)).isFalse();
    }


    @Test
    void 경로에_기물이_2개_이상이면_이동_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 5);

        board = Board.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                new Position(0, 4), new Advisor(Team.HAN));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isFalse();
    }

    @Test
    void 포는_다리로_사용할_수_없다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = Board.createBoardWith(new Position(0, 2), new Cannon(Team.CHO));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isFalse();
    }

    @Test
    void 포는_잡을_수_없다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = Board.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                to, new Cannon(Team.HAN));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest(name = "직선 이동이 아닌 경우 실패: {0}, {1} -> {2}, {3}")
    @CsvSource({
            "0, 0, 2, 2", // 대각선 이동
            "0, 0, 1, 2", // 마(Horse)와 같은 이동
            "5, 5, 7, 6", // 상(Elephant)과 같은 이동
            "3, 3, 4, 5"  // 무작위 좌표
    })
    void 직선_이동이_아닌_경우_이동_불가하다(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);
        board = new Board();

        // when, then
        assertThat(cannon.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest(name = "궁성 대각선 이동 {index}: {0} -> {1}")
    @CsvSource({
            "7, 3, 9, 5",
            "9, 5, 7, 3",
            "7, 5, 9, 3",
            "9, 3, 7, 5"
    })
    void 궁성_안에서_대각선_이동_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        board = Board.createBoardWith(new Position(8, 4), new Soldier(Team.HAN));

        // when & then
        assertThat(cannon.canMove(from, to, board))
                .isTrue();
    }

    @Test
    void 궁성_밖_대각선_이동_실패_테스트() {
        // given
        Position from = new Position(8, 4);
        Position to = new Position(6, 2);

        board = Board.createBoardWith(new Position(7, 3), new Soldier(Team.HAN));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isFalse();
    }

    @Test
    void 궁성_관통_직선_이동_테스트() {
        // given
        Position from = new Position(5, 4);
        Position to = new Position(9, 4);

        board = Board.createBoardWith(new Position(7, 4), new Soldier(Team.CHO));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isTrue();
    }

    @Test
    void 궁성_안에서_밖으로_직선_이동_테스트() {
        // given
        Position from = new Position(8, 4);
        Position to = new Position(8, 1);

        board = Board.createBoardWith(new Position(8, 3), new Soldier(Team.HAN));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isTrue();
    }

    @Test
    void 궁성_밖에서_안으로_직선_이동_테스트() {
        // given
        Position from = new Position(8, 1);
        Position to = new Position(8, 4);

        board = Board.createBoardWith(new Position(8, 3), new Soldier(Team.CHO));

        // when, then
        assertThat(cannon.canMove(from, to, board)).isTrue();
    }
}