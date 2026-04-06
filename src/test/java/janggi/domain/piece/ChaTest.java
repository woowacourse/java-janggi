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
import static org.junit.jupiter.api.Assertions.assertAll;

class ChaTest {

    private Cha cha;

    @BeforeEach
    void setUp() {
        cha = new Cha(TeamType.HAN);
    }

    @Test
    @DisplayName("차는 이동 경로에 장애물이 없다면 이동할 수 있다.")
    void validateCanMove_Success() {
        // given
        List<Piece> pieces = Collections.emptyList();

        // when & then
        assertThatCode(() -> cha.validateCanMove(pieces))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("차가 이동할 수 없는 위치일 경우 예외 발생")
    void validateCanMove_Fail_Invalid_Position() {
        // given
        Position start = new Position(4, 4);
        Position diagonalEnd = new Position(5, 5);
        Position knightEnd = new Position(5, 6);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> cha.getPiecePositionsInPath(start, diagonalEnd))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다."),
                () -> assertThatThrownBy(() -> cha.getPiecePositionsInPath(start, knightEnd))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동할 수 없는 위치입니다.")
        );
    }

    @Test
    @DisplayName("이동 경로 중간에 기물이 존재할 경우 예외 발생")
    void validateCanMove_Fail_Exist_Pieces_In_Path() {
        // given
        List<Piece> piecesInPath = new ArrayList<>(List.of(new Gung(TeamType.CHU)));

        // when & then
        assertThatThrownBy(() -> cha.validateCanMove(piecesInPath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
    }
}