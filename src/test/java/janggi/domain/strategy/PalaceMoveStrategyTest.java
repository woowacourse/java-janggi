package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceMoveStrategyTest {
    private final MoveStrategy strategy = new PalaceMoveStrategy();

    @DisplayName("궁성의 정중앙인 경우, 상하좌우와 대각선을 포함해 8개의 경로를 생성한다.")
    @Test
    void 궁성_정중앙_경로_생성_테스트() {
        // given
        Position current = new Position(1, 4);
        EnumSet<Direction> directions = EnumSet.allOf(Direction.class);
        directions.remove(Direction.NONE);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Path> pathList = new ArrayList<>();
        paths.forEach(pathList::add);
        assertThat(pathList).hasSize(8);
    }

    @DisplayName("궁성의 꼭짓점인 경우, 대각선을 포함한 3개의 경로를 생성한다.")
    @Test
    void 궁성_꼭짓점_경로_생성_테스트() {
        // given
        Position current = new Position(0, 3);
        EnumSet<Direction> directions = EnumSet.allOf(Direction.class);
        directions.remove(Direction.NONE);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Path> pathList = new ArrayList<>();
        paths.forEach(pathList::add);
        assertThat(pathList).hasSize(3);
    }

    @DisplayName("궁성의 변의 중앙인 경우, 대각선을 포함하지 않은 3개의 경로를 생성한다.")
    @Test
    void 궁성_변_중앙_경로_생성_테스트() {
        // given
        Position current = new Position(0, 4);
        EnumSet<Direction> directions = EnumSet.allOf(Direction.class);
        directions.remove(Direction.NONE);

        // when
        Paths paths = strategy.findMovablePaths(current, directions);

        // then
        List<Path> pathList = new ArrayList<>();
        paths.forEach(pathList::add);
        assertThat(pathList).hasSize(3);
    }
}
