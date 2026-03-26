package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import testUtil.BoardTestUtil;

class JanggiGameTest {

    @Test
    void 턴_변경_정상_테스트() {
        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame janggiGame = new JanggiGame(new Board(maSang));

        janggiGame.play();

        Country country = janggiGame.getCountry();

        assertThat(country).isEqualTo(Country.HAN);
    }

}