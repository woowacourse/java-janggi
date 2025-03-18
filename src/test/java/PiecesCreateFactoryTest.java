import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PiecesCreateFactoryTest {
    @Test
    void 피스_생성_테스트() {
        List<String> testPieces = List.of(
                "0,3, 차 백",
                "0,1, 상 백",
                "0,2, 마 백",
                "0,3, 사 백",
                "1,4, 궁 백",
                "2,1, 포 백",
                "3,0, 졸 백"
        );

        Pieces pieces = PiecesCreateFactory.generate(testPieces);

        Assertions.assertThat(pieces.size()).isEqualTo(testPieces.size());
    }
}
