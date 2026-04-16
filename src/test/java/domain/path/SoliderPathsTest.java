package domain.path;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Side;
import domain.vo.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoliderPathsTest {

    @DisplayName("초 병은 일반 위치에서 전진과 좌우 이동 경로를 반환한다.")
    @Test
    void 초_병은_일반_위치에서_전진과_좌우_이동_경로를_반환한다() {
        SoliderPaths paths = new SoliderPaths(Side.CHO);

        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(1, 7)));

        assertThat(actual).containsExactly(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
        );
    }

    @DisplayName("초 병은 궁성 중앙에서 대각선 전진 경로를 추가로 반환한다.")
    @Test
    void 초_병은_궁성_중앙에서_대각선_전진_경로를_추가로_반환한다() {
        SoliderPaths paths = new SoliderPaths(Side.CHO);

        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(5, 2)));

        assertThat(actual).containsExactly(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT),
            List.of(Direction.UP_LEFT),
            List.of(Direction.UP_RIGHT)
        );
    }

    @DisplayName("초 병은 궁성 좌하단에서 우상향 대각선만 추가한다.")
    @Test
    void 초_병은_궁성_좌하단에서_우상향_대각선만_추가한다() {
        SoliderPaths paths = new SoliderPaths(Side.CHO);

        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(4, 3)));

        assertThat(actual).containsExactly(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT),
            List.of(Direction.UP_RIGHT)
        );
    }

    @DisplayName("한 졸은 일반 위치에서 전진과 좌우 이동 경로를 반환한다.")
    @Test
    void 한_졸은_일반_위치에서_전진과_좌우_이동_경로를_반환한다() {
        SoliderPaths paths = new SoliderPaths(Side.HAN);

        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(1, 4)));

        assertThat(actual).containsExactly(
            List.of(Direction.DOWN),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
        );
    }

    @DisplayName("한 졸은 궁성 중앙에서 대각선 전진 경로를 추가로 반환한다.")
    @Test
    void 한_졸은_궁성_중앙에서_대각선_전진_경로를_추가로_반환한다() {
        SoliderPaths paths = new SoliderPaths(Side.HAN);

        List<List<Direction>> actual = toDirections(paths.getPaths(Position.of(5, 9)));

        assertThat(actual).containsExactly(
            List.of(Direction.DOWN),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT),
            List.of(Direction.DOWN_LEFT),
            List.of(Direction.DOWN_RIGHT)
        );
    }

    private List<List<Direction>> toDirections(List<Path> paths) {
        return paths.stream()
            .map(Path::getPath)
            .toList();
    }
}
