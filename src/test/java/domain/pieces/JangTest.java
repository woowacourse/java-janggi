package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;

class JangTest {

    @Test
    void 장_이동_초기위치_초나라_정상_테스트(){
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


//    @Test
//    void 장_이동_임의위치_궁상안_초나라_정상_테스트(){
//        Jang jang = new Jang(Country.CHO);
//        Position start = Position.create(3,5);
//
//        List<Position> expected = List.of(
//                Position.create(5,3),
//                Position.create(4,2),
//                Position.create(4,4)
//        );
//
//        List<Position> result = jang.getAvailableRoute(start);
//        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
//    }

}