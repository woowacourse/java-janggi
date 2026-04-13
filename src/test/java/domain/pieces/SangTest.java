package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

class SangTest {

    @DisplayName("상 이동 초나라 - 상하좌추 정상테스트")
    @Test
    void 상_이동_상하좌우_초나라_정상_테스트(){
        Sang sang = new Sang(Country.CHO);
        Position start = Position.create(4,4);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(1,2),
                Position.create(1,6),
                Position.create(2,7),
                Position.create(6,7),
                Position.create(7,6),
                Position.create(7,2),
                Position.create(2,1),
                Position.create(6,1)
        );

        List<Position> result = sang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("상 이동 - 경로가 막히면 이동 불가")
    @Test
    void 상_경로_막힘_이동불가() {
        Sang sang = new Sang(Country.CHO);
        Position start = Position.create(4, 4);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(3,4))) {
                return new Jol(Country.CHO);
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(2,7),
                Position.create(6,7),
                Position.create(7,6),
                Position.create(7,2),
                Position.create(2,1),
                Position.create(6,1)
        );

        List<Position> result = sang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("상 이동 - 아군 위치로 이동 불가")
    @Test
    void 상_아군_위치_이동불가() {
        Sang sang = new Sang(Country.CHO);
        Position start = Position.create(4, 4);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(1,2))) {
                return new Jol(Country.CHO); // 아군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(1,6),
                Position.create(2,7),
                Position.create(6,7),
                Position.create(7,6),
                Position.create(7,2),
                Position.create(2,1),
                Position.create(6,1)
        );

        List<Position> result = sang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("상 이동 - 적을 만나면 잡을 수 있음")
    @Test
    void 상_적군_잡기() {
        Sang sang = new Sang(Country.CHO);
        Position start = Position.create(4, 4);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(1,2))) {
                return new Jol(Country.HAN); // 적군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(1,2), // 적 포함
                Position.create(1,6),
                Position.create(2,7),
                Position.create(6,7),
                Position.create(7,6),
                Position.create(7,2),
                Position.create(2,1),
                Position.create(6,1)
        );

        List<Position> result = sang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}