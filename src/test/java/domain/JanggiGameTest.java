package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 턴_변경_정상_테스트() {
//        JanggiGame janggiGame = new JanggiGame(new Board(MaSangPosition.MA_SANG_MA_SANG, MaSangPosition.MA_SANG_MA_SANG));
        JanggiGame janggiGame = new JanggiGame(new Board());
        janggiGame.play();

        Country country = janggiGame.getCountry();

        assertThat(country).isEqualTo(Country.HAN);
    }

}