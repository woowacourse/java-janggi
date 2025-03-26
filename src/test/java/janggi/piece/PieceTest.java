package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PieceTest {

    @Nested
    @DisplayName("캐넌인지 검증하는 로직")
    class IsCannon {

        @DisplayName("Cannon이라면, true를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void isCannon(final Piece piece, final boolean expected) {
            // given & when
            final boolean actual = piece.isCannon();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> isCannon(){
            return Stream.of(
                    Arguments.of(new Cannon(Country.CHO), true),
                    Arguments.of(new Chariot(Country.CHO), false),
                    Arguments.of(new Elephant(Country.CHO), false),
                    Arguments.of(new General(Country.CHO), false),
                    Arguments.of(new Guard(Country.CHO), false),
                    Arguments.of(new Horse(Country.CHO), false),
                    Arguments.of(new Soldier(Country.CHO), false)
            );
        }

        @DisplayName("General이라면, true를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void isGeneral(final Piece piece, final boolean expected) {
            // given & when
            final boolean actual = piece.isGeneral();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> isGeneral(){
            return Stream.of(
                    Arguments.of(new Cannon(Country.CHO), false),
                    Arguments.of(new Chariot(Country.CHO), false),
                    Arguments.of(new Elephant(Country.CHO), false),
                    Arguments.of(new General(Country.CHO), true),
                    Arguments.of(new Guard(Country.CHO), false),
                    Arguments.of(new Horse(Country.CHO), false),
                    Arguments.of(new Soldier(Country.CHO), false)
            );
        }
    }
}
