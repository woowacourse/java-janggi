package domain.path;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleStepLinearPathGeneratorTest {
    SingleStepLinearPathGenerator singleStepLinearPathGenerator;

    @BeforeEach
    void setUp() {
        singleStepLinearPathGenerator = new SingleStepLinearPathGenerator();
    }

    @Test
    void 직선으로_한_칸_이동하는_방향을_반환한다() {
        Position departure = new Position(4, 4);
        Position destination = new Position(5, 4);

        Direction direction = singleStepLinearPathGenerator.decideSingleLinearDirection(departure, destination, false);

        assertThat(direction).isEqualTo(Direction.RIGHT);
    }

    @Test
    void 궁성_내에서는_대각선_한_칸_이동_방향을_반환한다() {
        Position departure = new Position(4, 1);
        Position destination = new Position(5, 2);

        Direction direction = singleStepLinearPathGenerator.decideSingleLinearDirection(departure, destination, true);

        assertThat(direction).isEqualTo(Direction.NORTHEAST);
    }

    @Test
    void 직선이_아니고_궁성_경로도_아니면_예외가_발생한다() {
        Position departure = new Position(4, 4);
        Position destination = new Position(5, 5);

        assertThatThrownBy(() -> singleStepLinearPathGenerator.decideSingleLinearDirection(departure, destination, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 두_칸_이상_이동하면_예외가_발생한다() {
        Position departure = new Position(4, 4);
        Position destination = new Position(4, 6);

        assertThatThrownBy(() -> singleStepLinearPathGenerator.decideSingleLinearDirection(departure, destination, false))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
