package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.setup.SetUpEntity;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = Game.createGame(
                SetUpEntity.IN_ELEPHANT.getBoardSetUp(),
                SetUpEntity.IN_ELEPHANT.getBoardSetUp(),
                Status.IN_PROGRESS
        );
    }

    @Nested
    class Move {
        @Test
        @DisplayName("제자리 이동을 하면 턴을 넘긴다.")
        void move() {
            Point choPoint = new Point(0, 0);

            game.move(choPoint, choPoint);

            assertThat(game.isTurnPiece(choPoint)).isFalse();
        }

        @Test
        @DisplayName("이동에 성공하면 턴을 바꾼다.")
        void switchTurnWhenMove() {
            // given
            Point point = new Point(3, 0);

            // when
            game.move(point, point);

            // then
            assertThat(game.getTurn()).isEqualTo(Side.HAN);
        }

        @Test
        @DisplayName("이동할 수 없는 위치라면 예외를 던진다.")
        void throwExceptionWhenNotDestination() {
            // given
            Point from = new Point(3, 0);
            Point invalidTo = new Point(5, 5);

            // when & then
            assertThatThrownBy(() -> game.move(from, invalidTo))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class IsTurnPiece {
        @Test
        @DisplayName("CHO 턴일때 같은 진영 기물인지 확인한다.")
        void isChoPiece() {
            Point choPoint = new Point(3, 0);
            Point hanPoint = new Point(6, 0);

            assertThat(game.isTurnPiece(choPoint)).isTrue();
            assertThat(game.isTurnPiece(hanPoint)).isFalse();
        }

        @Test
        @DisplayName("CHO 턴일때 같은 진영 기물인지 확인한다.")
        void isHanPiece() {
            Point choPoint = new Point(3, 0);
            Point hanPoint = new Point(6, 0);

            game.switchTurn();

            assertThat(game.isTurnPiece(choPoint)).isFalse();
            assertThat(game.isTurnPiece(hanPoint)).isTrue();
        }
    }
}
