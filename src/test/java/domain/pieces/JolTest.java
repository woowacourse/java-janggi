package domain.pieces;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JolTest {

    @Test
    void 졸_이동_좌우위_초나라_정상_테스트(){
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(4,3);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(5,3),
                Position.create(4,2),
                Position.create(4,4)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 졸_이동_좌우위_한나라_정상_테스트(){
        Jol jol = new Jol(Country.HAN);
        Position start = Position.create(7,3);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(6,3),
                Position.create(7,2),
                Position.create(7,4)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("졸 이동 한나라 - 좌우위에서 한칸 막혔을 때")
    @Test
    void 졸_한칸막혔을때_이동_한나라_정상_테스트(){
        Jol jol = new Jol(Country.HAN);
        Position start = Position.create(7,1);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(6,1),
                Position.create(7,2)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 졸_이동_궁성_초나라_정상_테스트(){
        Jol jol = new Jol(Country.CHO);
        Position start = Position.create(8,6);
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };

        List<Position> expected = List.of(
                Position.create(8,5),
                Position.create(8,7),
                Position.create(9,6),
                Position.create(9,5)
        );

        List<Position> result = jol.getAvailableRoute(start, finder);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

}