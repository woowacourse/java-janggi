import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.Pieces;
import piece.PiecesCreateFactory;
import piece.Team;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PiecesCreateFactoryTest {
    @Test
    void 피스_생성_테스트() {
        // given
        List<String> testPieces = List.of(
                "0,3 차 청",
                "0,1 상 청",
                "0,2 마 청",
                "0,3 사 청",
                "1,4 궁 청",
                "2,1 포 청",
                "3,0 졸 청",
                "6,0 차 홍"
        );

        // when
        Map<Team, Pieces> piecesMap = PiecesCreateFactory.generate(testPieces);

        // then
        Assertions.assertThat(piecesMap.get(Team.BLUE).size()).isEqualTo(7);
        Assertions.assertThat(piecesMap.get(Team.RED).size()).isEqualTo(1);
    }
}
