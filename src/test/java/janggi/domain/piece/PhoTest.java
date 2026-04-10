package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.space.Blank;
import janggi.domain.board.Path;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Cha;
import janggi.domain.space.piece.Ma;
import janggi.domain.space.piece.Pho;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Sang;
import janggi.domain.space.piece.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class PhoTest {

    @Test
    void 포_움직임_정상_처리_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(1, 2);
        Position to = new Position(1, 4);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 포_움직임_예외_처리_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(1, 2);
        Position to = new Position(2, 2);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 포가 이동할 수 없습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Pho(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateArrival(space))
                .withMessage("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    @Test
    void 도착지에_상대_팀의_포가_있을_경우_예외_처리_테스트() {
        Piece piece = new Pho(Team.CHO);
        Space space = new Pho(Team.HAN);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateArrival(space))
                .withMessage("이동하려는 위치에 상대팀의 포가 존재합니다.");
    }

    @Test
    void 도착지가_빈칸인_경우_정상_테스트() {
        Piece piece = new Pho(Team.CHO);
        Space space = new Blank();

        assertDoesNotThrow(() -> piece.validateArrival(space));
    }

    @Test
    void 이동_경로_사이에_말이_없을_경우_예외_처리_테스트() {
        Piece piece = new Pho(Team.CHO);
        List<Piece> pieces = List.of();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateRoutes(pieces))
                .withMessage("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
    }

    @Test
    void 이동_경로_사이에_말이_2개_이상_경우_예외_처리_테스트() {
        Piece piece = new Pho(Team.CHO);
        List<Piece> pieces = List.of(
                new Sang(Team.CHO),
                new Ma(Team.CHO)
        );

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateRoutes(pieces))
                .withMessage("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
    }

    @Test
    void 이동_경로_사이에_포가_있을_경우_예외_처리_테스트() {
        Piece piece = new Pho(Team.CHO);
        List<Piece> pieces = List.of(new Pho(Team.CHO));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateRoutes(pieces))
                .withMessage("포는 이동 경로 사이에 포를 제외한 하나의 말이 있어야 합니다.");
    }

    @Test
    void 포_경로_우측_방향_반환_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(5, 0);
        Path actual = piece.getPath(from, to);

        Path expect = new Path(List.of(
                new Position(1, 0),
                new Position(2, 0),
                new Position(3, 0),
                new Position(4, 0)
        ));
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void 포_경로_좌측_방향_반환_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(5, 0);
        Position to = new Position(0, 0);
        Path actual = piece.getPath(from, to);

        Path expect = new Path(List.of(
                new Position(4, 0),
                new Position(3, 0),
                new Position(2, 0),
                new Position(1, 0)
        ));
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void 포_경로_상단_방향_반환_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(0, 5);
        Path actual = piece.getPath(from, to);

        Path expect = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3),
                new Position(0, 4)
        ));
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void 포_경로_하단_방향_반환_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(0, 5);
        Position to = new Position(0, 0);
        Path actual = piece.getPath(from, to);

        Path expect = new Path(List.of(
                new Position(0, 4),
                new Position(0, 3),
                new Position(0, 2),
                new Position(0, 1)
        ));
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void 포_궁성_정상_이동_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(5, 2);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 포_궁성_예외_이동_테스트() {
        Piece piece = new Pho(Team.CHO);

        Position from = new Position(3, 0);
        Position to = new Position(5, 1);

        assertThatIllegalStateException()
                .isThrownBy(() -> piece.validateMove(from, to))
                .withMessage("해당 위치로 포가 이동할 수 없습니다.");
    }
}