package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.piece.PieceType;
import janggi.domain.setup.LeftElephantElephantFormation;
import janggi.fixture.SetupPolicyTestFixture;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LeftElephantElephantFormationTest {

    @Test
    @DisplayName("왼상 차림 테스트")
    void setUp() {
        LeftElephantElephantFormation policy = new LeftElephantElephantFormation();
        Map<Position, PieceType> expected =
                new LinkedHashMap<>(SetupPolicyTestFixture.상_마를_제외한_기물_배치_정보_제공());
        expected.put(Position.valueOf(1, 2), PieceType.ELEPHANT);
        expected.put(Position.valueOf(1, 3), PieceType.HORSE);
        expected.put(Position.valueOf(1, 7), PieceType.ELEPHANT);
        expected.put(Position.valueOf(1, 8), PieceType.HORSE);

        Map<Position, PieceType> actual = policy.offerBoardMap();

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}