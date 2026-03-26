package janggi.domain.movement;

import static janggi.domain.Position.MAXIMUM_ROW;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StraightMovementTest {

    @Nested
    @DisplayName("도달 여부 판정 테스트")
    class CanReach {

        Board board;
        BoardMediator boardMediator;
        @BeforeEach
        void setUp() {
            board = new Board(Map.of(Position.valueOf(5, 6), new Soldier(TeamType.BLUE)));
            boardMediator = new BoardMediatorImpl(board);
        }

        @Test
        @DisplayName("도달 가능한 경우")
        void success_1() {
            Position from = Position.valueOf(5, 3);
            int maxDistance = 1;
            Direction direction = Direction.valueOf(0, 1);
            Movement straightMovement = new StraightMovement(maxDistance, direction, boardMediator);
            boolean expected = true;

            boolean actual = straightMovement.canReach(from);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("도달 불가능한 경우")
        void success_2() {
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement straightMovement = new StraightMovement(maxDistance, direction, boardMediator);
            boolean expected = false;

            boolean actual = straightMovement.canReach(from);

            assertThat(actual).isEqualTo(expected);
        }
    }

}