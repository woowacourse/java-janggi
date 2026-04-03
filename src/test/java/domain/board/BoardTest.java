package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.Map;
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
    class 위치조회 {
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
            final Piece movingPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingPawn
            ));

            movableBoard.move(movingPawn, Position.of(3, 4));

            assertThat(movableBoard.findPiece(Position.of(4, 4))).isEmpty();
            assertThat(movableBoard.findPiece(Position.of(3, 4))).contains(movingPawn);
        }

        @Test
        void 현재_판_상태를_기준으로_기물의_이동_가능_경로를_반환한다() {
            final Piece movingPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Piece allyPiece = Piece.of(TeamColor.CHO, PieceType.GUARD);
            final Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingPawn,
                    Position.of(4, 5), allyPiece
            ));

            assertThat(movableBoard.findMovableRoutes(movingPawn))
                    .extracting(Route::endPos)
                    .containsExactlyInAnyOrder(Position.of(3, 4), Position.of(4, 3));
        }
    }
}


