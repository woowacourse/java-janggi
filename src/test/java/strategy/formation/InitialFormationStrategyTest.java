package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class InitialFormationStrategyTest {

    private InitialFormationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new TestStrategy();
    }

    @Test
    void 한나라_고정_기물들이_올바른_위치에_배치된다() {
        Map<Position, Piece> result = strategy.placeFixedPieces(TeamColor.HAN);

        // 한나라는 위쪽 진영 (y = 0 ~ 3)
        // 1. 차
        assertThat(result.get(Position.of(0, 0)).getPieceType()).isEqualTo(PieceType.ROOK);
        assertThat(result.get(Position.of(8, 0)).getPieceType()).isEqualTo(PieceType.ROOK);

        // 2. 사
        assertThat(result.get(Position.of(3, 0)).getPieceType()).isEqualTo(PieceType.GUARD);
        assertThat(result.get(Position.of(5, 0)).getPieceType()).isEqualTo(PieceType.GUARD);

        // 3. 궁
        assertThat(result.get(Position.of(4, 1)).getPieceType()).isEqualTo(PieceType.KING);

        // 4. 포
        assertThat(result.get(Position.of(1, 2)).getPieceType()).isEqualTo(PieceType.CANNON);
        assertThat(result.get(Position.of(7, 2)).getPieceType()).isEqualTo(PieceType.CANNON);

        // 5. 병
        assertThat(result.get(Position.of(0, 3)).getPieceType()).isEqualTo(PieceType.PAWN);
        assertThat(result.get(Position.of(4, 3)).getPieceType()).isEqualTo(PieceType.PAWN);
        assertThat(result.get(Position.of(8, 3)).getPieceType()).isEqualTo(PieceType.PAWN);
    }

    @Test
    void 초나라_고정_기물들이_올바른_위치에_배치된다() {
        Map<Position, Piece> result = strategy.placeFixedPieces(TeamColor.CHO);

        // 초나라는 아래쪽 진영 (y = 6 ~ 9)
        // 1. 차
        assertThat(result.get(Position.of(0, 9)).getPieceType()).isEqualTo(PieceType.ROOK);
        assertThat(result.get(Position.of(8, 9)).getPieceType()).isEqualTo(PieceType.ROOK);

        // 2. 사
        assertThat(result.get(Position.of(3, 9)).getPieceType()).isEqualTo(PieceType.GUARD);
        assertThat(result.get(Position.of(5, 9)).getPieceType()).isEqualTo(PieceType.GUARD);

        // 3. 궁
        assertThat(result.get(Position.of(4, 8)).getPieceType()).isEqualTo(PieceType.KING);

        // 4. 포
        assertThat(result.get(Position.of(1, 7)).getPieceType()).isEqualTo(PieceType.CANNON);
        assertThat(result.get(Position.of(7, 7)).getPieceType()).isEqualTo(PieceType.CANNON);

        // 5. 졸
        assertThat(result.get(Position.of(0, 6)).getPieceType()).isEqualTo(PieceType.PAWN);
        assertThat(result.get(Position.of(4, 6)).getPieceType()).isEqualTo(PieceType.PAWN);
        assertThat(result.get(Position.of(8, 6)).getPieceType()).isEqualTo(PieceType.PAWN);
    }
}

class TestStrategy implements InitialFormationStrategy {
    @Override
    public Map<Position, Piece> setupFormation(TeamColor teamColor) {
        return Map.of();
    }
}