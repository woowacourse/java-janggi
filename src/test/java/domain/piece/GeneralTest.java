package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Nested
    class 초기_위치를_반환한다 {

        @Test
        void 한_소속_궁이_적절한_초기_위치를_반환한다() {
            // given
            General general = new General(Side.HAN);
            List<Intersection> expected = List.of(new Intersection(2, 5));

            // when
            List<Intersection> actual = general.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 초_소속_궁이_적절한_초기_위치를_반환한다() {
            // given
            General general = new General(Side.CHO);
            List<Intersection> expected = List.of(new Intersection(9, 5));

            // when
            List<Intersection> actual = general.initAt();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    void 핵심_기물로_취급된다() {
        // given
        General general = new General(Side.CHO);

        // when
        boolean royalPiece = general.isRoyalPiece();

        // then
        assertThat(royalPiece).isTrue();
    }

    @Test
    void 본인의_점수를_반환한다() {
        // given
        General general = new General(Side.HAN);
        double expected = 0;

        // when
        double actual = general.getScore();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
