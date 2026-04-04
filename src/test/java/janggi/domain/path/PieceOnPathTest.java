package janggi.domain.path;

import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.General;
import janggi.domain.team.Team;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceOnPathTest {

    @Test
    void 경로_위_기물에_피스를_추가하면_저장된다() {
        // given
        PieceOnPath pieceOnPath = new PieceOnPath();
        EmptyPiece emptyPiece = new EmptyPiece();
        General general = new General(Team.HAN);

        // when
        pieceOnPath.add(emptyPiece);
        pieceOnPath.add(general);

        // then
        assertThat(pieceOnPath).containsExactly(emptyPiece, general);
    }

    @Test
    void 빈_경로_위_기물을_순회하면_비어있다() {
        // given
        PieceOnPath pieceOnPath = new PieceOnPath();

        // when & then
        assertThat(pieceOnPath).isEmpty();
    }
}
