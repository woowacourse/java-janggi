package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

class SangTest {

    @Test
    void 상_이동_좌우위_초나라_정상_테스트(){
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

}