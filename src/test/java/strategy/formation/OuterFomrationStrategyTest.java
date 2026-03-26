package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OuterFomrationStrategyTest {

    private InitialFormationStrategy initialFormationStrategy;

    @BeforeEach
    public void setUp() {
        initialFormationStrategy = new OuterFomrationStrategy();
    }

    @Test
    public void 한나라_바깥상차림일_때_마와_상의_좌표가_올바르다() {
        Map<Position, Piece> formation = initialFormationStrategy.setupFormation(TeamColor.HAN);

        assertThat(formation.size()).isEqualTo(4);

        // 상좌표 검증 - 한상1(1,0), 한상2(7,0)
        assertThat(formation.get(Position.of(1, 0)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(7, 0)).getPieceType()).isEqualTo(PieceType.ELEPHANT);

        // 마좌표 검증 - 한마1(2,0), 한마2(6,0)
        assertThat(formation.get(Position.of(2, 0)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(6, 0)).getPieceType()).isEqualTo(PieceType.HORSE);
    }

    @Test
    public void 초나라_바깥상차림일_때_마와_상의_좌표가_올바르다() {
        Map<Position, Piece> formation = initialFormationStrategy.setupFormation(TeamColor.CHO);

        assertThat(formation.size()).isEqualTo(4);

        // 상좌표 검증 - 초상1(1,9), 초상2(7,9)
        assertThat(formation.get(Position.of(1, 9)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(7, 9)).getPieceType()).isEqualTo(PieceType.ELEPHANT);

        // 마좌표 검증 - 초마1(2,9), 초마2(6,9)
        assertThat(formation.get(Position.of(2, 9)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(6, 9)).getPieceType()).isEqualTo(PieceType.HORSE);
    }
}
