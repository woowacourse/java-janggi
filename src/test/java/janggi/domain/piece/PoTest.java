package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PoTest {

    private Po po;

    @BeforeEach
    void setUp() {
        po = new Po(TeamType.HAN);
    }

    @Test
    @DisplayName("포는 경로 사이에 기물이 정확히 하나(포 제외) 있을 때만 넘어갈 수 있다.")
    void validateCanMove_Success() {
        // given
        List<Piece> piecesInPath = new ArrayList<>(List.of(new Jol(TeamType.CHU)));

        // when & then
        assertThatCode(() -> po.validateCanMove(piecesInPath))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("포가 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_InvalidPattern() {
        // given
        Position start = new Position(4, 4);
        Position invalidEnd = new Position(5, 5);

        // when & then
        assertThatThrownBy(() -> po.getPiecePositionsInPath(start, invalidEnd))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 기물이 하나도 없을 경우 예외 발생")
    void validateCanMove_Fail_NoObstacle() {
        List<Piece> pieces = Collections.emptyList();

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 기물이 2개 이상일 경우 예외 발생")
    void validateCanMove_Fail_TooManyObstacles() {
        List<Piece> pieces = new ArrayList<>(List.of(new Cha(TeamType.CHU), new Gung(TeamType.HAN)));

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 2개 이상 존재합니다.");
    }

    @Test
    @DisplayName("포의 이동 경로에 포가 존재할 경우 예외 발생")
    void validateCanMove_Fail_PoAsObstacle() {
        List<Piece> pieces = new ArrayList<>(List.of(new Po(TeamType.CHU)));

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }
}