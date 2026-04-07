package domain;

import domain.board.Piece;
import domain.board.Team;
import domain.board.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    @DisplayName("기물을 정상 생성하면 초기화한다.")
    void shouldCreatePieceCorrectly() {
        // given
        // when
        Piece piece = Piece.of(Team.CHU, Type.CHARIOT, new FixedMoveStrategy());

        // then
        Assertions.assertEquals(Type.CHARIOT, piece.getType());
        Assertions.assertEquals(Team.CHU, piece.getTeam());
    }
}
