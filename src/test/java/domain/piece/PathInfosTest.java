package domain.piece;

import domain.board.Position;
import domain.path.PathInfo;
import domain.path.PathInfos;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathInfosTest {

    @Nested
    class 한_칸_이동_검증 {
        @Test
        void 경로가_한_칸이면_예외가_발생하지_않는다() {
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), null)
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatCode(pathInfos::validateOnlyOneStep)
                    .doesNotThrowAnyException();
        }

        @Test
        void 경로가_두_칸_이상이면_예외가_발생한다() {
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), null),
                    new PathInfo(new Position(4, 6), null)
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatThrownBy(pathInfos::validateOnlyOneStep)
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    class 경로상_기물_검증 {
        @Test
        void 도착지에만_기물이_있으면_예외가_발생하지_않는다() {
            Position destination = new Position(4, 6);
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), null),
                    new PathInfo(destination, Piece.of(Camp.HAN, PieceType.CHARIOT))
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatCode(() -> pathInfos.validateNoBlockingPiece(destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 도착지_이전_경로에_기물이_있으면_예외가_발생한다() {
            Position destination = new Position(4, 6);
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.SOLDIER)),
                    new PathInfo(destination, null)
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatThrownBy(() -> pathInfos.validateNoBlockingPiece(destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 포_이동_검증 {
        @Test
        void 경로에_정확히_하나의_기물이_있고_포가_아니면_예외가_발생하지_않는다() {
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.SOLDIER)),
                    new PathInfo(new Position(4, 6), null)
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatCode(() -> pathInfos.validateHasBlockingPiece(new Position(4, 6)))
                    .doesNotThrowAnyException();
        }

        @Test
        void 경로가_길어도_도착지_이전의_기물이_하나면_예외가_발생하지_않는다() {
            Position destination = new Position(4, 8);
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), null),
                    new PathInfo(new Position(4, 6), Piece.of(Camp.CHO, PieceType.SOLDIER)),
                    new PathInfo(new Position(4, 7), null),
                    new PathInfo(destination, null)
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatCode(() -> pathInfos.validateHasBlockingPiece(destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 도착지_이전_경로에_기물이_없으면_예외가_발생한다() {
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.SOLDIER))
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatThrownBy(() -> pathInfos.validateHasBlockingPiece(new Position(4, 5)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 경로에_포가_포함되어_있으면_예외가_발생한다() {
            List<PathInfo> fakePathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.CANNON)),
                    new PathInfo(new Position(4, 6), null)
            );

            PathInfos pathInfos = new PathInfos(fakePathInfos);
            assertThatThrownBy(() -> pathInfos.validateHasBlockingPiece(new Position(4, 6)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
