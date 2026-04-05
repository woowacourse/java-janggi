package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PalaceTest {
    @ParameterizedTest
    @CsvSource({
            "1, 4, true",
            "2, 5, true",
            "3, 6, true",
            "9, 5, true",
            "5, 5, false",
            "1, 3, false"
    })
    void 궁성_안에_위치하는지_참거짓을_반환한다(int x, int y, boolean isPalace) {
        Position position = new Position(x, y);
        assertThat(Palace.isInside(position)).isEqualTo(isPalace);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 4, 3, 6",
        "2, 5, 1, 6",
        "1, 6, 3, 4"
    })
    void 궁성에서_대각선_이동에_대한_참을_반환한다(int x1, int y1, int x2, int y2) {
        Position start = new Position(x1, y1);
        Position end = new Position(x2, y2);
        assertThat(Palace.isDiagonalMove(start, end)).isTrue();
    }
    @ParameterizedTest
    @CsvSource({
            "1, 5, 2, 5",
            "2, 4, 2, 5",
            "2, 6, 2, 5",
            "3, 5, 2, 5"
    })
    void 궁성에서_십자_위치가_포함되면_대각선_이동에_대한_거짓을_반환한다(int x1, int y1, int x2, int y2) {
        Position start = new Position(x1, y1);
        Position end = new Position(x2, y2);
        assertThat(Palace.isDiagonalMove(start, end)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "4, 4, 2, 5",
            "2, 5, 4, 5",
            "1, 4, 4, 7",
    })
    void 둘_중_궁성_밖인_위치가_존재하면_대각선_이동에_대한_거짓을_반환한다(int x1, int y1, int x2, int y2) {
        Position start = new Position(x1, y1);
        Position end = new Position(x2, y2);
        assertThat(Palace.isDiagonalMove(start, end)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4, 2, 4",
            "1, 6, 3, 6",
            "1, 5, 2, 5"
    })
    void 직선_이동의_경우_대각선_이동에_대한_거짓을_반환한다(int x1, int y1, int x2, int y2) {
        Position start = new Position(x1, y1);
        Position end = new Position(x2, y2);
        assertThat(Palace.isDiagonalMove(start, end)).isFalse();
    }
}
