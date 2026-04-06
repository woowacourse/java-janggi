package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TankTest {
    private final Piece tank = new Tank(Team.HAN);
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 8", // 가로 끝에서 끝 (동쪽)
            "0, 8, 0, 0", // 가로 끝에서 끝 (서쪽)
            "0, 0, 9, 0", // 세로 끝에서 끝 (남쪽)
            "9, 0, 0, 0", // 세로 끝에서 끝 (북쪽)
            "5, 4, 5, 0", // 중간에서 가로 이동
            "5, 4, 0, 4"  // 중간에서 세로 이동
    })
    void 같은_행_또는_열의_칸으로_이동_테스트(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(tank.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 1", // 대각선 한 칸
            "0, 0, 2, 1", // 마의 길
            "5, 4, 6, 6", // 임의의 대각선
            "0, 0, 9, 8"  // 판의 반대쪽 대각선 끝
    })
    void 직선이_아닌_칸으로는_이동이_불가하다(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(tank.canMove(from, to, board)).isFalse();
    }

    @Test
    void 중간_경로에_기물이_있으면_이동이_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 5);

        board.place(new Position(0, 3), new Soldier(Team.HAN));

        // when, then
        assertThat(tank.canMove(from, to, board)).isFalse();
    }

    @ParameterizedTest
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

        // when & then
        assertThat(tank.canMove(from, to, board))
                .isTrue();
    }

    @Test
    void 궁성_밖_대각선_이동_실패_테스트() {
        // given
        Position from = new Position(8, 4);
        Position to = new Position(6, 2);

        // when, then
        assertThat(tank.canMove(from, to, board)).isFalse();
    }

    @Test
    void 궁성_관통_직선_이동_테스트() {
        // given
        Position from = new Position(5, 4);
        Position to = new Position(9, 4);

        // when, then
        assertThat(tank.canMove(from, to, board)).isTrue();
    }

    @Test
    void 궁성_안에서_밖으로_직선_이동_테스트() {
        // given
        Position from = new Position(8, 4);
        Position to = new Position(8, 1);

        // when, then
        assertThat(tank.canMove(from, to, board)).isTrue();
    }

    @Test
    void 궁성_밖에서_안으로_직선_이동_테스트() {
        // given
        Position from = new Position(8, 1);
        Position to = new Position(8, 4);

        // when, then
        assertThat(tank.canMove(from, to, board)).isTrue();
    }
}
