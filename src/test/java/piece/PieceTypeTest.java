package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static testUtil.TestConstant.RANK_1;
import static testUtil.TestConstant.RANK_10;
import static testUtil.TestConstant.RANK_2;
import static testUtil.TestConstant.RANK_3;
import static testUtil.TestConstant.RANK_4;
import static testUtil.TestConstant.RANK_5;
import static testUtil.TestConstant.RANK_6;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import position.Path;
import position.Position;
import position.PositionFile;

class PieceTypeTest {

    @Test
    void 기물_표기_이름을_반환한다() {
        assertThat(PieceType.GENERAL.getDisplayName()).isEqualTo("장");
        assertThat(PieceType.HORSE.getDisplayName()).isEqualTo("마");
    }

    @Nested
    @DisplayName("getMovablePaths 테스트")
    class GetMovablePathsTest {

        @Test
        void 차는_십자_모양으로_이동할_수_있다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.ROOK.getMovablePaths(current);

            assertThat(paths).anyMatch(path -> path.finalPosition().equals(new Position(PositionFile.FILE_5, RANK_1)));
            assertThat(paths).anyMatch(path -> path.finalPosition().equals(new Position(PositionFile.FILE_1, RANK_5)));
        }

        @Test
        void 포는_십자_무한_이동_가능하다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.CANNON.getMovablePaths(current);

            assertThat(paths).anyMatch(path -> path.finalPosition().equals(new Position(PositionFile.FILE_5, RANK_10)));
            assertThat(paths).anyMatch(path -> path.finalPosition().equals(new Position(PositionFile.FILE_9, RANK_5)));
        }

        @Test
        void 말은_두_칸_이동_경로를_가진다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.HORSE.getMovablePaths(current);

            assertThat(paths).anySatisfy(path ->
                    assertThat(path.pathPositions()).containsExactly(
                            new Position(PositionFile.FILE_5, RANK_5),
                            new Position(PositionFile.FILE_5, RANK_4),
                            new Position(PositionFile.FILE_4, RANK_3)
                    )
            );
        }

        @Test
        void 상은_세_칸_이동_경로를_가진다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.ELEPHANT.getMovablePaths(current);

            assertThat(paths).anySatisfy(path ->
                    assertThat(path.pathPositions()).containsExactly(
                            new Position(PositionFile.FILE_5, RANK_5),
                            new Position(PositionFile.FILE_5, RANK_4),
                            new Position(PositionFile.FILE_4, RANK_3),
                            new Position(PositionFile.FILE_3, RANK_2)
                    )
            );
        }

        @Test
        void 졸은_위_왼_오_한칸_이동한다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.CHO_SOLDIER.getMovablePaths(current);

            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_5, RANK_6))
            );
            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_4, RANK_5))
            );
            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_6, RANK_5))
            );
        }

        @Test
        void 병는_아래_왼_오_한칸_이동한다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.HAN_SOLDIER.getMovablePaths(current);

            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_5, RANK_4))
            );
            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_4, RANK_5))
            );
            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_6, RANK_5))
            );
        }

        @Test
        void 사는_십자_한칸_이동이_가능하다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.GUARD.getMovablePaths(current);

            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_5, RANK_6))
            );
            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_5, RANK_4))
            );
        }

        @Test
        void 장은_십자_한칸_이동이_가능하다() {
            Position current = new Position(PositionFile.FILE_5, RANK_5);
            List<Path> paths = PieceType.GENERAL.getMovablePaths(current);

            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_5, RANK_6))
            );
            assertThat(paths).anySatisfy(path ->
                    assertThat(path.finalPosition()).isEqualTo(new Position(PositionFile.FILE_4, RANK_5))
            );
        }
    }
}
