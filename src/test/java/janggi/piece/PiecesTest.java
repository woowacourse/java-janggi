package janggi.piece;

import fixture.PieceFixture;
import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PiecesTest {

    @Test
    @DisplayName("해당 팀의 기물을 모두 가져올 수 있다")
    void canGetPiecesByTeam() {
        // given
        final Pieces pieces = Pieces.from(List.of(
                PieceFixture.createPiece(1, 1, PieceType.SOLDIER, Team.CHO),
                PieceFixture.createPiece(2, 2, PieceType.SOLDIER, Team.CHO),
                PieceFixture.createPiece(3, 3, PieceType.SOLDIER, Team.HAN),
                PieceFixture.createPiece(4, 4, PieceType.SOLDIER, Team.HAN)));

        // when
        // then
        assertAll(() -> {
            for (final Piece piece : pieces.getByTeam(Team.HAN).getPieces()) {
                assertThat(piece.team.isHan()).isTrue();
            }
        });
    }
}
