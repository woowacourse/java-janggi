package model.formation;

import model.coordinate.Position;
import model.piece.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FormationFactoryTest {

    static Stream<Arguments> validFormationCombinations() {
        return Stream.of(JanggiFormation.values())
                .flatMap(han -> Stream.of(JanggiFormation.values())
                        .map(cho -> Arguments.of(han, cho)));
    }

    @ParameterizedTest
    @MethodSource("validFormationCombinations")
    void 두_팀의_상차림을_합쳐서_8개_기물을_생성한다(JanggiFormation han, JanggiFormation cho) {
        // when
        Map<Position, Piece> result = FormationFactory.generateFormation(han, cho);

        // then
        assertThat(result).hasSize(8);
    }
}