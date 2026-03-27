package domain;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    private Board board;
    private Piece choPawn;
    private Piece hanHorse;
    private Piece choCannon;

    @BeforeEach
    void setUp() {
        choPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
        hanHorse = Piece.of(TeamColor.HAN, PieceType.HORSE);
        choCannon = Piece.of(TeamColor.CHO, PieceType.CANNON);
        board = new Board(Map.of(
                Position.of(3, 4), choPawn,
                Position.of(2, 4), hanHorse,
                Position.of(1, 4), choCannon
        ));
    }

    @Nested
    class 사실조회 {
        @Test
        void 경로_중간에_놓인_기물들을_순서대로_반환한다() {
            Route route = new Route(
                    Position.of(4, 4),
                    Position.of(1, 4),
                    java.util.List.of(Position.of(3, 4), Position.of(2, 4))
            );

            assertThat(board.getBlockingPieces(route)).containsExactly(choPawn, hanHorse);
        }

        @Test
        void 도착지_기물이_있으면_반환한다() {
            Route route = new Route(
                    Position.of(4, 4),
                    Position.of(1, 4),
                    java.util.List.of(Position.of(3, 4), Position.of(2, 4))
            );

            assertThat(board.getDestinationPiece(route)).contains(choCannon);
        }

        @Test
        void 도착지_기물이_없으면_빈_Optional을_반환한다() {
            Route route = new Route(
                    Position.of(4, 4),
                    Position.of(0, 4),
                    java.util.List.of(Position.of(3, 4), Position.of(2, 4), Position.of(1, 4))
            );

            assertThat(board.getDestinationPiece(route)).isEqualTo(Optional.empty());
        }
    }

    @Nested
    class 위치조회 {
        @Test
        void 특정_기물의_현재_위치를_찾는다() {
            assertThat(board.findPositionOf(hanHorse)).contains(Position.of(2, 4));
        }
    }

    @Nested
    class 이동 {
        @Test
        void 기물과_도착지를_받아_실제_이동을_반영한다() {
            Piece movingPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingPawn
            ));

            movableBoard.move(movingPawn, Position.of(3, 4));

            assertThat(movableBoard.findPositionOf(movingPawn)).contains(Position.of(3, 4));
            assertThat(movableBoard.findPiece(Position.of(4, 4))).isEmpty();
            assertThat(movableBoard.findPiece(Position.of(3, 4))).contains(movingPawn);
        }
    }
}
