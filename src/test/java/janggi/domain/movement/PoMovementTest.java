package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.BoardFixture;
import janggi.domain.Coordinate;
import janggi.domain.PieceType;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.movestep.InfiniteMoveProcess;
import janggi.domain.movestep.MoveStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PoMovementTest {

    private Movement movement;

    @BeforeEach
    void setUp() {
        final var contiguousMovement = new SeveralMovement(
            new InfiniteMoveProcess(MoveStep.UP),
            new InfiniteMoveProcess(MoveStep.DOWN),
            new InfiniteMoveProcess(MoveStep.LEFT),
            new InfiniteMoveProcess(MoveStep.RIGHT)
        );

        movement = new PoMovement(contiguousMovement);
    }

    @Test
    @DisplayName("연속적인 움직임을 결합해 포 움직임을 만들 수 있다.")
    void createMovement() {
        final var contiguousMovement = new SeveralMovement(
            new InfiniteMoveProcess(MoveStep.UP),
            new InfiniteMoveProcess(MoveStep.DOWN),
            new InfiniteMoveProcess(MoveStep.LEFT),
            new InfiniteMoveProcess(MoveStep.RIGHT)
        );

        Movement movement = new PoMovement(contiguousMovement);

        assertThat(movement).isNotNull();
    }

    @Nested
    @DisplayName("장애물을 고려한 움직임 가능 여부 테스트")
    class CanMoveConsideringObstaclesTest {

        @Test
        @DisplayName("포가 이동할 때 포다리가 없을 경우 이동할 수 없다.")
        void test1() {
            // given
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            // when
            final var canMove = movement.canMove(departure, arrival, BoardFixture.emptyBoard());

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포가 이동할 때 뛰어넘을 기물이 하나가 아닐 경우 이동할 수 없다.")
        void test2() {
            // given
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            Board board = new BoardFixture()
                .anyPieceNotPo(6, 5)
                .anyPieceNotPo(7, 5)
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포는 포를 뛰어넘을 수 없다.")
        void test3() {
            // given
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            Board board = new BoardFixture()
//                .addPiece(5, 5, po)
                .addPiece(6, 5, PieceType.PO, Team.CHO) // <- 포다리 [불가능]
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포의 도착지에 포가 있으면 이동할 수 없다.")
        void test4() {
            // given
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            Board board = new BoardFixture()
//                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5) // <- 포다리
                .addPiece(8, 5, PieceType.PO, Team.CHO) // <- 목적지
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isFalse();
        }

        @Test
        @DisplayName("포다리가 포가 아니면서 도착지에 포가 아닌 기물이 있으면 이동할 수 있다.")
        void test5() {
            // given
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            Board board = new BoardFixture()
//                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5) // <- 포댜리
                .anyPieceNotPo(8, 5) // <- 목적지
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isTrue();
        }

        @Test
        @DisplayName("포가 이동할 때 포다리가 하나이면서 포다리가 포가 아니면서 도착 좌표에 피스가 없을 경우 이동할 수 있다.")
        void test6() {
            // given
            final var departure = new Coordinate(5, 5);
            final var arrival = new Coordinate(8, 5);

            Board board = new BoardFixture()
//                .addPiece(5, 5, po)
                .anyPieceNotPo(6, 5) // <- 포다리
                .build();

            // when
            final var canMove = movement.canMove(departure, arrival, board);

            // then
            assertThat(canMove).isTrue();
        }
    }
}
