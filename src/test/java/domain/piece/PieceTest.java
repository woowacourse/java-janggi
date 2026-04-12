package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Piece 클래스 테스트")
class PieceTest {

    @Test
    @DisplayName("getPieceType : 해당 기물 타입을 반환한다")
    void getPieceTypeReturnsConstructedType() {
        assertThat(new Piece(Team.HAN, PieceType.CANNON).getPieceType()).isEqualTo(PieceType.CANNON);
        assertThat(new Piece(Team.CHO, PieceType.HORSE).getPieceType()).isEqualTo(PieceType.HORSE);
    }

    @Test
    @DisplayName("isOwnedBy : 해당 팀 소유 기물에만 true를 반환한다")
    void isOwnedByReturnsTrueForCorrectTeam() {
        Piece hanPiece = new Piece(Team.HAN, PieceType.GENERAL);

        assertThat(hanPiece.isOwnedBy(Team.HAN)).isTrue();
        assertThat(hanPiece.isOwnedBy(Team.CHO)).isFalse();
    }

    @Test
    @DisplayName("isSameTeamAs : 같은 팀 기물끼리만 true를 반환한다")
    void isSameTeamAsReturnsTrueForSameTeam() {
        Piece hanGeneral = new Piece(Team.HAN, PieceType.GENERAL);
        Piece hanSoldier = new Piece(Team.HAN, PieceType.SOLDIER);
        Piece choGeneral = new Piece(Team.CHO, PieceType.GENERAL);

        assertThat(hanGeneral.isSameTeamAs(hanSoldier)).isTrue();
        assertThat(hanGeneral.isSameTeamAs(choGeneral)).isFalse();
        assertThat(choGeneral.isSameTeamAs(hanGeneral)).isFalse();
    }
}
