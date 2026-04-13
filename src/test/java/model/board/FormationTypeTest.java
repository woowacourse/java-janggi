package model.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Stream;
import model.Team;
import model.coordinate.Position;
import model.piece.Elephant;
import model.piece.Horse;
import model.piece.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class FormationTypeTest {

    static Stream<Arguments> formationTestProvider() {
        return Stream.of(
                Arguments.of(FormationType.SANG_MA_SANG_MA, Elephant.class, Horse.class, Elephant.class, Horse.class),
                Arguments.of(FormationType.MA_SANG_MA_SANG, Horse.class, Elephant.class, Horse.class, Elephant.class),
                Arguments.of(FormationType.MA_SANG_SANG_MA, Horse.class, Elephant.class, Elephant.class, Horse.class),
                Arguments.of(FormationType.SANG_MA_MA_SANG, Elephant.class, Horse.class, Horse.class, Elephant.class)
        );
    }

    static Stream<Arguments> 포메이션_별_기물_위치() {
        return Stream.of(
                // formation, 좌외(1), 좌내(2), 우내(6), 우외(7) 순서
                Arguments.of(FormationType.SANG_MA_SANG_MA, Elephant.class, Horse.class, Elephant.class, Horse.class),
                Arguments.of(FormationType.MA_SANG_MA_SANG, Horse.class, Elephant.class, Horse.class, Elephant.class),
                Arguments.of(FormationType.MA_SANG_SANG_MA, Horse.class, Elephant.class, Elephant.class, Horse.class),
                Arguments.of(FormationType.SANG_MA_MA_SANG, Elephant.class, Horse.class, Horse.class, Elephant.class)
        );
    }

    static Stream<Arguments> choFormationProvider() {
        return 포메이션_별_기물_위치();
    }

    @ParameterizedTest(name = "{0} 차림 일 때")
    @MethodSource("포메이션_별_기물_위치")
    void 한나라_상차림_기물_순서_테스트(
            FormationType formation,
            Class<? extends Piece> leftOuter,
            Class<? extends Piece> leftInner,
            Class<? extends Piece> rightInner,
            Class<? extends Piece> rightOuter
    ) {
        Map<Position, Piece> result = formation.generateByTeam(Team.HAN);

        assertThat(result.get(new Position(0, 1))).isInstanceOf(leftOuter);
        assertThat(result.get(new Position(0, 2))).isInstanceOf(leftInner);
        assertThat(result.get(new Position(0, 6))).isInstanceOf(rightInner);
        assertThat(result.get(new Position(0, 7))).isInstanceOf(rightOuter);
    }

    @ParameterizedTest(name = "{0} 차림일 때")
    @MethodSource("choFormationProvider")
    void 초나라_상차림_기물_순서_테스트(
            FormationType formation,
            Class<? extends Piece> leftOuter,
            Class<? extends Piece> leftInner,
            Class<? extends Piece> rightInner,
            Class<? extends Piece> rightOuter
    ) {
        Map<Position, Piece> result = formation.generateByTeam(Team.CHO);

        assertThat(result.get(new Position(9, 1))).isInstanceOf(leftOuter);
        assertThat(result.get(new Position(9, 2))).isInstanceOf(leftInner);
        assertThat(result.get(new Position(9, 6))).isInstanceOf(rightInner);
        assertThat(result.get(new Position(9, 7))).isInstanceOf(rightOuter);
    }

    @ParameterizedTest(name = "{1}나라 일 때 {0}포메이션에서 기물 수는 총 4개다.")
    @CsvSource({
            "SANG_MA_SANG_MA, HAN", "MA_SANG_MA_SANG, HAN", "MA_SANG_SANG_MA, HAN", "SANG_MA_MA_SANG, HAN",
            "SANG_MA_SANG_MA, CHO", "MA_SANG_MA_SANG, CHO", "MA_SANG_SANG_MA, CHO", "SANG_MA_MA_SANG, CHO"
    })
    void 각_팀별_상차림의_기물_수는_4개여야_한다(FormationType formation, Team team) {
        // when
        assertThat(formation.generateByTeam(team)).hasSize(4);
    }
}
