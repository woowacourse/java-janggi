package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;


class PoTest {

    @DisplayName("포 이동 - 경로에 기물없으면 이동 불가")
    @Test
    void 포_경로에_기물없으면_이동불가() {
        Po po = new Po(Country.CHO);
        Position start = Position.create(3, 2);

        PieceFinder finder = position -> None.INSTANCE;

        List<Position> result = po.getAvailableRoute(start, finder);
        assertThat(result).isEmpty();
    }

    @DisplayName("포 이동 - 기뭏 하나가 있어야 이동 가능")
    @Test
    void 포_하나_건너서_이동() {
        Po po = new Po(Country.CHO);
        Position start = Position.create(3, 2);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(5,2))) {
                return new Jol(Country.CHO); // 점프 대상
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(6,2),
                Position.create(7,2),
                Position.create(8,2),
                Position.create(9,2),
                Position.create(10,2)
        );

        List<Position> result = po.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("포 이동 - 적을 만나면 잡고 멈춤")
    @Test
    void 포_적군_잡기() {
        Po po = new Po(Country.CHO);
        Position start = Position.create(3, 2);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(4,2))) {
                return new Jol(Country.CHO); // 점프 대상
            }
            if (position.equals(Position.create(6,2))) {
                return new Jol(Country.HAN); // 적군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(5,2),
                Position.create(6,2) // 적 포함
        );

        List<Position> result = po.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("포 이동 - 아군 위치로 이동 불가")
    @Test
    void 포_아군에_막힘() {
        Po po = new Po(Country.CHO);
        Position start = Position.create(3, 2);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(4,2))) {
                return new Jol(Country.CHO); // 점프 대상
            }
            if (position.equals(Position.create(6,2))) {
                return new Jol(Country.CHO); // 아군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(5,2)
        );

        List<Position> result = po.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("포 이동 - 포는 포를 못 넘는다")
    @Test
    void 포는_포를_넘을수없다() {
        Po po = new Po(Country.CHO);
        Position start = Position.create(3, 2);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(4,2))) {
                return new Po(Country.HAN); // 포
            }
            return None.INSTANCE;
        };

        List<Position> result = po.getAvailableRoute(start, finder);
        assertThat(result).isEmpty();
    }
}