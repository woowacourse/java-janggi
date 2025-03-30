package position;

import game.Board;
import org.junit.jupiter.api.Test;
import piece.Country;
import piece.Rook;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static testutil.TestConstant.*;

class PathTest {

    @Test
    void 마지막_위치를_가져올_수_있다() {
        // given
        Path path = new Path(List.of(B5, C5, D5));

        // when
        Position last = path.getLast();

        // then
        assertThat(last).isEqualTo(D5);
    }

    @Test
    void 마지막_위치를_제외한_Path를_반환한다() {
        // given
        Path path = new Path(List.of(B5, C5, D5));

        // when
        Path trimmed = path.withoutLast();

        // then
        assertThat(trimmed.positions()).containsExactly(B5, C5);
    }

    @Test
    void 마지막_요소_없이_비어있는_Path를_반환한다() {
        // given
        Path path = new Path(List.of(E5));

        // when
        Path trimmed = path.withoutLast();

        // then
        assertThat(trimmed.positions()).isEmpty();
    }

    @Test
    void 특정_위치를_포함하는지_확인한다() {
        // given
        Path path = new Path(List.of(B5, C5, D5));

        // then
        assertThat(path.contains(C5)).isTrue();
        assertThat(path.contains(E1)).isFalse();
    }

    @Test
    void 목적지가_일치하면_true를_반환한다() {
        // given
        Path path = new Path(List.of(B5, C5, D5));

        // then
        assertThat(path.isDestination(D5)).isTrue();
        assertThat(path.isDestination(C5)).isFalse();
    }

    @Test
    void 장애물이_없으면_예외없이_통과한다() {
        // given
        Board board = new Board(Map.of());
        Path path = new Path(List.of(B5, C5, D5));

        // expect
        assertThatCode(() -> path.validateNoObstacles(board))
                .doesNotThrowAnyException();
    }

    @Test
    void 중간에_기물이_있으면_예외를_던진다() {
        // given
        Board board = new Board(Map.of(C5, new Rook(Country.CHO)));
        Path path = new Path(List.of(B5, C5, D5));

        // expect
        assertThatThrownBy(() -> path.validateNoObstacles(board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중간에 기물이 있어 갈 수 없습니다.");
    }

    @Test
    void 비어있는_Path에서_getLast_호출시_예외를_던진다() {
        // given
        Path path = new Path(List.of());

        // expect
        assertThatThrownBy(path::getLast)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(  "경로가 비어 있습니다.");
    }

    @Test
    void isEmpty_는_경로가_비어있는지_확인한다() {
        assertThat(new Path(List.of()).isEmpty()).isTrue();
        assertThat(new Path(List.of(A1)).isEmpty()).isFalse();
    }
}
