package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static testUtil.TestConstant.RANK_5;

import java.util.List;
import org.junit.jupiter.api.Test;
import position.Position;
import position.PositionFile;
import position.PositionRank;

class MoveDirectionTest {

    @Test
    void 일반_방향에서_유효한_다음_위치_하나를_반환한다() {
        // given
        Position current = new Position(PositionFile.FILE_5, RANK_5);

        // when
        List<Position> result = MoveDirection.UP.calculateNextPositionsFrom(current);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(current.add(0, 1));
    }

    @Test
    void 범위를_벗어난_위치는_빈_리스트를_반환한다() {
        // given
        Position edge = new Position(PositionFile.FILE_5, PositionRank.ofEachCountry(10, game.Country.CHO));

        // when
        List<Position> result = MoveDirection.UP.calculateNextPositionsFrom(edge);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void CROSS_INF는_십자_모양의_모든_위치를_반환한다() {
        // given
        Position current = new Position(PositionFile.FILE_5, RANK_5);

        // when
        List<Position> result = MoveDirection.CROSS_INF.calculateNextPositionsFrom(current);

        // then
        assertThat(result).hasSize(17);
    }
}
