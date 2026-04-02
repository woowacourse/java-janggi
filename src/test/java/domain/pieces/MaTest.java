package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import domain.Position;
import domain.enums.Country;

class MaTest {

    @Test
    void 마_이동_좌우위_초나라_정상_테스트(){
        Ma ma = new Ma(Country.CHO);
        Position start = Position.create(4,3);

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

        List<Position> result = ma.getAvailableRoute(start);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}