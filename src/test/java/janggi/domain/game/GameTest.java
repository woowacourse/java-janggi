package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.setup.InElephantSetUp;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = Game.createGame(
                InElephantSetUp.INSTANCE,
                InElephantSetUp.INSTANCE
        );
    }

    @Nested
    class Move {
        @Test
        @DisplayName("제자리 이동을 하면 턴을 넘긴다.")
        void move() {
            Point choPoint = new Point(0, 0);
            BoardSetUp boardSetUp = InElephantSetUp.INSTANCE;
            Game game = Game.createGame(boardSetUp, boardSetUp);

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
            BoardSetUp choBoardSetUp = side -> Map.of(choPoint, new Soldier(side));
            BoardSetUp handBoardSetUp = side -> Map.of(hanPoint, new Soldier(side));
            Game game = Game.createGame(choBoardSetUp, handBoardSetUp);

            assertThat(game.isTurnPiece(choPoint)).isTrue();
            assertThat(game.isTurnPiece(hanPoint)).isFalse();
        }

        @Test
        @DisplayName("CHO 턴일때 같은 진영 기물인지 확인한다.")
        void isHanPiece() {
            Point choPoint = new Point(3, 0);
            Point hanPoint = new Point(6, 0);
            BoardSetUp choBoardSetUp = side -> Map.of(choPoint, new Soldier(side));
            BoardSetUp handBoardSetUp = side -> Map.of(hanPoint, new Soldier(side));
            Game game = Game.createGame(choBoardSetUp, handBoardSetUp);

            game.switchTurn();

            assertThat(game.isTurnPiece(choPoint)).isFalse();
            assertThat(game.isTurnPiece(hanPoint)).isTrue();
        }
    }
}
