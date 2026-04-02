package janggi.domain.setup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Position;
import janggi.domain.fixture.SetupPolicyTestFixture;
import janggi.domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RightElephantElephantFormationTest {

    @Test
    @DisplayName("오른상 차림 테스트")
    void setUp() {
        RightElephantElephantFormation policy = new RightElephantElephantFormation();
        Map<Position, PieceType> expected =
                new LinkedHashMap<>(SetupPolicyTestFixture.상_마를_제외한_기물_배치_정보_제공());
        expected.put(Position.valueOf(1, 2), PieceType.HORSE);
        expected.put(Position.valueOf(1, 3), PieceType.ELEPHANT);
        expected.put(Position.valueOf(1, 7), PieceType.HORSE);
        expected.put(Position.valueOf(1, 8), PieceType.ELEPHANT);

        Map<Position, PieceType> actual = policy.offerBoardMap();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}