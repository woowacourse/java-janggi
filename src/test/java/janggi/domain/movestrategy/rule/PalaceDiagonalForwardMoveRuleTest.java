package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;
import janggi.domain.palace.Palace;
import janggi.domain.palace.PalaceFactory;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceDiagonalForwardMoveRuleTest {

    private PalaceDiagonalForwardMoveRule rule;

    @BeforeEach
    void setUp() {
        List<Palace> palaces = List.of(
                PalaceFactory.createPalace(Team.HAN),
                PalaceFactory.createPalace(Team.CHO)
        );
        rule = new PalaceDiagonalForwardMoveRule(palaces);
    }

    @ParameterizedTest
    @DisplayName("궁성 대각선을 따라 1칸 이동이 가능하다.")
    @CsvSource({
            "4, 1, 5, 2",
            "5, 2, 6, 3",
            "6, 3, 5, 2",
            "5, 2, 4, 1",
            "4, 8, 5, 9",
            "5, 9, 6, 10"
    })
     void testCanMoveDiagonalOneStep(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(rule.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("궁성 대각선을 따라 2칸 이동이 가능하다.")
    @CsvSource({
            "4, 1, 6, 3",
            "6, 3, 4, 1",
            "6, 1, 4, 3",
            "4, 3, 6, 1",
            "4, 8, 6, 10",
            "6, 10, 4, 8"
    })
    void testCanMoveDiagonalTwoSteps(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(rule.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("2칸 대각선 이동 시 중간 경로를 반환한다.")
    @CsvSource({
            "4, 1, 6, 3, 5, 2",
            "6, 3, 4, 1, 5, 2",
            "6, 1, 4, 3, 5, 2",
            "4, 3, 6, 1, 5, 2"
    })
    void testFindPathForTwoStepDiagonal(int x1, int y1, int x2, int y2, int mx, int my) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when
        List<Position> path = rule.findPath(from, to);

        // then
        assertThat(path).containsExactly(new Position(mx, my));
    }

    @ParameterizedTest
    @DisplayName("1칸 대각선 이동 시 경로가 비어있다.")
    @CsvSource({
            "4, 1, 5, 2",
            "5, 2, 6, 3"
    })
    void testFindPathForOneStepDiagonal(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when
        List<Position> path = rule.findPath(from, to);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("궁성 대각선 좌표가 아니면 이동이 불가능하다.")
    @CsvSource({
            "4, 1, 5, 1",
            "5, 1, 6, 2",
            "4, 2, 6, 2"
    })
    void testCanNotMoveFromNonDiagonalPosition(int x1, int y1, int x2, int y2) {
        // given
        Position from = new Position(x1, y1);
        Position to = new Position(x2, y2);

        // when & then
        assertThat(rule.canMove(from, to)).isFalse();
    }

}
