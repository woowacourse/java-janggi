package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    @DisplayName("차를 생성하면 차가 초기화된다.")
    void 차_생성() {
        // given
        // when
        Chariot chariot = Chariot.of(Team.CHU);

        // then
        Assertions.assertEquals(chariot.findMyTeam(), Team.CHU);
    }

    @Test
    @DisplayName("상하좌우로 이동 경로에 다른 기물이 없으면 이동할 수 있다.")
    void 차_상하좌우_정상_이동() {
        // given
        Piece chariot = Chariot.of(Team.CHU);

        // when
        chariot.move();

        // then
    }
}
