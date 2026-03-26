package strategy.formation;

import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InnerFormationStrategyTest {

    private InitialFormationStrategy initialFormationStrategy;

    @BeforeEach
    public void setUp() {
        initialFormationStrategy = new InnerFormationStrategy();
    }


    @Test
    public void 한나라_안상차림일_때_마와_상의_좌표가_올바르다() {
        // given & when: 한나라(TeamColor.HAN)의 전체 초기 배치 맵을 반환받는다.
        Map<Position, Piece> formation = initialFormationStrategy.setUpPieces(TeamColor.HAN);

        // then: 반환된 기물이 정확히 16개인지 확인
        assertThat(formation.size()).isEqualTo(16);

        // 상좌표 검증 - 한상1(2,0), 한상2(6,0)
        // (장기판 맨 윗줄이 y=0 이라고 가정)
        assertThat(formation.get(Position.of(2, 0)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(6, 0)).getPieceType()).isEqualTo(PieceType.ELEPHANT);

        // 마좌표 검증 - 한마1(1,0), 한마2(7,0)
        assertThat(formation.get(Position.of(1, 0)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(7, 0)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(4, 1)).getPieceType()).isEqualTo(PieceType.KING);
    }

    @Test
    public void 초나라_안상차림일_때_마와_상의_좌표가_올바르다() {
        // given & when: 초나라(TeamColor.CHO)의 전체 초기 배치 맵을 반환받는다.
        Map<Position, Piece> formation = initialFormationStrategy.setUpPieces(TeamColor.CHO);

        // then: 반환된 기물이 정확히 16개인지 확인
        assertThat(formation.size()).isEqualTo(16);

        // 상좌표 검증 - 초상1(2,9), 초상2(6,9)
        // (장기판 맨 아랫줄이 y=9 라고 가정)
        assertThat(formation.get(Position.of(2, 9)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(formation.get(Position.of(6, 9)).getPieceType()).isEqualTo(PieceType.ELEPHANT);

        // 마좌표 검증 - 초마1(1,9), 초마2(7,9)
        assertThat(formation.get(Position.of(7, 9)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(1, 9)).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(formation.get(Position.of(4, 8)).getPieceType()).isEqualTo(PieceType.KING);
    }
}
