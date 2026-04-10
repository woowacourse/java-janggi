package domain.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {
    Position hanPalacePosition;
    Position choPalacePosition;

    @BeforeEach
    void setUp() {
        hanPalacePosition = new Position(4, 8);
        choPalacePosition = new Position(4, 1);
    }

    @Test
    void 해당_좌표가_궁성인지_반환한다() {
        Position outPalacePosition = new Position(0, 0);

        assertThat(Palace.isInPalace(choPalacePosition)).isTrue();
        assertThat(Palace.isInPalace(hanPalacePosition)).isTrue();
        assertThat(Palace.isInPalace(outPalacePosition)).isFalse();
    }

    @Test
    void 해당_좌표가_초의_궁성_내부인지_반환한다() {
        assertThat(Palace.isInChoPalace(choPalacePosition)).isTrue();
        assertThat(Palace.isInChoPalace(hanPalacePosition)).isFalse();
    }

    @Test
    void 해당_좌표가_한의_궁성_내부인지_반환한다() {
        assertThat(Palace.isInHanPalace(hanPalacePosition)).isTrue();
        assertThat(Palace.isInHanPalace(choPalacePosition)).isFalse();
    }

    @Test
    void 해당_좌표가_궁성의_중앙인지_반환한다() {
        Position outPalacePosition = new Position(0, 0);

        assertThat(Palace.isPalaceCenter(hanPalacePosition)).isTrue();
        assertThat(Palace.isPalaceCenter(choPalacePosition)).isTrue();
        assertThat(Palace.isPalaceCenter(outPalacePosition)).isFalse();
    }

    @Test
    void 해당_좌표가_초의_궁성의_중앙인지_반환한다() {

        assertThat(Palace.isChoPalaceCenter(choPalacePosition)).isTrue();
        assertThat(Palace.isChoPalaceCenter(hanPalacePosition)).isFalse();
    }

    @Test
    void 해당_좌표가_한의_궁성의_중앙인지_반환한다() {
        assertThat(Palace.isHanPalaceCenter(hanPalacePosition)).isTrue();
        assertThat(Palace.isHanPalaceCenter(choPalacePosition)).isFalse();
    }

    @Test
    void 출발지와_도착지가_궁성_내에서_이동_가능한_경로인지_반환한다() {
        Position availableDeparture = new Position(3, 0);
        Position availableDestination = new Position(4, 1);

        Position disableDeparture = new Position(4, 0);
        Position disableDestination = new Position(3, 1);

        assertThat(Palace.isPalacePath(availableDeparture, availableDestination)).isTrue();
        assertThat(Palace.isPalacePath(disableDeparture, disableDestination)).isFalse();
    }
}
