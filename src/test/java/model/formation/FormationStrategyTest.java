package model.formation;


import model.Team;
import model.coordinate.Position;
import model.piece.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

import static model.coordinate.Position.CHO_LEFT_INNER;
import static model.coordinate.Position.CHO_LEFT_OUTER;
import static model.coordinate.Position.CHO_RIGHT_INNER;
import static model.coordinate.Position.CHO_RIGHT_OUTER;
import static model.coordinate.Position.HAN_LEFT_INNER;
import static model.coordinate.Position.HAN_LEFT_OUTER;
import static model.coordinate.Position.HAN_RIGHT_INNER;
import static model.coordinate.Position.HAN_RIGHT_OUTER;
import static org.assertj.core.api.Assertions.assertThat;

class FormationStrategyTest {

    @ParameterizedTest
    @MethodSource("model.fixture.FormationTestFixture#한나라_상차림_전략")
    void HAN팀_상차림_전략별_기물_배치가_올바르다(
            FormationStrategy strategy,
            Class<Piece> leftOuter,
            Class<Piece> leftInner,
            Class<Piece> rightInner,
            Class<Piece> rightOuter
    ) {
        // when
        Map<Position, Piece> formation = strategy.generateFormation(Team.HAN);

        // then
        assertThat(formation.get(HAN_LEFT_OUTER)).isInstanceOf(leftOuter);
        assertThat(formation.get(HAN_LEFT_INNER)).isInstanceOf(leftInner);
        assertThat(formation.get(HAN_RIGHT_INNER)).isInstanceOf(rightInner);
        assertThat(formation.get(HAN_RIGHT_OUTER)).isInstanceOf(rightOuter);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.FormationTestFixture#초나라_상차림_전략")
    void CHO팀_상차림_전략별_기물_배치가_올바르다(
            FormationStrategy strategy,
            Class<Piece> leftOuter,
            Class<Piece> leftInner,
            Class<Piece> rightInner,
            Class<Piece> rightOuter
    ) {
        // when
        Map<Position, Piece> formation = strategy.generateFormation(Team.CHO);

        // then
        assertThat(formation.get(CHO_LEFT_OUTER)).isInstanceOf(leftOuter);
        assertThat(formation.get(CHO_LEFT_INNER)).isInstanceOf(leftInner);
        assertThat(formation.get(CHO_RIGHT_INNER)).isInstanceOf(rightInner);
        assertThat(formation.get(CHO_RIGHT_OUTER)).isInstanceOf(rightOuter);
    }

    @ParameterizedTest
    @MethodSource("model.fixture.FormationTestFixture#나라별_상차림_전략")
    void 상차림_기물_수는_4개다(FormationStrategy strategy, Team team) {
        // when
        Map<Position, Piece> pieceByFormation = strategy.generateFormation(team);

        // then
        assertThat(pieceByFormation).hasSize(4);
    }
}