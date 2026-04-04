package janggi.model.gimul;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Team;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.diagonalMove.Sang;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.linearMove.Pho;
import janggi.model.gimul.palace.Jang;
import janggi.model.gimul.palace.Sa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GimulTypeTest {

    @DisplayName("Cha 기물로부터 CHA 타입을 반환한다.")
    @Test
    void from_cha() {
        assertThat(GimulType.from(new Cha(Team.CHO))).isEqualTo(GimulType.CHA);
    }

    @DisplayName("Pho 기물로부터 PHO 타입을 반환한다.")
    @Test
    void from_pho() {
        assertThat(GimulType.from(new Pho(Team.CHO))).isEqualTo(GimulType.PHO);
    }

    @DisplayName("Ma 기물로부터 MA 타입을 반환한다.")
    @Test
    void from_ma() {
        assertThat(GimulType.from(new Ma(Team.CHO))).isEqualTo(GimulType.MA);
    }

    @DisplayName("Sang 기물로부터 SANG 타입을 반환한다.")
    @Test
    void from_sang() {
        assertThat(GimulType.from(new Sang(Team.CHO))).isEqualTo(GimulType.SANG);
    }

    @DisplayName("Sa 기물로부터 SA 타입을 반환한다.")
    @Test
    void from_sa() {
        assertThat(GimulType.from(new Sa(Team.CHO))).isEqualTo(GimulType.SA);
    }

    @DisplayName("Byeong 기물로부터 BYEONG 타입을 반환한다.")
    @Test
    void from_byeong() {
        assertThat(GimulType.from(new Byeong(Team.CHO))).isEqualTo(GimulType.BYEONG);
    }

    @DisplayName("Jang 기물로부터 JANG 타입을 반환한다.")
    @Test
    void from_jang() {
        assertThat(GimulType.from(new Jang(Team.CHO))).isEqualTo(GimulType.JANG);
    }

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
