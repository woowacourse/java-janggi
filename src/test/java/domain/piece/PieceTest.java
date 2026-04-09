package domain.piece;

import domain.board.Position;
import domain.board.Route;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Nested
    class 기물판단 {
        @Test
        void 같은_팀인지_판단한다() {
            final Piece choPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Piece choGuard = Piece.of(TeamColor.CHO, PieceType.GUARD);
            final Piece hanHorse = Piece.of(TeamColor.HAN, PieceType.HORSE);

            assertThat(choPawn.isSameTeam(choGuard)).isTrue();
            assertThat(choPawn.isEnemy(hanHorse)).isTrue();
        }

        @Test
        void 도착지_점유_가능_여부를_판단한다() {
            final Piece choPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThat(choPawn.canOccupy(Optional.empty())).isTrue();
            assertThat(choPawn.canOccupy(Optional.of(Piece.of(TeamColor.HAN, PieceType.HORSE)))).isTrue();
            assertThat(choPawn.canOccupy(Optional.of(Piece.of(TeamColor.CHO, PieceType.GUARD)))).isFalse();
        }

    }

    @Nested
    class 전략위임 {
        @Test
        void 기물은_자기_전략으로_후보_경로를_생성한다() {
            final Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThat(piece.makeRoutes(Position.of(2, 3))).containsExactlyInAnyOrder(
                    new Route(Position.of(2, 3), Position.of(1, 3), List.of()),
                    new Route(Position.of(2, 3), Position.of(2, 4), List.of()),
                    new Route(Position.of(2, 3), Position.of(2, 2), List.of()),
                    new Route(Position.of(2, 3), Position.of(1, 4), List.of())
            );
        }

        @Test
        void 궁성과_사는_궁성_경로_생성기로_후보_경로를_생성한다() {
            final Piece piece = Piece.of(TeamColor.HAN, PieceType.GUARD);

            assertThat(piece.makeRoutes(Position.of(0, 3))).containsExactlyInAnyOrder(
                    new Route(Position.of(0, 3), Position.of(0, 4), List.of()),
                    new Route(Position.of(0, 3), Position.of(1, 3), List.of()),
                    new Route(Position.of(0, 3), Position.of(1, 4), List.of())
            );
        }

        @Test
        void 차는_궁성_중앙에서_기본_직선_경로와_궁성_대각선_경로를_함께_생성한다() {
            final Piece piece = Piece.of(TeamColor.CHO, PieceType.ROOK);

            assertThat(piece.makeRoutes(Position.of(8, 4))).contains(
                    new Route(Position.of(8, 4), Position.of(7, 4), List.of()),
                    new Route(Position.of(8, 4), Position.of(9, 4), List.of()),
                    new Route(Position.of(8, 4), Position.of(8, 3), List.of()),
                    new Route(Position.of(8, 4), Position.of(8, 5), List.of()),
                    new Route(Position.of(8, 4), Position.of(7, 3), List.of()),
                    new Route(Position.of(8, 4), Position.of(7, 5), List.of()),
                    new Route(Position.of(8, 4), Position.of(9, 3), List.of()),
                    new Route(Position.of(8, 4), Position.of(9, 5), List.of())
            );
        }

        @Test
        void 차는_궁성_꼭지점에서_기본_직선_경로와_궁성_대각선_경로를_함께_생성한다() {
            final Piece piece = Piece.of(TeamColor.HAN, PieceType.ROOK);

            assertThat(piece.makeRoutes(Position.of(0, 3))).contains(
                    new Route(Position.of(0, 3), Position.of(0, 4), List.of()),
                    new Route(Position.of(0, 3), Position.of(1, 3), List.of()),
                    new Route(Position.of(0, 3), Position.of(1, 4), List.of()),
                    new Route(Position.of(0, 3), Position.of(2, 5), List.of(Position.of(1, 4)))
            );
        }

        @Test
        void 포는_궁성_꼭지점에서_기본_직선_경로와_궁성_대각선_경로를_함께_생성한다() {
            final Piece piece = Piece.of(TeamColor.HAN, PieceType.CANNON);

            assertThat(piece.makeRoutes(Position.of(0, 3))).contains(
                    new Route(Position.of(0, 3), Position.of(1, 3), List.of()),
                    new Route(Position.of(0, 3), Position.of(0, 4), List.of()),
                    new Route(Position.of(0, 3), Position.of(2, 5), List.of(Position.of(1, 4)))
            );
        }

        @Test
        void 졸은_궁성_중앙에서_기본_이동_경로와_궁성_대각선_경로를_함께_생성한다() {
            final Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThat(piece.makeRoutes(Position.of(8, 4))).contains(
                    new Route(Position.of(8, 4), Position.of(7, 4), List.of()),
                    new Route(Position.of(8, 4), Position.of(8, 3), List.of()),
                    new Route(Position.of(8, 4), Position.of(8, 5), List.of()),
                    new Route(Position.of(8, 4), Position.of(7, 3), List.of()),
                    new Route(Position.of(8, 4), Position.of(7, 5), List.of())
            );
        }

        @Test
        void 졸은_궁성_꼭지점에서_기본_이동_경로와_궁성_대각선_경로를_함께_생성한다() {
            final Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThat(piece.makeRoutes(Position.of(9, 3))).contains(
                    new Route(Position.of(9, 3), Position.of(8, 3), List.of()),
                    new Route(Position.of(9, 3), Position.of(9, 4), List.of()),
                    new Route(Position.of(9, 3), Position.of(9, 2), List.of()),
                    new Route(Position.of(9, 3), Position.of(8, 4), List.of())
            );
        }

        @Test
        void 기물은_자기_전략으로_이동_가능_여부를_판단한다() {
            final Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Route route = new Route(Position.of(2, 3), Position.of(1, 3), List.of());

            final boolean canMove = piece.canMove(
                    route,
                    List.of(),
                    Optional.of(Piece.of(TeamColor.HAN, PieceType.HORSE))
            );

            assertThat(canMove).isTrue();
        }

        @Test
        void 기물은_공통_규칙으로_도착지의_아군을_점유할_수_없다() {
            final Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Route route = new Route(Position.of(2, 3), Position.of(1, 3), List.of());

            final boolean canMove = piece.canMove(
                    route,
                    List.of(),
                    Optional.of(Piece.of(TeamColor.CHO, PieceType.GUARD))
            );

            assertThat(canMove).isFalse();
        }
    }
}
