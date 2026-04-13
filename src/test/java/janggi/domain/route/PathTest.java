package janggi.domain.route;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PathTest {
    @DisplayName("마의 경로를 생성한다.")
    @Test
    void 마_경로_생성_테스트() {
        // given
        Position start = new Position(0, 0);
        List<Direction> horseSteps = List.of(Direction.S, Direction.SE);

        // when
        Optional<Path> path = Path.fromSequence(start, horseSteps);

        // then
        assertThat(path).isPresent();
        assertThat(path.get()).hasSize(2);
        assertThat(path.get()).containsExactly(
                new Position(1, 0),
                new Position(2, 1)
        );
    }

    @DisplayName("상의 경로를 생성한다.")
    @Test
    void 상_경로_생성_테스트() {
        // given
        Position start = new Position(3, 3);
        List<Direction> elephantSteps = List.of(Direction.S, Direction.SE, Direction.SE);

        // when
        Optional<Path> path = Path.fromSequence(start, elephantSteps);

        // then
        assertThat(path).isPresent();
        assertThat(path.get()).hasSize(3);
        assertThat(path.get()).containsExactly(
                new Position(4, 3),
                new Position(5, 4),
                new Position(6, 5)
        );
    }

    @DisplayName("이동 중간에 보드 밖으로 나가는 좌표가 포함되는 경우, 빈 경로를 반환한다.")
    @Test
    void 경로_생성_실패_테스트() {
        // given
        Position start = new Position(0, 0);
        List<Direction> outSteps = List.of(Direction.N, Direction.NW);

        // when
        Optional<Path> path = Path.fromSequence(start, outSteps);

        // then
        assertThat(path.isEmpty()).isTrue();
    }

    @DisplayName("연속 이동 경로를 생성한다.")
    @Test
    void 연속_이동_경로_생성_테스트() {
        // given
        Position start = new Position(7, 0);
        Direction direction = Direction.N;

        // when
        Path path = Path.fromContinuousMove(start, direction);

        // then
        assertThat(path).hasSize(7);
        assertThat(path).containsExactly(
                new Position(6, 0), new Position(5, 0), new Position(4, 0),
                new Position(3, 0), new Position(2, 0), new Position(1, 0),
                new Position(0, 0)
        );
    }

    @DisplayName("보드 끝에서 이동할 수 없는 방향으로 이동을 시도하는 경우, 빈 경로를 반환한다.")
    @Test
    void 보드_끝_이동_시도_빈_경로_반환_테스트() {
        // given
        Position start = new Position(0, 0);
        Direction direction = Direction.N;

        // when
        Path path = Path.fromContinuousMove(start, direction);

        // then
        assertThat(path.isEmpty()).isTrue();
    }
}
