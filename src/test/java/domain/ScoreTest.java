package domain;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    void 기물의_점수가_올바르게_초기화되었는지_확인() {

        // given
        final Map<PieceType, Integer> pieceCounts = Map.ofEntries(
                entry(PieceType.GENERAL, 1),
                entry(PieceType.SOLDIER, 1),
                entry(PieceType.GUARD, 1),
                entry(PieceType.ELEPHANT, 1),
                entry(PieceType.HORSE, 1),
                entry(PieceType.CANNON, 1),
                entry(PieceType.CHARIOT, 1)
        );

        // when
        // then
        assertThat(Score.calculate(pieceCounts)).isEqualTo(33);
    }

    @Test
    void 남은_기물들의_점수_총합_계산() {

        // given
        final Map<PieceType, Integer> pieceCounts = Map.ofEntries(
                entry(PieceType.GENERAL, 1),
                entry(PieceType.SOLDIER, 3),
                entry(PieceType.GUARD, 1),
                entry(PieceType.ELEPHANT, 2),
                entry(PieceType.HORSE, 1),
                entry(PieceType.CANNON, 2),
                entry(PieceType.CHARIOT, 1)
        );

        // when
        // then
        assertThat(Score.calculate(pieceCounts)).isEqualTo(47);
    }
}
