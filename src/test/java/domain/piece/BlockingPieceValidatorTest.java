package domain.piece;

import domain.board.Position;
import domain.path.PathInfo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlockingPieceValidatorTest {

    @Nested
    class 한_칸_이동_검증 {
        @Test
        void 경로가_한_칸이면_예외가_발생하지_않는다() {
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), null)
            );

            assertThatCode(() -> BlockingPieceValidator.validateOnlyOneStep(pathInfos))
                    .doesNotThrowAnyException();
        }

        @Test
        void 경로가_두_칸_이상이면_예외가_발생한다() {
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), null),
                    new PathInfo(new Position(4, 6), null)
            );

            assertThatThrownBy(() -> BlockingPieceValidator.validateOnlyOneStep(pathInfos))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    class 경로상_기물_검증 {
        @Test
        void 도착지에만_기물이_있으면_예외가_발생하지_않는다() {
            Position destination = new Position(4, 6);
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), null),
                    new PathInfo(destination, Piece.of(Camp.HAN, PieceType.CHARIOT))
            );

            assertThatCode(() -> BlockingPieceValidator.validateNoBlockingPiece(pathInfos, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 도착지_이전_경로에_기물이_있으면_예외가_발생한다() {
            Position destination = new Position(4, 6);
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.SOLDIER)),
                    new PathInfo(destination, null)
            );

            assertThatThrownBy(() -> BlockingPieceValidator.validateNoBlockingPiece(pathInfos, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 포_이동_검증 {
        @Test
        void 경로에_정확히_하나의_기물이_있고_포가_아니면_예외가_발생하지_않는다() {
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.SOLDIER)),
                    new PathInfo(new Position(4, 6), null)
            );

            assertThatCode(() -> BlockingPieceValidator.validateHasBlockingPiece(pathInfos))
                    .doesNotThrowAnyException();
        }

        @Test
        void 경로가_두_칸이_아니면_예외가_발생한다() {
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.SOLDIER))
            );

            assertThatThrownBy(() -> BlockingPieceValidator.validateHasBlockingPiece(pathInfos))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 경로에_포가_포함되어_있으면_예외가_발생한다() {
            List<PathInfo> pathInfos = List.of(
                    new PathInfo(new Position(4, 5), Piece.of(Camp.CHO, PieceType.CANNON)),
                    new PathInfo(new Position(4, 6), null)
            );

            assertThatThrownBy(() -> BlockingPieceValidator.validateHasBlockingPiece(pathInfos))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
