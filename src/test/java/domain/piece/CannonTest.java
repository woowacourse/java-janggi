package domain.piece;

import domain.Offset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CannonTest {

    private Piece cannon;

    @BeforeEach
    void setUp() {
        cannon = new Cannon(Team.CHO);
    }

    @Test
    void 포는_왼쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(-3, 0);

        List<Offset> pathPositions = cannon.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 0)));
    }

    @Test
    void 포는_오른쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        List<Offset> pathPositions = cannon.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 포는_위쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, 5);

        List<Offset> pathPositions = cannon.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(
                List.of(new Offset(0, 1),
                        new Offset(0, 2),
                        new Offset(0, 3),
                        new Offset(0, 4))
        );
    }


    @Test
    void 포는_아래쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, -6);

        List<Offset> pathPositions = cannon.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(0, -1),
                        new Offset(0, -2),
                        new Offset(0, -3),
                        new Offset(0, -4),
                        new Offset(0, -5))
        );
    }


    @Test
    void 포는_기물이_사이에_하나의_기물이_있으면_정상적으로_움직일_수_있다() {
        List<Piece> blockedPieces = List.of(new Horse(Team.HAN));

        assertDoesNotThrow(() -> cannon.validateMove(blockedPieces, Optional.of(new Horse(Team.HAN))));
    }


    @Test
    void 포는_기물이_사이에_하나라도_존재하지_않으면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of();

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces, Optional.of(new Horse(Team.HAN)))
        );
    }

    @Test
    void 포는_기물이_사이에_두개_이상_존재하면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of(new Horse(Team.HAN), new Horse(Team.HAN));

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces, Optional.of(new Horse(Team.HAN)))
        );
    }

    @Test
    void 포는_기물이_사이에_포가_존재하면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of(new Cannon(Team.HAN));

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces, Optional.of(new Horse(Team.HAN)))
        );
    }

    @Test
    void 포는_목적지에_포가_존재하면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of(new Horse(Team.HAN));

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces, Optional.of(new Cannon(Team.HAN)))
        );
    }


}
