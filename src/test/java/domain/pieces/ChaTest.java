package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

class ChaTest {

    @DisplayName("차 이동 초나라 - 상하좌우 좌표 반환")
    @Test
    void 차_이동_상하좌우_초나라_정상_테스트(){
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(1,1);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(2,1),
                Position.create(3,1),
                Position.create(4,1),
                Position.create(5,1),
                Position.create(6,1),
                Position.create(7,1),
                Position.create(8,1),
                Position.create(9,1),
                Position.create(10,1),
                Position.create(1,2),
                Position.create(1,3),
                Position.create(1,4),
                Position.create(1,5),
                Position.create(1,6),
                Position.create(1,7),
                Position.create(1,8),
                Position.create(1,9)
        );

        List<Position> result = cha.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("차 이동 초나라 - 임의위치에서 상하좌우 좌표 반환")
    @Test
    void 차_이동_임의위치_초나라_정상_테스트(){
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(2,2);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(1,2),
                Position.create(3,2),
                Position.create(4,2),
                Position.create(5,2),
                Position.create(6,2),
                Position.create(7,2),
                Position.create(8,2),
                Position.create(9,2),
                Position.create(10,2),
                Position.create(2,1),
                Position.create(2,3),
                Position.create(2,4),
                Position.create(2,5),
                Position.create(2,6),
                Position.create(2,7),
                Position.create(2,8),
                Position.create(2,9)
        );

        List<Position> result = cha.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("차 이동 - 아군에 막히면 그 전까지만 이동")
    @Test
    void 차_아군에_막힘() {
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(1, 1);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(1, 4))) {
                return new Jol(Country.CHO); // 아군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(2,1),
                Position.create(3,1),
                Position.create(4,1),
                Position.create(5,1),
                Position.create(6,1),
                Position.create(7,1),
                Position.create(8,1),
                Position.create(9,1),
                Position.create(10,1),

                Position.create(1,2),
                Position.create(1,3) // (1,4)에서 막힘
        );

        List<Position> result = cha.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("차 이동 - 적을 만나면 잡고 멈춤")
    @Test
    void 차_적군_잡고_정지() {
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(1, 1);

        PieceFinder finder = position -> {
            if (position.equals(Position.create(1, 4))) {
                return new Jol(Country.HAN); // 적군
            }
            return None.INSTANCE;
        };

        List<Position> expected = List.of(
                Position.create(2,1),
                Position.create(3,1),
                Position.create(4,1),
                Position.create(5,1),
                Position.create(6,1),
                Position.create(7,1),
                Position.create(8,1),
                Position.create(9,1),
                Position.create(10,1),

                Position.create(1,2),
                Position.create(1,3),
                Position.create(1,4) // 적 포함
        );

        List<Position> result = cha.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("차 이동 - 궁성 안에서 대각선 이동 가능")
    @Test
    void 차_궁성_대각선_이동() {
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(8, 6); // 궁 중앙

        PieceFinder finder = position -> None.INSTANCE;

        List<Position> expected = List.of(
                // 기존 직선 이동
                Position.create(7,6),
                Position.create(6,6),
                Position.create(5,6),
                Position.create(4,6),
                Position.create(3,6),
                Position.create(2,6),
                Position.create(1,6),

                Position.create(9,6),
                Position.create(10,6),

                Position.create(8,5),
                Position.create(8,4),
                Position.create(8,3),
                Position.create(8,2),
                Position.create(8,1),

                Position.create(8,7),
                Position.create(8,8),
                Position.create(8,9),

                // 궁성 대각선 (핵심)
                Position.create(9,5),
                Position.create(10,4)
        );

        List<Position> result = cha.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }
}