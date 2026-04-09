package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PalaceTest {

    @ParameterizedTest
    @CsvSource({
            "0, 3", "0, 4", "0, 5",
            "1, 3", "1, 4", "1, 5",
            "2, 3", "2, 4", "2, 5",
            "7, 3", "7, 4", "7, 5",
            "8, 3", "8, 4", "8, 5",
            "9, 3", "9, 4", "9, 5"
    })
    void 궁성_내부_좌표를_판별한다(int row, int col) {
        assertThat(PalaceRule.isInsidePalace(new Position(row, col))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0", "3, 4", "5, 5", "6, 4"
    })
    void 궁성_외부_좌표를_판별한다(int row, int col) {
        assertThat(PalaceRule.isInsidePalace(new Position(row, col))).isFalse();
    }

    @Test
    void 한_궁성_중앙에서_꼭짓점으로_대각선_이동_가능() {
        assertThat(PalaceRule.canMoveDiagonally(
                new Position(1, 4), new Position(0, 3))).isTrue();
    }

    @Test
    void 한_궁성_꼭짓점에서_중앙으로_대각선_이동_가능() {
        assertThat(PalaceRule.canMoveDiagonally(
                new Position(2, 5), new Position(1, 4))).isTrue();
    }

    @Test
    void 초_궁성_중앙에서_꼭짓점으로_대각선_이동_가능() {
        assertThat(PalaceRule.canMoveDiagonally(
                new Position(8, 4), new Position(7, 3))).isTrue();
    }

    @Test
    void 궁성_내부지만_대각선_선이_아닌_곳은_불가() {
        assertThat(PalaceRule.canMoveDiagonally(
                new Position(0, 3), new Position(1, 3))).isFalse();
    }

    @Test
    void 대각선_2칸_이동_불가() {
        assertThat(PalaceRule.canMoveDiagonally(
                new Position(0, 3), new Position(2, 5))).isFalse();
    }

    @Test
    void 궁성_외부에서는_대각선_이동_불가() {
        assertThat(PalaceRule.canMoveDiagonally(
                new Position(4, 4), new Position(5, 5))).isFalse();
    }

    //  2칸 대각선 판별 차, 포 전용 - 한나라, 초나라 구분 없이 시작
    @Test
    void 궁성_꼭짓점에서_반대_꼭짓점으로_2칸_대각선_이동_판별() {
        assertThat(PalaceRule.isDiagonalInPalace(
                new Position(0, 3), new Position(2, 5))).isTrue();
        assertThat(PalaceRule.isDiagonalInPalace(
                new Position(0, 5), new Position(2, 3))).isTrue();
        assertThat(PalaceRule.isDiagonalInPalace(
                new Position(7, 3), new Position(9, 5))).isTrue();
    }

    @Test
    void 궁성_1칸_대각선도_판별_가능() {
        assertThat(PalaceRule.isDiagonalInPalace(
                new Position(1, 4), new Position(0, 3))).isTrue();
    }


    @Test
    void 궁성_2칸_대각선의_중간_경유지는_궁성_중앙() {
        Position midpoint = PalaceRule.getDiagonalMidpoint(
                new Position(0, 3), new Position(2, 5));
        assertThat(midpoint).isEqualTo(new Position(1, 4));
    }


    @Test
    void 궁성_1칸_대각선은_중간_경유지_없음() {
        Position midpoint = PalaceRule.getDiagonalMidpoint(
                new Position(1, 4), new Position(0, 3));
        assertThat(midpoint).isNull();
    }

}
