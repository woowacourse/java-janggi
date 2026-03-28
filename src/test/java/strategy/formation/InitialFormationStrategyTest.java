package strategy.formation;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import domain.piece.TeamColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class InitialFormationStrategyTest {

    private InitialFormationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new TestStrategy();
    }

    @Nested
    class 한나라 {
        @Test
        void 고정_기물들이_올바른_위치에_배치된다() {
            Map<Position, Piece> result = strategy.setUpPieces(TeamColor.HAN);

            assertThat(result).hasSize(12);
            assertThat(result.get(Position.of(0, 0)).getPieceType()).isEqualTo(PieceType.ROOK);
            assertThat(result.get(Position.of(0, 8)).getPieceType()).isEqualTo(PieceType.ROOK);
            assertThat(result.get(Position.of(0, 3)).getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(result.get(Position.of(0, 5)).getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(result.get(Position.of(1, 4)).getPieceType()).isEqualTo(PieceType.KING);
            assertThat(result.get(Position.of(2, 1)).getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(result.get(Position.of(2, 7)).getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(result.get(Position.of(3, 0)).getPieceType()).isEqualTo(PieceType.PAWN);
            assertThat(result.get(Position.of(3, 4)).getPieceType()).isEqualTo(PieceType.PAWN);
            assertThat(result.get(Position.of(3, 8)).getPieceType()).isEqualTo(PieceType.PAWN);
        }
    }

    @Nested
    class 초나라 {
        @Test
        void 고정_기물들이_올바른_위치에_배치된다() {
            Map<Position, Piece> result = strategy.setUpPieces(TeamColor.CHO);

            assertThat(result).hasSize(12);
            assertThat(result.get(Position.of(9, 0)).getPieceType()).isEqualTo(PieceType.ROOK);
            assertThat(result.get(Position.of(9, 8)).getPieceType()).isEqualTo(PieceType.ROOK);
            assertThat(result.get(Position.of(9, 3)).getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(result.get(Position.of(9, 5)).getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(result.get(Position.of(8, 4)).getPieceType()).isEqualTo(PieceType.KING);
            assertThat(result.get(Position.of(7, 1)).getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(result.get(Position.of(7, 7)).getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(result.get(Position.of(6, 0)).getPieceType()).isEqualTo(PieceType.PAWN);
            assertThat(result.get(Position.of(6, 4)).getPieceType()).isEqualTo(PieceType.PAWN);
            assertThat(result.get(Position.of(6, 8)).getPieceType()).isEqualTo(PieceType.PAWN);
        }
    }
}

class TestStrategy extends InitialFormationStrategy {
    @Override
    protected Map<Position, Piece> setupFormation(TeamColor teamColor) {
        return Map.of();
    }
}


