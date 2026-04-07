package janggi.model.gimul;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GimulTypeTest {

    @DisplayName("CHA 타입으로 Cha 기물을 생성한다.")
    @Test
    void create_cha() {
        assertThat(GimulType.CHA.create(Team.CHO).getSymbol()).isEqualTo("차");
    }

    @DisplayName("PHO 타입으로 Pho 기물을 생성한다.")
    @Test
    void create_pho() {
        assertThat(GimulType.PHO.create(Team.CHO).getSymbol()).isEqualTo("포");
    }

    @DisplayName("MA 타입으로 Ma 기물을 생성한다.")
    @Test
    void create_ma() {
        assertThat(GimulType.MA.create(Team.CHO).getSymbol()).isEqualTo("마");
    }

    @DisplayName("SANG 타입으로 Sang 기물을 생성한다.")
    @Test
    void create_sang() {
        assertThat(GimulType.SANG.create(Team.CHO).getSymbol()).isEqualTo("상");
    }

    @DisplayName("SA 타입으로 Sa 기물을 생성한다.")
    @Test
    void create_sa() {
        assertThat(GimulType.SA.create(Team.CHO).getSymbol()).isEqualTo("사");
    }

    @DisplayName("BYEONG 타입으로 Byeong 기물을 생성한다.")
    @Test
    void create_byeong() {
        assertThat(GimulType.BYEONG.create(Team.CHO).getSymbol()).isEqualTo("병");
    }

    @DisplayName("JANG 타입으로 Jang 기물을 생성한다.")
    @Test
    void create_jang() {
        assertThat(GimulType.JANG.create(Team.CHO).getSymbol()).isEqualTo("장");
    }
}
