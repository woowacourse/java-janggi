package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("궁성 로직 테스트")
class PalaceTest {

    @DisplayName("궁성 내부인지 검증")
    @Nested
    class 궁성_내부인지_검증 {

        @DisplayName("좌표가 한(HAN) 진영의 궁성 범위 안이면 true를 리턴한다")
        @ParameterizedTest
        @CsvSource({
                "1, 4", "2, 5", "3, 6", // 한 궁성 내부
                "1, 5", "2, 4", "2, 6"  // 한 궁성 십자 및 옆
        })
        void 한_궁성_안이면_true를_리턴한다(int row, int file) {
            Intersection pos = new Intersection(row, file);

            assertThat(Palace.contains(pos, Side.HAN)).isTrue();
        }

        @DisplayName("좌표가 초(CHO) 진영의 궁성 범위 안이면 true를 리턴한다")
        @ParameterizedTest
        @CsvSource({
                "8, 4", "9, 5", "10, 6", // 초 궁성 내부
                "8, 5", "9, 4", "10, 5"  // 초 궁성 십자 및 옆
        })
        void 초_궁성_안이면_true를_리턴한다(int row, int file) {
            Intersection pos = new Intersection(row, file);

            assertThat(Palace.contains(pos, Side.CHO)).isTrue();
        }

        @DisplayName("궁성 범위를 벗어난 좌표는 false를 리턴한다")
        @ParameterizedTest
        @CsvSource({
                "4, 5", // 궁성 바로 아래 (한 기준)
                "7, 5", // 궁성 바로 위 (초 기준)
                "2, 3", // 궁성 왼쪽 바깥
                "2, 7"  // 궁성 오른쪽 바깥
        })
        void 궁성_밖이면_false를_리턴한다(int row, int file) {
            Intersection pos = new Intersection(row, file);

            assertThat(Palace.contains(pos, Side.HAN)).isFalse();
            assertThat(Palace.contains(pos, Side.CHO)).isFalse();
        }
    }

    @DisplayName("궁성의 중앙인지 검증")
    @Nested
    class 궁성_중앙_검증 {

        @DisplayName("한 진영과 초 진영의 중앙 좌표에 대해 true를 리턴한다")
        @Test
        void 중앙이면_true를_리턴한다() {
            assertThat(Palace.isCenter(new Intersection(2, 5))).isTrue();
            assertThat(Palace.isCenter(new Intersection(9, 5))).isTrue();
        }

        @DisplayName("중앙이 아닌 좌표에 대해 false를 리턴한다")
        @Test
        void 중앙이_아니면_false를_리턴한다() {
            assertThat(Palace.isCenter(new Intersection(1, 4))).isFalse();
            assertThat(Palace.isCenter(new Intersection(5, 5))).isFalse();
        }
    }

    @DisplayName("궁성의 코너인지 검증")
    @Nested
    class 궁성_코너_검증 {

        @DisplayName("궁성의 코너 좌표에 대해 true를 리턴한다")
        @ParameterizedTest
        @CsvSource({
                "1, 4", "1, 6", "3, 4", "3, 6", // 한 궁성 코너
                "8, 4", "8, 6", "10, 4", "10, 6" // 초 궁성 코너
        })
        void 궁성_코너면_true를_리턴한다(int row, int file) {
            assertThat(Palace.isCorner(new Intersection(row, file))).isTrue();
        }

        @DisplayName("코너가 아닌 좌표에 대해 false를 리턴한다")
        @ParameterizedTest
        @CsvSource({
                "2, 5", "9, 5", // 중앙
                "2, 4", "2, 6", // 옆
                "1, 5", "3, 5"  // 상하 중앙
        })
        void 궁성_코너가_아니면_false를_리턴한다(int row, int file) {
            assertThat(Palace.isCorner(new Intersection(row, file))).isFalse();
        }
    }

    @DisplayName("궁성 코너에 대한 중앙 계산 검증")
    @Nested
    class 궁성_코너에_대한_중앙_계산_검증 {

        @DisplayName("코너 좌표가 주어지면 해당 진영의 중앙 좌표를 리턴한다")
        @Test
        void 코너에_대한_중앙_좌표를_리턴한다() {
            Intersection hanCorner = new Intersection(1, 4);
            Intersection choCorner = new Intersection(10, 6);

            assertThat(Palace.getCenterOf(hanCorner)).isEqualTo(new Intersection(2, 5));
            assertThat(Palace.getCenterOf(choCorner)).isEqualTo(new Intersection(9, 5));
        }
    }

    @DisplayName("중앙 건너편 코너 계산 검증")
    @Nested
    class 중앙_건너편_코너_계산_검증 {

        @DisplayName("코너 좌표가 주어지면 중앙을 가로질러 마주보는 반대편 코너를 리턴한다")
        @ParameterizedTest
        @CsvSource({
                "1, 4, 3, 6",
                "1, 6, 3, 4",
                "3, 4, 1, 6",
                "3, 6, 1, 4",
                "8, 4, 10, 6",
                "10, 6, 8, 4"
        })
        void 코너의_중앙_너머_코너를_리턴한다(int row, int file, int expectedRow, int expectedFile) {
            Intersection from = new Intersection(row, file);
            Intersection expected = new Intersection(expectedRow, expectedFile);

            assertThat(Palace.getOppositeCornerOf(from)).isEqualTo(expected);
        }

        @DisplayName("코너가 아닌 좌표를 입력하면 예외를 던진다")
        @Test
        void 코너가_아닌_좌표를_입력하면_예외를_던진다() {
            Intersection sidePosistion = new Intersection(2, 4);

            assertThatThrownBy(() -> Palace.getOppositeCornerOf(sidePosistion))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
