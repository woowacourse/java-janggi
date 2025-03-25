package position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static position.PositionFile.FILE_1;
import static position.PositionFile.FILE_5;
import static position.PositionFile.FILE_8;
import static testUtil.TestConstant.RANK_1;
import static testUtil.TestConstant.RANK_2;
import static testUtil.TestConstant.RANK_3;
import static testUtil.TestConstant.RANK_4;
import static testUtil.TestConstant.RANK_5;
import static testUtil.TestConstant.RANK_7;
import static testUtil.TestConstant.RANK_9;

import java.util.List;
import org.junit.jupiter.api.Test;
import piece.MoveDirection;
import piece.Piece;
import piece.PieceType;

class PathTest {


    @Test
    void 현재_위치에서_이동_방향을_통해_경로를_구할_수_있다() {
        // given
        final Position startPosition = new Position(PositionFile.FILE_5, RANK_5);

        // when
        final List<Path> result = Path.getMovablePaths(startPosition,
                List.of(MoveDirection.DOWN, MoveDirection.DOWN_LEFT));

        // then
        assertThat(result).containsExactlyInAnyOrder(new Path(
                new Position(PositionFile.FILE_4, RANK_3),
                List.of(new Position(PositionFile.FILE_5, RANK_5), new Position(PositionFile.FILE_5, RANK_4),
                        new Position(PositionFile.FILE_4, RANK_3))
        ));
    }

    @Test
    void 중간에_마주치는_기물들을_반환할_수_있다() {
        // given
        final Path path = new Path(new Position(FILE_5, RANK_5), List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final List<Piece> result = path.getEncounteredMiddlePieces(List.of(
                new Piece(new Position(FILE_5, RANK_2), PieceType.HORSE),
                new Piece(new Position(FILE_5, RANK_3), PieceType.HORSE),
                new Piece(new Position(FILE_8, RANK_7), PieceType.CHO_SOLDIER),
                new Piece(new Position(FILE_8, RANK_7), PieceType.CHO_SOLDIER)
        ));

        // then
        assertAll(
                () -> assertThat(result.getFirst().getPosition()).isEqualTo(new Position(FILE_5, RANK_2)),
                () -> assertThat(result.get(1).getPosition()).isEqualTo(new Position(FILE_5, RANK_3))
        );
    }

    @Test
    void 마지막에_기물을_마주치는지_반환할_수_있다() {
        // given
        final Path path = new Path(new Position(FILE_5, RANK_5), List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isEncounteredLast(List.of(
                new Piece(new Position(FILE_5, RANK_9), PieceType.GENERAL),
                new Piece(new Position(FILE_8, RANK_7), PieceType.ELEPHANT),
                new Piece(new Position(FILE_5, RANK_5), PieceType.CHO_SOLDIER)
        ));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 마지막에_기물을_마주치지_않으면_false를_반환한다() {
        // given
        final Path path = new Path(new Position(FILE_5, RANK_5), List.of(
                new Position(FILE_5, RANK_1),
                new Position(FILE_5, RANK_2),
                new Position(FILE_5, RANK_3),
                new Position(FILE_5, RANK_4),
                new Position(FILE_5, RANK_5)
        ));

        // when
        final boolean result = path.isEncounteredLast(List.of(
                new Piece(new Position(FILE_1, RANK_9), PieceType.GENERAL),
                new Piece(new Position(FILE_1, RANK_7), PieceType.ELEPHANT),
                new Piece(new Position(FILE_1, RANK_5), PieceType.CHO_SOLDIER)
        ));

        // then
        assertThat(result).isFalse();
    }
}
