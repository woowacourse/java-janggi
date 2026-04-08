package domain.palace;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Position;
import domain.piece.TeamColor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Nested
    class 생성 {
        @Test
        void 팀에_맞는_궁성을_생성한다() {
            final Palace choPalace = Palace.of(TeamColor.CHO);
            final Palace hanPalace = Palace.of(TeamColor.HAN);

            assertThat(choPalace.contains(Position.of(8, 4))).isTrue();
            assertThat(choPalace.contains(Position.of(1, 4))).isFalse();

            assertThat(hanPalace.contains(Position.of(1, 4))).isTrue();
            assertThat(hanPalace.contains(Position.of(8, 4))).isFalse();
        }
    }

    @Nested
    class 궁성연결 {
        @Test
        void 궁성_중앙에서는_상하좌우와_대각선_방향으로_연결된다() {
            final Palace choPalace = Palace.of(TeamColor.CHO);

            assertThat(choPalace.connectedPositions(Position.of(8, 4)))
                    .containsExactlyInAnyOrder(
                            Position.of(7, 3),
                            Position.of(7, 4),
                            Position.of(7, 5),
                            Position.of(8, 3),
                            Position.of(8, 5),
                            Position.of(9, 3),
                            Position.of(9, 4),
                            Position.of(9, 5)
                    );
        }

        @Test
        void 궁성_꼭지점에서는_변_방향과_중앙으로만_연결된다() {
            final Palace hanPalace = Palace.of(TeamColor.HAN);

            assertThat(hanPalace.connectedPositions(Position.of(0, 3)))
                    .containsExactlyInAnyOrder(
                            Position.of(0, 4),
                            Position.of(1, 3),
                            Position.of(1, 4)
                    );
        }

        @Test
        void 궁성_변의_중간점에서는_직선_방향으로만_연결된다() {
            final Palace choPalace = Palace.of(TeamColor.CHO);

            assertThat(choPalace.connectedPositions(Position.of(9, 4)))
                    .containsExactlyInAnyOrder(
                            Position.of(9, 3),
                            Position.of(9, 5),
                            Position.of(8, 4)
                    );
        }

        @Test
        void 궁성_밖의_좌표에서는_연결된_좌표가_없다() {
            final Palace hanPalace = Palace.of(TeamColor.HAN);

            assertThat(hanPalace.connectedPositions(Position.of(4, 4))).isEmpty();
        }
    }
}
