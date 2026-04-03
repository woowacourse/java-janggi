package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;

class ChaTest {

    @DisplayName("처음 위치에서 차 이동 규칙에 의해 갈 수 있는 모든 좌표 반환")
    @Test
    void 차_이동_좌우위아래_초나라_정상_테스트(){
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(1,1);

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

        List<Position> result = cha.getAvailableRoute(start, Direction.UP);
        result.addAll(cha.getAvailableRoute(start, Direction.DOWN)) ;
        result.addAll(cha.getAvailableRoute(start, Direction.LEFT)) ;
        result.addAll(cha.getAvailableRoute(start, Direction.RIGHT)) ;
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("임의의 위치에서 차 이동 규칙에 의해 갈 수 있는 모든 좌표 반환")
    @Test
    void 차_이동_임의위치_초나라_정상_테스트(){
        Cha cha = new Cha(Country.CHO);
        Position start = Position.create(2,2);

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

        List<Position> result = cha.getAvailableRoute(start, Direction.UP);
        result.addAll(cha.getAvailableRoute(start, Direction.DOWN)) ;
        result.addAll(cha.getAvailableRoute(start, Direction.LEFT)) ;
        result.addAll(cha.getAvailableRoute(start, Direction.RIGHT)) ;
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }
}