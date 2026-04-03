package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.side.TeamType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
//        Position start = new Position(startX, startY);
//        Position end = new Position(endX, endY);
        List<Piece> piecesInPath = new ArrayList<>(List.of(new Jol(TeamType.CHU)));

        // when & then
        assertThatCode(() -> po.validateCanMove(piecesInPath))
                .doesNotThrowAnyException();
    }

    /**
     * 이건 Board 테스트에서
     */
//    @Test
//    @DisplayName("제자리로 이동할 경우 예외 발생")
//    void validateCanMove_Fail_Same_Position() {
//        // given
//        Position start = new Position(4, 4);
//        Position sameEnd = new Position(4, 4);
//
//        // when & then
//        assertThatThrownBy(() -> po.validateCanMove(start, sameEnd, board))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("출발지와 목적지가 동일합니다.");
//    }

    /**
     * 이건 SlidingPiece에서 테스트
     */
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
//        Position start = new Position(4, 4);
//        Position end = new Position(4, 6);
        List<Piece> pieces = Collections.emptyList();

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하지 않아 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 기물이 2개 이상일 경우 예외 발생")
    void validateCanMove_Fail_TooManyObstacles() {
//        Position start = new Position(2, 10);
//        Position end = new Position(2, 1);

        List<Piece> pieces = new ArrayList<>(List.of(new Cha(TeamType.CHU), new Gung(TeamType.HAN)));

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 2개 이상 존재합니다.");
    }

    @Test
    @DisplayName("포의 이동 경로에 포가 존재할 경우 예외 발생")
    void validateCanMove_Fail_PoAsObstacle() {
//        Position start = new Position(2, 9);
//        Position end = new Position(2, 7);
        List<Piece> pieces = new ArrayList<>(List.of(new Po(TeamType.CHU)));

        // when & then
        assertThatThrownBy(() -> po.validateCanMove(pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 넘을 수 없습니다.");
    }
}