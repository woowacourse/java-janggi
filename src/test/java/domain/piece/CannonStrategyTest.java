package domain.piece;

import domain.Path;
import domain.board.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CannonStrategyTest {

    Piece piece;

    @BeforeEach
    void setUp() {
        piece = new Cannon(Team.CHO);
    }

    @Test
    void 포는_기물이_사이에_하나의_기물이_있으면_정상적으로_움직일_수_있다() {
        List<Path> paths = List.of(new Path(new Position(3, 0), new Horse(Team.HAN)));

        assertDoesNotThrow(() -> piece.validateMove(paths, new Horse(Team.HAN)));
    }


    @Test
    void 포는_기물이_사이에_하나라도_존재하지_않으면_예외를_반환한다() {
        List<Path> paths = List.of();

        assertThrows(IllegalStateException.class,
                () -> piece.validateMove(paths, new Horse(Team.HAN))
        );
    }

    @Test
    void 포는_기물이_사이에_두개_이상_존재하면_예외를_반환한다() {
        List<Path> paths = List.of(
                new Path(new Position(3, 0), new Horse(Team.HAN)),
                new Path(new Position(6, 0), new Horse(Team.HAN))
        );


        assertThrows(IllegalStateException.class,
                () -> piece.validateMove(paths, new Horse(Team.HAN))
        );
    }

    @Test
    void 포는_기물이_사이에_포가_존재하면_예외를_반환한다() {
        List<Path> paths = List.of(
                new Path(new Position(3, 0), new Cannon(Team.HAN))
        );

        assertThrows(IllegalStateException.class,
                () -> piece.validateMove(paths, new Horse(Team.HAN))
        );
    }

    @Test
    void 포는_목적지에_포가_존재하면_예외를_반환한다() {
        List<Path> paths = List.of(
                new Path(new Position(3, 0), new Horse(Team.HAN))
        );

        assertThrows(IllegalStateException.class,
                () -> piece.validateMove(paths, new Cannon(Team.HAN))
        );
    }

}
