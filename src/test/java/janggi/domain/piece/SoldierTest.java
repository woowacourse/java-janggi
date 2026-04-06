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

    @ParameterizedTest(name = "{index}: {0}팀 졸 ({1},{2}) -> ({3},{4}) 전진 대각선 이동")
    @CsvSource({
            "CHO, 2, 3, 1, 4", // 초(CHO) 졸: 한 궁성 좌측 아래 -> 중앙 (전진 대각선)
            "CHO, 2, 5, 1, 4", // 초(CHO) 졸: 한 궁성 우측 아래 -> 중앙 (전진 대각선)
            "CHO, 1, 4, 0, 3", // 초(CHO) 졸: 한 궁성 중앙 -> 좌측 위 (전진 대각선)
            "CHO, 1, 4, 0, 5", // 초(CHO) 졸: 한 궁성 중앙 -> 우측 위 (전진 대각선)
            "HAN, 7, 3, 8, 4", // 한(HAN) 졸: 초 궁성 좌측 위 -> 중앙 (전진 대각선)
            "HAN, 7, 5, 8, 4", // 한(HAN) 졸: 초 궁성 우측 위 -> 중앙 (전진 대각선)
            "HAN, 8, 4, 9, 3", // 한(HAN) 졸: 초 궁성 중앙 -> 좌측 아래 (전진 대각선)
            "HAN, 8, 4, 9, 5"  // 한(HAN) 졸: 초 궁성 중앙 -> 우측 아래 (전진 대각선)
    })
    void 궁성_내_대각선_전진_이동_성공_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        soldier = new Soldier(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        assertThat(soldier.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest(name = "{index}: {0}팀 졸 ({1},{2}) -> ({3},{4}) 잘못된 대각선 이동 시도")
    @CsvSource({
            "CHO, 5, 4, 4, 3", // 궁성 밖 대각선 이동
            "HAN, 4, 4, 5, 5", // 궁성 밖 대각선 이동
            "CHO, 0, 3, 1, 4", // 초(CHO) 졸: 한 궁성 내에서 후퇴 방향 대각선 이동
            "HAN, 9, 5, 8, 4"  // 한(HAN) 졸: 초 궁성 내에서 후퇴 방향 대각선 이동
    })
    void 대각선_이동_실패_테스트(Team team, int fromRow, int fromCol, int toRow, int toCol) {
        soldier = new Soldier(team);
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        assertThat(soldier.canMove(from, to, board)).isFalse();
    }
}
