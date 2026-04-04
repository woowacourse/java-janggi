package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceTest {
    private final Palace palace = new Palace();

    @Test
    @DisplayName("궁성 내 대각선으로 양 끝에서 이동하는 경우 중앙 경로를 반환한다.")
    void findDiagonalPath_When_OppositeCorners() {
        assertAll(
                () -> assertThat(palace.findDiagonalPath(new Position(4, 1), new Position(6, 3)))
                        .contains(List.of(new Position(5, 2))),
                () -> assertThat(palace.findDiagonalPath(new Position(6, 1), new Position(4, 3)))
                        .contains(List.of(new Position(5, 2))),
                () -> assertThat(palace.findDiagonalPath(new Position(4, 8), new Position(6, 10)))
                        .contains(List.of(new Position(5, 9))),
                () -> assertThat(palace.findDiagonalPath(new Position(6, 8), new Position(4, 10)))
                        .contains(List.of(new Position(5, 9)))
        );
    }

    @Test
    @DisplayName("궁성 꼭짓점과 중앙 사이 이동이면 빈 경로를 반환한다.")
    void findDiagonalPath_When_MoveCornerAndCenter() {
        assertAll(
                () -> assertThat(palace.findDiagonalPath(new Position(4, 1), new Position(5, 2)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(5, 2), new Position(6, 3)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(6, 1), new Position(5, 2)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(5, 2), new Position(4, 3)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(4, 8), new Position(5, 9)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(5, 9), new Position(6, 10)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(6, 8), new Position(5, 9)))
                        .contains(List.of()),
                () -> assertThat(palace.findDiagonalPath(new Position(5, 9), new Position(4, 10)))
                        .contains(List.of())
        );
    }

    @Test
    @DisplayName("궁성 안의 대각선 경로가 아니면 빈 값을 반환한다.")
    void findDiagonalPath_When_NotPalaceDiagonal() {
        assertAll(
                () -> assertThat(palace.findDiagonalPath(new Position(4, 1), new Position(5, 3))).isEmpty(),
                () -> assertThat(palace.findDiagonalPath(new Position(4, 1), new Position(4, 3))).isEmpty(),
                () -> assertThat(palace.findDiagonalPath(new Position(1, 1), new Position(2, 2))).isEmpty(),
                () -> assertThat(palace.findDiagonalPath(new Position(4, 1), new Position(6, 10))).isEmpty()
        );
    }

    @Test
    @DisplayName("한나라 궁성 내부 좌표면 true를 반환한다.")
    void contains_When_HanPalacePosition() {
        assertThat(palace.contains(new Position(5, 2))).isTrue();
        assertThat(palace.contains(new Position(3, 2))).isFalse();
    }

    @Test
    @DisplayName("초나라 궁성 내부 좌표면 true를 반환한다.")
    void contains_When_ChoPalacePosition() {
        assertThat(palace.contains(new Position(5, 9))).isTrue();
        assertThat(palace.contains(new Position(5, 7))).isFalse();
    }
}
