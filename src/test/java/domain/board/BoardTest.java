package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(Map.of(
                Position.of(3, 4), Piece.of(TeamColor.CHO, PieceType.PAWN),
                Position.of(2, 4), Piece.of(TeamColor.HAN, PieceType.HORSE),
                Position.of(1, 4), Piece.of(TeamColor.CHO, PieceType.CANNON)
        ));
    }

    @Nested
    class 위치조회 {
        @Test
        void 팀에_속한_기물들을_좌표순으로_조회한다() {
            assertThat(board.findPiecesByTeam(TeamColor.CHO))
                    .extracting(piecePosition -> piecePosition.position())
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
        void 상대_왕을_포획하면_이동_결과에_반영한다() {
            final Piece movingRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
            final Piece hanKing = Piece.of(TeamColor.HAN, PieceType.KING);
            final Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingRook,
                    Position.of(1, 4), hanKing
            ));

            final MoveResult moveResult = movableBoard.move(movingRook, Position.of(1, 4));

            assertThat(moveResult.capturedKing()).isTrue();
            assertThat(movableBoard.findPiece(Position.of(1, 4))).contains(movingRook);
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

    @Nested
    class 예외 {
        @Test
        void 보드에_없는_기물의_이동_가능_경로를_조회하면_예외가_발생한다() {
            final Piece missingPiece = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThatThrownBy(() -> board.findMovableRoutes(missingPiece))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("보드에 없는 기물입니다.");
        }

        @Test
        void 보드에_없는_기물을_이동시키면_예외가_발생한다() {
            final Piece missingPiece = Piece.of(TeamColor.CHO, PieceType.PAWN);

            assertThatThrownBy(() -> board.move(missingPiece, Position.of(2, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("보드에 없는 기물입니다.");
        }

        @Test
        void 기물이_생성할_수_없는_목적지로_이동하면_예외가_발생한다() {
            final Piece movingPawn = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingPawn
            ));

            assertThatThrownBy(() -> movableBoard.move(movingPawn, Position.of(5, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("해당 기물은 목적지로 이동할 수 없습니다.");
        }

        @Test
        void 경로는_있지만_현재_판_상태에서_막혀_이동할_수_없으면_예외가_발생한다() {
            final Piece movingRook = Piece.of(TeamColor.CHO, PieceType.ROOK);
            final Piece blockingPiece = Piece.of(TeamColor.CHO, PieceType.PAWN);
            final Board movableBoard = new Board(Map.of(
                    Position.of(4, 4), movingRook,
                    Position.of(3, 4), blockingPiece
            ));

            assertThatThrownBy(() -> movableBoard.move(movingRook, Position.of(1, 4)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("현재 판 상태에서는 해당 목적지로 이동할 수 없습니다.");
        }
    }
}
