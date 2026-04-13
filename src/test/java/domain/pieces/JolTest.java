package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import testUtil.BoardTestUtil;

class JolTest {

    @DisplayName("졸 이동 초나라 - 좌,우,위 좌표 반환")
    @Test
    void 졸_이동_좌우위_초나라_정상_테스트() {
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(4, 3);
        PieceFinder finder = BoardTestUtil.createPieceFinder();

        List<Position> expected = List.of(
                Position.create(5, 3),
                Position.create(4, 2),
                Position.create(4, 4)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 한나라 - 좌,우,위 좌표 반환")
    @Test
    void 졸_이동_좌우위_한나라_정상_테스트() {
        Jol jol = new Jol(Country.HAN);
        Position start = Position.create(7, 3);
        PieceFinder finder = BoardTestUtil.createPieceFinder();

        List<Position> expected = List.of(
                Position.create(6, 3),
                Position.create(7, 2),
                Position.create(7, 4)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 한나라 - 좌우위에서 한칸 막혔을 때")
    @Test
    void 졸_한칸막혔을때_이동_한나라_정상_테스트() {
        Jol jol = new Jol(Country.HAN);
        Position start = Position.create(7, 1);
        PieceFinder finder = BoardTestUtil.createPieceFinder();

        List<Position> expected = List.of(
                Position.create(6, 1),
                Position.create(7, 2)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 초나라 - 좌우위에서 한칸 막혔을 때")
    @Test
    void 졸_한칸막혔을때_이동_초나라_정상_테스트() {
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(10, 1);
        PieceFinder finder = BoardTestUtil.createPieceFinder();

        List<Position> expected = List.of(
                Position.create(10, 2)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 초나라 - 궁성 안 테스트")
    @Test
    void 졸_이동_궁성_초나라_정상_테스트() {
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(8, 6);
        PieceFinder finder = BoardTestUtil.createPieceFinder();

        List<Position> expected = List.of(
                Position.create(8, 5),
                Position.create(8, 7),
                Position.create(9, 6),
                Position.create(9, 5)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 한나라 - 궁성안 테스트")
    @Test
    void 졸_이동_궁성_한나라_정상_테스트() {
        Jol jol = new Jol(Country.HAN);
        Position start = Position.create(3, 4);
        PieceFinder finder = BoardTestUtil.createPieceFinder();

        List<Position> expected = List.of(
                Position.create(3, 3),
                Position.create(3, 5),
                Position.create(2, 4),
                Position.create(2, 5)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 - 아군 위치로 이동 불가")
    @Test
    void 졸_아군_막힘() {
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(4, 3);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(5, 3))) {
                return new Jol(Country.CHO); // 아군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(4, 2),
                Position.create(4, 4)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 - 적군 잡기 가능")
    @Test
    void 졸_적군_잡기() {
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(4, 3);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(5, 3))) {
                return new Jol(Country.HAN); // 적군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(5, 3), // 적 포함
                Position.create(4, 2),
                Position.create(4, 4)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 - 궁성에서 아군/적군 처리")
    @Test
    void 졸_궁성_아군적군_처리() {
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(8, 6);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(9, 6))) {
                return new Jol(Country.CHO); // 아군 → 막힘
            }
            if (position.equals(Position.create(9, 5))) {
                return new Jol(Country.HAN); // 적군 → 가능
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(8, 5),
                Position.create(8, 7),
                Position.create(9, 5) // 적만 포함
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}