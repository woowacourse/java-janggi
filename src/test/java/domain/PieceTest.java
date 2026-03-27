package domain;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Nested
    class 전략위임 {
        @Test
        void 기물은_자기_전략으로_후보_경로를_생성한다() {
            Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThat(piece.makeRoutes(Position.of(2, 3))).containsExactlyInAnyOrder(
                    new Route(Position.of(2, 3), Position.of(1, 3), List.of()),
                    new Route(Position.of(2, 3), Position.of(2, 4), List.of()),
                    new Route(Position.of(2, 3), Position.of(2, 2), List.of())
            );
        }

        @Test
        void 기물은_자기_전략으로_이동_가능_여부를_판단한다() {
            Piece piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
            Route route = new Route(Position.of(2, 3), Position.of(1, 3), List.of());

            boolean canMove = piece.canMove(
                    route,
                    List.of(),
                    Optional.of(Piece.of(TeamColor.HAN, PieceType.HORSE))
            );

            assertThat(canMove).isTrue();
        }
    }
}
