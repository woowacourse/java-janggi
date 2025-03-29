package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Country;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PiecesTest {

    @Nested
    @DisplayName("계산")
    class Calculate {

        @DisplayName("주어진 나라에 해당하는 총 점수를 계산하여 반환한다.")
        @ParameterizedTest
        @MethodSource
        void calculateAllScoreByCountry(final Piece piece, final JanggiScore expected, final Country country) {
            // given
            final Pieces pieces = new Pieces(List.of(piece));

            // when
            final JanggiScore actual = pieces.calculateAllScoreByCountry(country);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> calculateAllScoreByCountry() {
            final double MAX_SCORE_OF_HAN = 73.5;
            final double MAX_SCORE_OF_CHO = 72;
            final Country HAN = Country.HAN;
            final Country CHO = Country.CHO;

            return Stream.of(
                    Arguments.of(new Cannon(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 7), CHO),
                    Arguments.of(new Chariot(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 13), CHO),
                    Arguments.of(new Elephant(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 3), CHO),
                    Arguments.of(new General(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 0), CHO),
                    Arguments.of(new Guard(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 3), CHO),
                    Arguments.of(new Horse(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 5), CHO),
                    Arguments.of(new Soldier(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 2), CHO),

                    Arguments.of(new Cannon(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 7), HAN),
                    Arguments.of(new Chariot(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 13), HAN),
                    Arguments.of(new Elephant(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 3), HAN),
                    Arguments.of(new General(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 0), HAN),
                    Arguments.of(new Guard(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 3), HAN),
                    Arguments.of(new Horse(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 5), HAN),
                    Arguments.of(new Soldier(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 2), HAN)
            );
        }
    }
}
