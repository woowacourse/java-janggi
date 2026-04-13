package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.team.TeamType;
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

    @ParameterizedTest
    @DisplayName("차는 궁성 내부에서 대각선으로 이동할 수 있다.")
    @CsvSource({
            "4, 1, 5, 2",
            "4, 1, 6, 3",
            "4, 3, 5, 2",
            "4, 3, 6, 1",
    })
    void validateCanMove_Diagonal_Success_When_In_Palace(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatCode(() -> cha.getPiecePositionsInPath(start, end))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("차가 궁성 내부에서 대각선으로 세 칸 이상 이동할 경우 예외 발생")
    @CsvSource({
            "4, 1, 7, 4",
            "6, 1, 3, 4"
    })
    void validateCanMove_Diagonal_Fail_When_In_Palace(int startX, int startY, int endX, int endY) {
        // given
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        // when & then
        assertThatThrownBy(() -> cha.getPiecePositionsInPath(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 위치입니다.");
    }
}