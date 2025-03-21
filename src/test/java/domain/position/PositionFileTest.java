package domain.position;

import domain.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PositionFileTest extends BaseTest {

    @Test
    void 파일에_값을_더하여_다음_파일을_구할_수_있다() {
        // given
        final PositionFile positionFile = PositionFile.FILE_1;
        final int addingValue = 1;

        // when
        final PositionFile result = positionFile.add(addingValue);

        // then
        assertThat(result).isEqualTo(PositionFile.FILE_2);
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -6, 5, 6})
    void 파일에_값을_더했을_때_범위를_벗어나면_예외가_발생한다(int addingValue) {
        // given
        final PositionFile positionFile = PositionFile.FILE_5;

        // expected
        assertThatThrownBy(() -> positionFile.add(addingValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 파일을 찾을 수 없습니다.");
    }

    @Test
    void 현재_파일과_새_파일_범위_내의_파일들을_구할_수_있다() {
        // given
        final PositionFile from = PositionFile.FILE_5;
        final PositionFile to = PositionFile.FILE_8;

        // when
        final List<PositionFile> betweenFiles =  from.getBetweenFiles(to);

        // then
        assertThat(betweenFiles).containsExactlyInAnyOrder(
                PositionFile.FILE_6,
                PositionFile.FILE_7
        );
    }

}
