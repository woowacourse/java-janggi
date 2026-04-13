package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

class MaTest {

    @DisplayName("마 이동 - 상하좌우 이동 테스트")
    @Test
    void 마_이동_상하좌우_초나라_정상_테스트(){
        Ma ma = new Ma(Country.CHO);
        Position start = Position.create(4,3);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(2,4),
                Position.create(2,2),
                Position.create(6,2),
                Position.create(6,4),
                Position.create(3,1),
                Position.create(5,1),
                Position.create(3,5),
                Position.create(5,5)
        );

        List<Position> result = ma.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("마 이동 - 가는 경로가 막히면 이동 불가")
    @Test
    void 마_길막힘_이동불가() {
        Ma ma = new Ma(Country.CHO);
        Position start = Position.create(4, 3);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(3,3))) {
                return new Jol(Country.CHO);
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(6,2),
                Position.create(6,4),
                Position.create(3,1),
                Position.create(5,1),
                Position.create(3,5),
                Position.create(5,5)
        );

        List<Position> result = ma.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("마 이동 - 아군 위치로 이동 불가")
    @Test
    void 마_아군_위치_이동불가() {
        Ma ma = new Ma(Country.CHO);
        Position start = Position.create(4, 3);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(2,4))) {
                return new Jol(Country.CHO); // 아군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(2,2),
                Position.create(6,2),
                Position.create(6,4),
                Position.create(3,1),
                Position.create(5,1),
                Position.create(3,5),
                Position.create(5,5)
        );

        List<Position> result = ma.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("마 이동 - 적을 만나면 잡을 수 있음")
    @Test
    void 마_적군_잡기() {
        Ma ma = new Ma(Country.CHO);
        Position start = Position.create(4, 3);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(2,4))) {
                return new Jol(Country.HAN); // 적군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(2,4), // 적 포함
                Position.create(2,2),
                Position.create(6,2),
                Position.create(6,4),
                Position.create(3,1),
                Position.create(5,1),
                Position.create(3,5),
                Position.create(5,5)
        );

        List<Position> result = ma.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}