package janggi.domain.board.strategy;

import janggi.domain.Camp;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FormationStrategyTest {

    @Nested
    class 상마상마_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 상마상마를_선택하면_올바른_기물_순서를_갖는다(Camp camp) {
            FormationStrategy strategy = new ElephantHorseElephantHorse();
            List<Piece> pieces = strategy.createPieces(camp);

            assertThat(pieces.get(0)).isInstanceOf(Elephant.class);
            assertThat(pieces.get(1)).isInstanceOf(Horse.class);
            assertThat(pieces.get(2)).isInstanceOf(Elephant.class);
            assertThat(pieces.get(3)).isInstanceOf(Horse.class);
        }
    }

    @Nested
    class 상마마상_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 상마마상을_선택하면_올바른_기물_순서를_갖는다(Camp camp) {
            FormationStrategy strategy = new ElephantHorseHorseElephant();
            List<Piece> pieces = strategy.createPieces(camp);

            assertThat(pieces.get(0)).isInstanceOf(Elephant.class);
            assertThat(pieces.get(1)).isInstanceOf(Horse.class);
            assertThat(pieces.get(2)).isInstanceOf(Horse.class);
            assertThat(pieces.get(3)).isInstanceOf(Elephant.class);
        }
    }

    @Nested
    class 마상상마_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 마상상마를_선택하면_올바른_기물_순서를_갖는다(Camp camp) {
            FormationStrategy strategy = new HorseElephantElephantHorse();
            List<Piece> pieces = strategy.createPieces(camp);

            assertThat(pieces.get(0)).isInstanceOf(Horse.class);
            assertThat(pieces.get(1)).isInstanceOf(Elephant.class);
            assertThat(pieces.get(2)).isInstanceOf(Elephant.class);
            assertThat(pieces.get(3)).isInstanceOf(Horse.class);
        }
    }

    @Nested
    class 마상마상_전략 {
        @ParameterizedTest
        @EnumSource(Camp.class)
        void 마상마상을_선택하면_올바른_기물_순서를_갖는다(Camp camp) {
            FormationStrategy strategy = new HorseElephantHorseElephant();
            List<Piece> pieces = strategy.createPieces(camp);

            assertThat(pieces.get(0)).isInstanceOf(Horse.class);
            assertThat(pieces.get(1)).isInstanceOf(Elephant.class);
            assertThat(pieces.get(2)).isInstanceOf(Horse.class);
            assertThat(pieces.get(3)).isInstanceOf(Elephant.class);
        }
    }
}
