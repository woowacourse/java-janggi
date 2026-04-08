package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.constant.Country;
import domain.constant.PieceType;
import java.util.List;

import org.junit.jupiter.api.Test;

import testUtil.BoardTestUtil;

class JanggiGameTest {

    @Test
    void 턴_변경_정상_테스트() {
        List<PieceType> maSang = BoardTestUtil.createMaSangSangMa();
        JanggiGame janggiGame = new JanggiGame(new Board(maSang, maSang));
        Position startPosition = Position.create(4,1);
        Position endPosition = Position.create(5,1);

        janggiGame.play(startPosition, endPosition);

        Country country = janggiGame.getCountry();

        assertThat(country).isEqualTo(Country.HAN);
    }

}