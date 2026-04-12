package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import domain.enums.Country;
import domain.enums.PieceType;
import testUtil.BoardTestUtil;

class JanggiGameTest {

    @Test
    void 턴_변경_정상_테스트() {
        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame janggiGame = new JanggiGame(new Board(maSang));
        Position startPosition = Position.create(4,1);
        Position endPosition = Position.create(5,1);

        janggiGame.play(startPosition, endPosition);

        Country country = janggiGame.getCountry();

        assertThat(country).isEqualTo(Country.HAN);
    }

    @Test
    void 왕_죽음_게임_종료_정상_테스트() {
        List<PieceType> maSang = BoardTestUtil.createMasangSangMa();
        JanggiGame janggiGame = new JanggiGame(new Board(maSang));

        janggiGame.play(Position.create(4,5), Position.create(4,4));
        janggiGame.play(Position.create(10,2), Position.create(8,3));
        janggiGame.play(Position.create(4,3), Position.create(5,3));
        janggiGame.play(Position.create(8,2), Position.create(8,5));
        janggiGame.play(Position.create(5,3), Position.create(6,3));
        janggiGame.play(Position.create(8,5), Position.create(2,5));

        assertThat(janggiGame.isGameOver()).isEqualTo(true);
    }
}