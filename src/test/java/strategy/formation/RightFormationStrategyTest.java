package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RightFormationStrategyTest {

    private InitialFormationStrategy initialFormationStrategy;

    @BeforeEach
    public void setUp() {
        initialFormationStrategy = new RightFormationStrategy();
    }

    @Test
    public void 한나라_우상차림일_때_마와_상의_좌표가_올바르다() {
        Map<Position, Piece> formation = initialFormationStrategy.setupFormation(TeamColor.HAN);

        assertThat(formation.size()).isEqualTo(4);
        assertThat(formation.get(Position.of(1, 0)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(2, 0)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(6, 0)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(7, 0)).getPieceType()).isEqualTo(PieceType.HORSE);
    }

    @Test
    public void 초나라_우상차림일_때_마와_상의_좌표가_올바르다() {
        Map<Position, Piece> formation = initialFormationStrategy.setupFormation(TeamColor.CHO);

        assertThat(formation.size()).isEqualTo(4);
        assertThat(formation.get(Position.of(1, 9)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(2, 9)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(6, 9)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(7, 9)).getPieceType()).isEqualTo(PieceType.HORSE);
    }
}
