package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {
    @Test
    @DisplayName("모든 기물에 대해, 소속팀이 달라도 기물 종류가 같으면 isSamePiece가 참을 반환한다.")
    void should_result_true_when_same_piece_type_even_if_different_team_for_all_pieces() {
        for (PieceType pieceType : PieceType.values()) {
            Piece pieceCho = new Piece(Team.CHO, pieceType);
            Piece pieceHan = new Piece(Team.HAN, pieceType);

            Assertions.assertThat(pieceCho.isSamePiece(pieceHan.pieceType()))
                    .as("기물 종류: " + pieceType.name() + " - 소속팀이 다를 때 isSamePiece 검증 실패")
                    .isTrue();
        }
    }
}
