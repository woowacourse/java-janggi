package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.fixture.SetupPolicyTestFixture;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.text.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

public class LeftElephantSetupPolicyTest {

    @ParameterizedTest
    @DisplayName("왼상 차림 테스트")
    void setUp(List<Entry<Position, PieceType>> s1) {
        Map<Position, PieceType> expected =
            new LinkedHashMap<>(SetupPolicyTestFixture.상_마를_제외한_기물_배치_정보_제공());
        expected.put(Position.valueOf(1, 2), PieceType.ELEPHANT);
        expected.put(Position.valueOf(1, 3), PieceType.HORSE);
        expected.put(Position.valueOf(1, 7), PieceType.ELEPHANT);
        expected.put(Position.valueOf(1, 8), PieceType.HORSE);

        Map<Position, PieceType> actual = InnerElephantSetupPolicy.offerBoardMap();

        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(expected);
    }
}