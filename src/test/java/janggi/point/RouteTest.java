package janggi.point;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.game.Board;
import janggi.game.Team;
import janggi.piece.Byeong;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RouteTest {

    @Nested
    @DisplayName("이동 경로 테스트")
    class CheckTest {

        @Test
        @DisplayName("기물은 이동 경로에 다른 기물이 있으면 이동 시킬 수 없다.")
        void notMovePieceWithHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Ma(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 5);

            List<Point> path = List.of(new Point(5, 4));
            Route route = new Route(path, targetPoint);

            assertThatThrownBy(() -> route.validateRoute(startPoint, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("기물은 최종 위치에 같은 팀의 기물이 있으면 이동 시킬 수 없다.")
        void notMovePieceWithHurdleInTarget() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Ma(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            List<Point> path = List.of();
            Route route = new Route(path, targetPoint);

            assertThatThrownBy(() -> route.validateRoute(startPoint, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("포는 포를 제외한 하나의 기물이 없다면 이동할 수 없다.")
        void notMovePieceWithoutHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(new Point(6, 4), new Po(Team.CHO)));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            List<Point> path = List.of(new Point(5, 4));
            Route route = new Route(path, targetPoint);

            assertThatThrownBy(() -> route.validateRoute(startPoint, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 제외한 하나의 기물만 필요합니다.");
        }

        @Test
        @DisplayName("포는 포를 잡을 수 없다.")
        void notCatchPieceWithoutHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO),
                new Point(4, 4), new Po(Team.HAN)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            List<Point> path = List.of(new Point(5, 4));
            Route route = new Route(path, targetPoint);

            assertThatThrownBy(() -> route.validateRoute(startPoint, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
        }
    }
}
