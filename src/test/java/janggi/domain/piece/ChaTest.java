package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    void 차_움직임_경로_정상_판정_테스트() {
        Piece piece = new Cha(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(0, 9);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 차_대각선_움직임_경로_정상_판정_테스트() {
        Piece piece = new Cha(Team.CHO);

        Position from = new Position(3, 2);
        Position to = new Position(5, 0);

        assertDoesNotThrow(() -> piece.validateMove(from, to));
    }

    @Test
    void 차_움직임_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);

        Position from = new Position(0, 0);
        Position to = new Position(1, 1);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("[ERROR] 해당 위치로 차가 이동할 수 없습니다.");
    }

    @Test
    void 차_대각선_움직임_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);

        Position from = new Position(5, 1);
        Position to = new Position(4, 0);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateMove(from, to))
            .withMessage("[ERROR] 해당 위치로 차가 이동할 수 없습니다.");
    }

    @Test
    void 도착지에_같은_팀의_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);
        Space space = new Sang(Team.CHO);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateArrival(space))
            .withMessage("[ERROR] 이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    @Test
    void 이동_경로_사이에_말이_있을_경우_예외_처리_테스트() {
        Piece piece = new Cha(Team.CHO);
        List<Piece> pieces = List.of(new Sang(Team.CHO));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.validateRoutes(pieces))
            .withMessage("[ERROR] 이동 경로 사이에 다른 말이 있으면 안됩니다.");
    }

    @Test
    void 차_경로_반환_테스트() {
        Piece piece = new Cha(Team.CHO);

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
}