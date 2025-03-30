package janggi.palace;

import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.team.TeamName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceFactoryTest {
    @DisplayName("정상: 궁성 생성 확인 (초)")
    @Test
    void createPalace_Cho() {
        assertThatCode(() -> PalaceFactory.createPalace(TeamName.CHO)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 궁성 생성 확인 (한)")
    @Test
    void createPalace_Han() {
        assertThatCode(() -> PalaceFactory.createPalace(TeamName.HAN)).doesNotThrowAnyException();
    }
}
