package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PieceStorageTest {

    @Test
    void 외부에서_주입받은_기물리스트로_생성된다() {
        List<Piece> piecesList = List.of(
                Piece.of(TeamColor.CHO, PieceType.CANNON),
                Piece.of(TeamColor.CHO, PieceType.ELEPHANT),
                Piece.of(TeamColor.CHO, PieceType.GUARD));

        PieceStorage pieceStorage = new PieceStorage(piecesList);

        assertThat(pieceStorage.getActivePieces()).isEqualTo(piecesList);
    }

    @Test
    void 활성_기물_조회_결과는_외부에서_수정할_수_없다() {
        List<Piece> piecesList = List.of(
                Piece.of(TeamColor.CHO, PieceType.CANNON),
                Piece.of(TeamColor.CHO, PieceType.ELEPHANT)
        );

        PieceStorage pieceStorage = new PieceStorage(piecesList);

        assertThatThrownBy(() ->
                pieceStorage.getActivePieces().add(Piece.of(TeamColor.CHO, PieceType.GUARD))
        ).isInstanceOf(UnsupportedOperationException.class);
    }
}
