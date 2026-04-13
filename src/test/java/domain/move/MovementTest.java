package domain.move;

import static org.assertj.core.api.Assertions.assertThat;

import domain.coordination.Coordination;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    void 직선_이동의_경로를_반환한다() {
        Movement movement = new StraightMovement();
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(1, 7);

        assertThat(movement.canMove(from, to, Team.CHO)).isTrue();
        assertThat(movement.path(from, to)).containsExactly(
                Coordination.of(1, 8),
                Coordination.of(1, 9)
        );
    }

    @Test
    void 궁성_대각선_이동의_경로를_반환한다() {
        Movement movement = new PalaceDiagonalMovement();
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(6, 8);

        assertThat(movement.canMove(from, to, Team.CHO)).isTrue();
        assertThat(movement.path(from, to)).containsExactly(Coordination.of(5, 9));
    }

    @Test
    void 장과_사는_궁성_안에서_한_칸_이동할_수_있다() {
        Movement movement = new PalaceOneStepMovement();

        assertThat(movement.canMove(Coordination.of(5, 9), Coordination.of(6, 8), Team.CHO)).isTrue();
        assertThat(movement.canMove(Coordination.of(5, 9), Coordination.of(5, 7), Team.CHO)).isFalse();
    }

    @Test
    void 졸과_병은_전진과_좌우로_이동할_수_있다() {
        Movement movement = new SoldierBasicMovement();

        assertThat(movement.canMove(Coordination.of(1, 7), Coordination.of(1, 6), Team.CHO)).isTrue();
        assertThat(movement.canMove(Coordination.of(1, 4), Coordination.of(1, 5), Team.HAN)).isTrue();
        assertThat(movement.canMove(Coordination.of(1, 7), Coordination.of(1, 8), Team.CHO)).isFalse();
    }

    @Test
    void 졸과_병은_상대_궁성에서_전진_대각선으로_이동할_수_있다() {
        Movement movement = new EnemyPalaceForwardDiagonalMovement();

        assertThat(movement.canMove(Coordination.of(4, 3), Coordination.of(5, 2), Team.CHO)).isTrue();
        assertThat(movement.canMove(Coordination.of(4, 3), Coordination.of(5, 4), Team.CHO)).isFalse();
    }

    @Test
    void 마의_멱_경로를_반환한다() {
        Movement movement = new HorseMovement();
        Coordination from = Coordination.of(2, 1);
        Coordination to = Coordination.of(3, 3);

        assertThat(movement.canMove(from, to, Team.HAN)).isTrue();
        assertThat(movement.path(from, to)).containsExactly(Coordination.of(2, 2));
    }

    @Test
    void 상의_상다리_경로를_반환한다() {
        Movement movement = new ElephantMovement();
        Coordination from = Coordination.of(3, 1);
        Coordination to = Coordination.of(5, 4);

        assertThat(movement.canMove(from, to, Team.HAN)).isTrue();
        assertThat(movement.path(from, to)).containsExactly(
                Coordination.of(3, 2),
                Coordination.of(4, 3)
        );
    }
}
