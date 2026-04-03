package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;

class SangTest {

    @Test
    void 상_이동_좌우위_초나라_정상_테스트(){
        Sang sang = new Sang(Country.CHO);
        Position start = Position.create(4,4);

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

        List<Position> result = sang.getAvailableRoute(start, Direction.UP);
        result.addAll(sang.getAvailableRoute(start, Direction.DOWN)) ;
        result.addAll(sang.getAvailableRoute(start, Direction.LEFT)) ;
        result.addAll(sang.getAvailableRoute(start, Direction.RIGHT)) ;
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}