package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FormationStrategyTest {

    @Nested
    class 상마상마_전략 {

        @ParameterizedTest
        @EnumSource(Camp.class)
        void 상마상마를_선택하면_정상_포지션을_갖는다(Camp camp) {
            FormationStrategy formationStrategy = new ElephantHorseElephantHorse();

            Map<JanggiPosition, Piece> pieces = formationStrategy.createPieces(camp);

            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 1))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 2))).isInstanceOf(Horse.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 6))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 7))).isInstanceOf(Horse.class);
        }
    }


    @Nested
    class 상마마상_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 상마마상을_선택하면_정상_포지션을_갖는다(Camp camp) {
            FormationStrategy formationStrategy = new ElephantHorseHorseElephant();

            Map<JanggiPosition, Piece> pieces = formationStrategy.createPieces(camp);

            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 1))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 2))).isInstanceOf(Horse.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 7))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 6))).isInstanceOf(Horse.class);
        }
    }

    @Nested
    class 마상상마_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 마상상마를_선택하면_정상_포지션을_갖는다(Camp camp) {
            FormationStrategy formationStrategy = new HorseElephantElephantHorse();

            Map<JanggiPosition, Piece> pieces = formationStrategy.createPieces(camp);

            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 2))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 1))).isInstanceOf(Horse.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 6))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 7))).isInstanceOf(Horse.class);
        }
    }

    @Nested
    class 마상마상_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 마상마상을_선택하면_정상_포지션을_갖는다(Camp camp) {
            FormationStrategy formationStrategy = new HorseElephantHorseElephant();

            Map<JanggiPosition, Piece> pieces = formationStrategy.createPieces(camp);

            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 2))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 1))).isInstanceOf(Horse.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 7))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(JanggiPosition.of(camp.baselineRow(), 6))).isInstanceOf(Horse.class);
        }
    }
}
