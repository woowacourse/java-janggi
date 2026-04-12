package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

class JangTest {

    @DisplayName("장 이동 초나라- 상하좌우 이동 가능")
    @Test
    void 장_이동_초나라_이동_정상_테스트(){
        Jang jang = new Jang(Country.CHO);
        Position start = Position.create(2,5);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

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

        List<Position> result = jang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("장 이동 한나라 - 상하좌우 이동 가능")
    @Test
    void 장_이동_한나라_이동_정상_테스트(){
        Jang jang = new Jang(Country.HAN);
        Position start = Position.create(9,5);

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

        List<Position> result = jang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }


    @DisplayName("장 이동 - 궁성 밖으로 이동 불가")
    @Test
    void 장_궁성_밖으로_이동_불가() {
        Jang jang = new Jang(Country.CHO);
        Position start = Position.create(1, 4); // 궁 모서리

        PieceFinder finder = position -> None.INSTANCE;

        List<Position> expected = List.of(
                Position.create(1,5),
                Position.create(2,4),
                Position.create(2,5)
        );

        List<Position> result = jang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("장 이동 - 아군 위치로 이동 불가")
    @Test
    void 장_아군_위치로_이동_불가() {
        Jang jang = new Jang(Country.CHO);
        Position start = Position.create(2,5);

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

        List<Position> result = jang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("장 이동 - 적을 만나면 잡고 멈춤")
    @Test
    void 장_적군_잡기() {
        Jang jang = new Jang(Country.CHO);
        Position start = Position.create(2,5);

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

        List<Position> result = jang.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}