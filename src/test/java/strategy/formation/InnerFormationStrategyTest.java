package strategy.formation;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import domain.piece.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InnerFormationStrategyTest {

    private InitialFormationStrategy initialFormationStrategy;

    @BeforeEach
    public void setUp() {
        initialFormationStrategy = new InnerFormationStrategy();
    }


    @Nested
    class 한나라 {
        @Test
        public void 안상차림일_때_마와_상의_좌표가_올바르다() {
            Map<Position, Piece> formation = initialFormationStrategy.createInitialPieces(TeamColor.HAN);

            assertThat(formation.size()).isEqualTo(16);
            assertThat(formation.get(Position.of(0, 2)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
            assertThat(formation.get(Position.of(0, 6)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
            assertThat(formation.get(Position.of(0, 1)).getPieceType()).isEqualTo(PieceType.HORSE);
            assertThat(formation.get(Position.of(0, 7)).getPieceType()).isEqualTo(PieceType.HORSE);
            assertThat(formation.get(Position.of(1, 4)).getPieceType()).isEqualTo(PieceType.KING);
        }
    }

    @Nested
    class 초나라 {
        @Test
        public void 안상차림일_때_마와_상의_좌표가_올바르다() {
            Map<Position, Piece> formation = initialFormationStrategy.createInitialPieces(TeamColor.CHO);

            assertThat(formation.size()).isEqualTo(16);
            assertThat(formation.get(Position.of(9, 2)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
            assertThat(formation.get(Position.of(9, 6)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
            assertThat(formation.get(Position.of(9, 7)).getPieceType()).isEqualTo(PieceType.HORSE);
            assertThat(formation.get(Position.of(9, 1)).getPieceType()).isEqualTo(PieceType.HORSE);
            assertThat(formation.get(Position.of(8, 4)).getPieceType()).isEqualTo(PieceType.KING);
        }
    }
}


