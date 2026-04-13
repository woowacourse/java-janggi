package domain.board;

import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardTest {
    @Test
    @DisplayName("마(Horse)가 '日'자 모양으로 정상 이동한다.")
    void should_move_horse_successfully() {
        JanggiGenerator generator = new JanggiGenerator(Formation.ELEPHANT_HORSE_HORSE_ELEPHANT,
                Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);
        JanggiBoard board = new JanggiBoard(generator);

        Point start = new Point(0, 2);
        Point target = new Point(2, 3);

        Assertions.assertThatCode(() -> board.tryToMove(start, target))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("마(Horse)가 일직선으로 이동하려고 하면 경로 생성 실패로 차단된다.")
    void should_fail_when_horse_moves_straight() {
        JanggiGenerator generator = new JanggiGenerator(Formation.ELEPHANT_HORSE_HORSE_ELEPHANT,
                Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);
        JanggiBoard board = new JanggiBoard(generator);

        Point start = new Point(0, 2);
        Point target = new Point(0, 4);

        Assertions.assertThatThrownBy(() -> board.tryToMove(start, target))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상(Elephant)이 이동하는 중간 경로에 장애물이 있으면 이동이 차단된다.")
    void should_fail_when_elephant_path_is_blocked_by_obstacle() {
        JanggiGenerator generator = new JanggiGenerator(Formation.ELEPHANT_HORSE_HORSE_ELEPHANT,
                Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);
        JanggiBoard board = new JanggiBoard(generator);

        Point start = new Point(0, 1);
        Point target = new Point(3, 3);

        Point obstaclePoint = new Point(2, 2);
        Intersection obstacleIntersection = board.findIntersection(obstaclePoint);
        Intersection fakePiece = new Intersection(obstaclePoint, new Piece(Team.HAN, PieceType.SOLDIER));
        obstacleIntersection.arrive(fakePiece);

        Assertions.assertThatThrownBy(() -> board.tryToMove(start, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
    }

    @Test
    @DisplayName("포(Cannon)가 포를 넘어가려고 하면 예외가 발생한다.")
    void should_fail_when_cannon_jumps_over_cannon() {
        JanggiGenerator generator = new JanggiGenerator(Formation.ELEPHANT_HORSE_HORSE_ELEPHANT,
                Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);
        JanggiBoard board = new JanggiBoard(generator);

        Point start = new Point(2, 1);
        Point target = new Point(2, 5);

        Point obstaclePoint = new Point(2, 3);
        Intersection obstacleIntersection = board.findIntersection(obstaclePoint);
        Intersection fakePiece = new Intersection(obstaclePoint, new Piece(Team.HAN, PieceType.CANNON));
        obstacleIntersection.arrive(fakePiece);

        Assertions.assertThatThrownBy(() -> board.tryToMove(start, target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 넘어갈 수 없습니다.");
    }

    @Test
    @DisplayName("한나라 병(Soldier)이 뒤로 갈 때 이동이 차단된다.")
    void should_fail_when_soldier_moves_backward() {
        JanggiGenerator generator = new JanggiGenerator(Formation.ELEPHANT_HORSE_HORSE_ELEPHANT,
                Formation.ELEPHANT_HORSE_HORSE_ELEPHANT);
        JanggiBoard board = new JanggiBoard(generator);

        Point start = new Point(3, 0);
        Point backwards = new Point(2, 0);

        Assertions.assertThatThrownBy(() -> board.tryToMove(start, backwards))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
