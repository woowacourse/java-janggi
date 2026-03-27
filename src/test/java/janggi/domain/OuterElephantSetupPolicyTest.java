package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.piece.PieceType;
import janggi.domain.setup.OuterElephantSetupPolicy;
import janggi.fixture.SetupPolicyTestFixture;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OuterElephantSetupPolicyTest {

    @Test
    @DisplayName("바깥상 차림 테스트")
    void setUp() {
        OuterElephantSetupPolicy policy = new OuterElephantSetupPolicy();
        Map<Position, PieceType> expected =
                new LinkedHashMap<>(SetupPolicyTestFixture.상_마를_제외한_기물_배치_정보_제공());
        expected.put(Position.valueOf(1, 2), PieceType.ELEPHANT);
        expected.put(Position.valueOf(1, 3), PieceType.HORSE);
        expected.put(Position.valueOf(1, 7), PieceType.HORSE);
        expected.put(Position.valueOf(1, 8), PieceType.ELEPHANT);

        Map<Position, PieceType> actual = policy.offerBoardMap();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
