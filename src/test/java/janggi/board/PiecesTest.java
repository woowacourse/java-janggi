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

        @DisplayName("주어진 나라에 해당하는 총 점수를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void calculateAllScoreByCountry(final Piece piece, final JanggiScore expectedScore) {
            // given
            final Pieces pieces = new Pieces(List.of(piece));

            // when
            final JanggiScore actual = pieces.calculateAllScoreByCountry(Country.CHO);

            // then
            assertThat(actual).isEqualTo(expectedScore);
        }

        static Stream<Arguments> calculateAllScoreByCountry() {
            final double MAX_SCORE_OF_CHO = 72;
            return Stream.of(
                    Arguments.of(new Cannon(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 7)),
                    Arguments.of(new Chariot(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 13)),
                    Arguments.of(new Elephant(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 3)),
                    Arguments.of(new General(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 0)),
                    Arguments.of(new Guard(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 3)),
                    Arguments.of(new Horse(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 5)),
                    Arguments.of(new Soldier(Country.HAN), new JanggiScore(MAX_SCORE_OF_CHO - 2))
            );
        }
    }
}
