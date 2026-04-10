package janggi.domain.movestrategy.rule;


import janggi.domain.board.BoardDirection;
import janggi.domain.board.Position;
import janggi.domain.palace.Palace;
import janggi.domain.palace.PalaceFactory;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceDiagonalDirectionalOneStepMoveRuleTest {

    private PalaceDiagonalDirectionalOneStepMoveRule hanRule;
    private PalaceDiagonalDirectionalOneStepMoveRule choRule;

    @BeforeEach
    void setUp() {
        Palace hanPalace = PalaceFactory.createPalace(Team.HAN);
        Palace choPalace = PalaceFactory.createPalace(Team.CHO);
        hanRule = new PalaceDiagonalDirectionalOneStepMoveRule(
                hanPalace, BoardDirection.UP);
        choRule = new PalaceDiagonalDirectionalOneStepMoveRule(
                choPalace, BoardDirection.DOWN);
    }

    @ParameterizedTest
    @DisplayName("한 진영 졸은 궁성 내에서 전진 방향 대각선 1칸 이동이 가능하다.")
    @CsvSource({
            "4, 1, 5, 2",
            "6, 1, 5, 2",
            "5, 2, 4, 3",
            "5, 2, 6, 3"
    })
    void testHanSoliderCanMoveDiagonalForward(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(hanRule.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("한 진영 졸은 궁성 내에서 후진 방향 대각선 이동이 불가능하다.")
    @CsvSource({
            "5, 2, 4, 1",
            "5, 2, 6, 1",
            "4, 3, 5, 2",
            "6, 3, 5, 2"
    })
    void testHanSoliderCanNotMoveDiagonalBackward(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(hanRule.canMove(from, to)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("초 진영 졸은 궁성 내에서 전진 방향 대각선 1칸 이동이 가능하다.")
    @CsvSource({
            "4, 10, 5, 9",
            "6, 10, 5, 9",
            "5, 9, 4, 8",
            "5, 9, 6, 8"
    })
    void testChoSoliderCanMoveDiagonalForward(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(choRule.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("초 진영 졸은 궁성에서 후진 방향 대각선 이동이 불가능하다.")
    @CsvSource({
            "5, 9, 4, 10",
            "5, 9, 6, 10",
            "4, 8, 5, 9",
            "6, 8, 5, 9"
    })
    void testChoSoliderCanNotMoveDiagonalBackward(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(choRule.canMove(from, to)).isFalse();
    }

}
