package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;
import janggi.domain.palace.Palace;
import janggi.domain.palace.PalaceFactory;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceDiagonalOneStepMoveRuleTest {
    private PalaceDiagonalOneStepMoveRule hanRule;

    @BeforeEach
    void setUp() {
        Palace hanPalace = PalaceFactory.createPalace(Team.HAN);
        hanRule = new PalaceDiagonalOneStepMoveRule(hanPalace);
    }

    @ParameterizedTest
    @DisplayName("궁성 대각선 좌표에서 대각선 1칸 이동이 가능하다.")
    @CsvSource({
            "4, 1, 5, 2",
            "6, 1, 5, 2",
            "5, 2, 4, 3",
            "5, 2, 6, 3",
            "5, 2, 4, 1",
            "5, 2, 6, 1",
            "4, 3, 5, 2",
            "6, 3, 5, 2"
    })
    void testCanMoveDiagonalOneStep(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(hanRule.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("궁성 대각선 좌표가 아니면 대각선 이동이 불가능하다.")
    @CsvSource({
            "4, 1, 5, 1",
            "5, 1, 6, 2",
            "4, 2, 5, 3"
    })
    void testCanNotMoveDiagonalFromNonDiagonalPosition(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(hanRule.canMove(from, to)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("대각선 2칸 이동은 불가능하다.")
    @CsvSource({
            "4, 1, 6, 3",
            "6, 1, 4, 3",
            "4, 3, 6, 1",
            "6, 3, 4, 1"
    })
    void testCanNotMoveDiagonalTwoSteps(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(hanRule.canMove(from, to)).isFalse();
    }
}
