package domain;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

        @Test
        void 팀에_속한_기물들을_좌표순으로_조회한다() {
            assertThat(board.findPiecesByTeam(TeamColor.CHO))
                    .extracting(entry -> entry.getKey())
                    .containsExactly(Position.of(1, 4), Position.of(3, 4));
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

            assertThat(movableBoard.move(movingPawn, Position.of(3, 4))).isEmpty();

            assertThat(movableBoard.findPositionOf(movingPawn)).contains(Position.of(3, 4));
            assertThat(movableBoard.findPiece(Position.of(4, 4))).isEmpty();
            assertThat(movableBoard.findPiece(Position.of(3, 4))).contains(movingPawn);
        }

        @Test
        void 빈_칸으로_이동하면_잡힌_기물이_없다() {
            Piece movingPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingPawn
            ));

            Optional<Piece> captured = movableBoard.move(movingPawn, Position.of(3, 4));

            assertThat(captured).isEmpty();
        }

        @Test
        void 상대_일반_기물이_있는_칸으로_이동하면_잡힌_기물을_반환한다() {
            Piece choRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
            Piece hanHorse = Piece.of(TeamColor.HAN, PieceType.HORSE);
            Board movableBoard = new Board(Map.of(
                    Position.of(1, 3), choRook,
                    Position.of(1, 4), hanHorse
            ));

            Optional<Piece> captured = movableBoard.move(choRook, Position.of(1, 4));

            assertThat(captured).contains(hanHorse);
            assertThat(captured.orElseThrow().isKing()).isFalse();
        }

        @Test
        void 상대_왕이_있는_칸으로_이동하면_잡힌_왕을_반환한다() {
            Piece choRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
            Piece hanKing = Piece.of(TeamColor.HAN, PieceType.KING);
            Board movableBoard = new Board(Map.of(
                    Position.of(1, 3), choRook,
                    Position.of(1, 4), hanKing
            ));

            Optional<Piece> captured = movableBoard.move(choRook, Position.of(1, 4));

            assertThat(captured).contains(hanKing);
            assertThat(captured.orElseThrow().isKing()).isTrue();
        }

        @Test
        void 현재_판_상태를_기준으로_기물의_이동_가능_경로를_반환한다() {
            Piece movingPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            Piece allyPiece = Piece.of(TeamColor.CHO, PieceType.GUARD);
            Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingPawn,
                    Position.of(4, 5), allyPiece
            ));

            MovableRoutes movable = movableBoard.findMovableRoutes(movingPawn);

            assertThat(movable.routes())
                    .extracting(Route::endPos)
                    .containsExactlyInAnyOrder(Position.of(3, 4), Position.of(4, 3));
            assertThat(movable.hasKingDestination()).isFalse();
        }

        @Test
        void 이동_가능_경로_중_목적지에_왕이_있으면_왕_목적지_여부를_참으로_반환한다() {
            Piece choRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
            Piece hanKing = Piece.of(TeamColor.HAN, PieceType.KING);
            Board movableBoard = new Board(Map.of(
                    Position.of(1, 3), choRook,
                    Position.of(1, 4), hanKing
            ));

            MovableRoutes movable = movableBoard.findMovableRoutes(choRook);

            assertThat(movable.hasKingDestination()).isTrue();
            assertThat(movable.routes())
                    .extracting(Route::endPos)
                    .contains(Position.of(1, 4));
        }

        @Test
        void 여러_이동_후보_중_일부만_왕을_목적지로_해도_왕_목적지_여부는_참이다() {
            Piece choRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
            Piece hanKing = Piece.of(TeamColor.HAN, PieceType.KING);
            Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), choRook,
                    Position.of(4, 0), hanKing
            ));

            MovableRoutes movable = movableBoard.findMovableRoutes(choRook);

            assertThat(movable.routes().size()).isGreaterThan(1);
            assertThat(movable.hasKingDestination()).isTrue();
            assertThat(movable.routes())
                    .extracting(Route::endPos)
                    .contains(Position.of(4, 0));
        }
    }
}
