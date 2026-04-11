package janggi.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

@DisplayName("궁성(Palace) 테스트")
class PalaceTest {

    @DisplayName("궁성 범위 내 위치는 contains가 true를 반환한다.")
    @Test
    void contains_PositionInsidePalace() {
        assertAll(
                () -> assertThat(Palace.HAN.contains(Position.from("25"))).isTrue(),
                () -> assertThat(Palace.HAN.contains(Position.from("14"))).isTrue(),
                () -> assertThat(Palace.CHO.contains(Position.from("95"))).isTrue(),
                () -> assertThat(Palace.CHO.contains(Position.from("84"))).isTrue()
        );
    }

    @DisplayName("궁성 범위 밖 위치는 contains가 false를 반환한다.")
    @Test
    void contains_PositionOutsidePalace() {
        assertAll(
                () -> assertThat(Palace.HAN.contains(Position.from("45"))).isFalse(),
                () -> assertThat(Palace.HAN.contains(Position.from("23"))).isFalse(),
                () -> assertThat(Palace.CHO.contains(Position.from("75"))).isFalse()
        );
    }

    @DisplayName("궁성 대각선 선 위의 위치(꼭짓점, 중심)는 isOnDiagonalLine이 true를 반환한다.")
    @Test
    void isOnDiagonalLine_DiagonalPositions() {
        assertAll(
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("14"))).isTrue(),
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("16"))).isTrue(),
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("25"))).isTrue(),
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("34"))).isTrue(),
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("36"))).isTrue(),
                () -> assertThat(Palace.CHO.isOnDiagonalLine(Position.from("95"))).isTrue(),
                () -> assertThat(Palace.CHO.isOnDiagonalLine(Position.from("84"))).isTrue(),
                () -> assertThat(Palace.CHO.isOnDiagonalLine(Position.from("04"))).isTrue(),
                () -> assertThat(Palace.CHO.isOnDiagonalLine(Position.from("06"))).isTrue()
        );
    }

    @DisplayName("궁성 대각선 선 위가 아닌 위치는 isOnDiagonalLine이 false를 반환한다.")
    @Test
    void isOnDiagonalLine_NonDiagonalPositions() {
        assertAll(
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("15"))).isFalse(),
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("24"))).isFalse(),
                () -> assertThat(Palace.HAN.isOnDiagonalLine(Position.from("45"))).isFalse()
        );
    }

    @DisplayName("대각선 선 위 1칸 이동은 canMoveDiagonally가 true를 반환한다.")
    @Test
    void canMoveDiagonally_OneStep() {
        assertAll(
                () -> assertThat(Palace.HAN.canMoveDiagonally(Position.from("25"), Position.from("14"))).isTrue(),
                () -> assertThat(Palace.HAN.canMoveDiagonally(Position.from("25"), Position.from("36"))).isTrue(),
                () -> assertThat(Palace.CHO.canMoveDiagonally(Position.from("95"), Position.from("84"))).isTrue()
        );
    }

    @DisplayName("대각선 선 위 2칸 이동은 canMoveDiagonally가 true를 반환한다.")
    @Test
    void canMoveDiagonally_TwoStep() {
        assertAll(
                () -> assertThat(Palace.HAN.canMoveDiagonally(Position.from("14"), Position.from("36"))).isTrue(),
                () -> assertThat(Palace.HAN.canMoveDiagonally(Position.from("16"), Position.from("34"))).isTrue(),
                () -> assertThat(Palace.CHO.canMoveDiagonally(Position.from("84"), Position.from("06"))).isTrue()
        );
    }

    @DisplayName("직선 이동은 canMoveDiagonally가 false를 반환한다.")
    @Test
    void canMoveDiagonally_StraightMove() {
        assertThat(Palace.HAN.canMoveDiagonally(Position.from("25"), Position.from("24"))).isFalse();
    }

    @DisplayName("2칸 대각선 이동의 경로는 중심 위치를 포함한다.")
    @Test
    void getDiagonalPath_TwoStep_ReturnsCenterPosition() {
        List<Position> path = Palace.HAN.getDiagonalPath(Position.from("14"), Position.from("36"));
        assertThat(path).containsExactly(Position.from("25"));
    }

    @DisplayName("1칸 대각선 이동의 경로는 비어있다.")
    @Test
    void getDiagonalPath_OneStep_ReturnsEmptyPath() {
        List<Position> path = Palace.HAN.getDiagonalPath(Position.from("25"), Position.from("14"));
        assertThat(path).isEmpty();
    }
}
