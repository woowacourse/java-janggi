package janggi.domain.piece;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SteppingPieceTest {


    @Test
    @DisplayName("이동 경로에 기물이 존재할 경우 예외 발생")
    void validateCanMove_Fail_Exist_Pieces_In_Path() {
        // given
        List<Piece> piecesInPath = new ArrayList<>(List.of(new Jol(TeamType.CHU)));
        Gung gung = new Gung(TeamType.CHU);

        // when & then
        assertThatThrownBy(() -> gung.validateCanMove(piecesInPath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 존재하여 이동할 수 없습니다.");
    }
}