package janggi.setting;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GungSungTest {

    @DisplayName("특정 궁성영역 내의 위치인지 확인할 수 있다.")
    @Test
    void canCheckPositionInGungSung() {
        boolean isInGungSung = GungSung.TOP_GUNGSUNG.isInGungSung(new Position(4, 1));
        assertThat(isInGungSung).isTrue();
    }
}