package domain.position;

import domain.piece.MoveDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static testUtil.TestConstant.*;

class PositionFileTest {

    @Test
    void 파일에_값을_더하여_다음_파일을_구할_수_있다() {
        // given
        final PositionFile positionFile = PositionFile.가;
        final int addingValue = 1;

        // when
        final PositionFile result = positionFile.add(addingValue);

        // then
        assertThat(result).isEqualTo(PositionFile.나);
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -6, 5, 6})
    void 파일에_값을_더했을_때_범위를_벗어나면_예외가_발생한다(int addingValue) {
        // given
        final PositionFile positionFile = PositionFile.마;

        // expected
        assertThatThrownBy(() -> positionFile.add(addingValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 파일을 찾을 수 없습니다.");
    }

    @Test
    void 현재_위치에서_이동_방향을_통해_경로를_구할_수_있다() {
        // given
        final Position position = new Position(PositionFile.마, RANK_5);

        // when
        final List<Path> result = position.getPathsFrom(List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT));

        // then
        assertThat(result).containsExactlyInAnyOrder(new Path(
                new Position(PositionFile.라, RANK_3),
                List.of(new Position(PositionFile.마, RANK_5), new Position(PositionFile.마, RANK_4), new Position(PositionFile.라, RANK_3))
        ));
    }

    @Test
    void 현재_파일과_새_파일_범위_내의_파일들을_구할_수_있다() {
        // given
        final PositionFile from = PositionFile.마;
        final PositionFile to = PositionFile.아;

        // when
        final List<PositionFile> betweenFiles =  from.getBetweenFiles(to);

        // then
        assertThat(betweenFiles).containsExactlyInAnyOrder(
                PositionFile.바,
                PositionFile.사
        );
    }

}
