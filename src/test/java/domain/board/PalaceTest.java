package domain.board;

import domain.Offset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PalaceTest {
    @Test
    void 출발지와_목적지가_대각선_양코너인_경우_유효하다() {
        Position from = new Position(3, 0);
        Position to = new Position(5, 2);

        Assertions.assertDoesNotThrow(() -> Palace.CHO.validateDiagonalMoveRule(from, to));
    }

    @Test
    void 출발지가_코너이고_목적지가_센터인_경우_유효하다() {
        Position from = new Position(5, 9);
        Position to = new Position(4, 8);

        Assertions.assertDoesNotThrow(() -> Palace.HAN.validateDiagonalMoveRule(from, to));
    }

    @Test
    void 출발지가_센터이고_목적지가_코너인_경우_유효하다() {
        Position from = new Position(4, 8);
        Position to = new Position(5, 9);

        Assertions.assertDoesNotThrow(() -> Palace.HAN.validateDiagonalMoveRule(from, to));
    }

    @Test
    void 출발지가_궁성_밖이면_실패한다() {
        Position from = new Position(2, 0);
        Position to = new Position(3, 1);
        assertThrows(IllegalStateException.class, () -> Palace.CHO.validateDiagonalMoveRule(from, to));
    }

    @Test
    void 목적지가_궁성_밖이면_실패한다() {
        Position from = new Position(3, 0);
        Position to = new Position(6, 2);
        assertThrows(IllegalStateException.class, () -> Palace.CHO.validateDiagonalMoveRule(from, to));
    }


    @Test
    void 대각선_방향으로_가는_경로가_아니라면_유효하지_않다() {
        Position from = new Position(3, 0);
        Position to = new Position(5, 1);
        assertThrows(IllegalStateException.class, () -> Palace.CHO.validateDiagonalMoveRule(from, to));
    }

    @Test
    void 변에는_대각선_이동이_없다() {
        Position from = new Position(4, 9);
        Position to = new Position(5, 8);

        assertThrows(IllegalStateException.class, () -> Palace.CHO.validateDiagonalMoveRule(from, to));
    }


    @Test
    void 출발지와_목적지가_대각선_양코너인_경우_중앙을_경유한다() {
        Position from = new Position(3, 0);
        Position to = new Position(5, 2);

        List<Offset> offsets = Palace.CHO.generatePaths(from, to);
        assertThat(offsets).isEqualTo(List.of(new Offset(1, 1)));
    }

    @Test
    void 출발지가_코너이고_목적지가_센터인_경우_경유하는_곳은_없다() {
        Position from = new Position(5, 9);
        Position to = new Position(4, 8);

        List<Offset> offsets = Palace.HAN.generatePaths(from, to);
        assertThat(offsets).isEqualTo(List.of());
    }

    @Test
    void 출발지가_센터이고_목적지가_코너인_경우_경유하는_곳은_없으며_성공한다() {
        Position from = new Position(4, 8);
        Position to = new Position(5, 9);

        List<Offset> offsets = Palace.HAN.generatePaths(from, to);
        assertThat(offsets).isEqualTo(List.of());
    }
}
