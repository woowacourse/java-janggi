package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

class SaTest {

    @DisplayName("사 이동 초나라 - 궁성안에서 상화좌우 대각 이동 가능")
    @Test
    void 사_이동_중앙_초나라_정상_테스트() {
        Sa sa = new Sa(Country.CHO);
        Position start = Position.create(2, 5);

        PieceFinder finder = position -> None.INSTANCE;

        List<Position> expected = List.of(
                Position.create(3,5),
                Position.create(1,5),
                Position.create(2,4),
                Position.create(2,6),
                Position.create(1,4),
                Position.create(1,6),
                Position.create(3,6),
                Position.create(3,4)
        );

        List<Position> result = sa.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("사 이동 한나라 - 궁성안에서 상화좌우 대각 이동 가능")
    @Test
    void 사_이동_한나라_정상_테스트() {
        Sa sa = new Sa(Country.HAN);
        Position start = Position.create(9, 5);

        PieceFinder finder = position -> None.INSTANCE;

        List<Position> expected = List.of(
                Position.create(10,5),
                Position.create(8,5),
                Position.create(9,4),
                Position.create(9,6),
                Position.create(10,4),
                Position.create(10,6),
                Position.create(8,6),
                Position.create(8,4)
        );

        List<Position> result = sa.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("사 이동 - 궁성 밖으로 이동 불가")
    @Test
    void 사_궁성_밖_이동불가() {
        Sa sa = new Sa(Country.CHO);
        Position start = Position.create(1, 4); // 궁 모서리

        PieceFinder finder = position -> None.INSTANCE;

        List<Position> expected = List.of(
                Position.create(1,5),
                Position.create(2,4),
                Position.create(2,5)
        );

        List<Position> result = sa.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("사 이동 - 아군 위치로 이동 불가")
    @Test
    void 사_아군_위치_이동불가() {
        Sa sa = new Sa(Country.CHO);
        Position start = Position.create(2, 5);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(3,5))) {
                return new Jol(Country.CHO); // 아군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(1,5),
                Position.create(2,4),
                Position.create(2,6),
                Position.create(1,4),
                Position.create(1,6),
                Position.create(3,6),
                Position.create(3,4)
        );

        List<Position> result = sa.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("사 이동 - 적을 만나면 잡을 수 있음")
    @Test
    void 사_적군_잡기() {
        Sa sa = new Sa(Country.CHO);
        Position start = Position.create(2, 5);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(3,5))) {
                return new Jol(Country.HAN); // 적군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(3,5), // 적 포함
                Position.create(1,5),
                Position.create(2,4),
                Position.create(2,6),
                Position.create(1,4),
                Position.create(1,6),
                Position.create(3,6),
                Position.create(3,4)
        );

        List<Position> result = sa.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}