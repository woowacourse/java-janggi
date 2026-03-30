package domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Piece 클래스 테스트")
class PieceTest {

    @Test
    @DisplayName("getPieceType : 해당 기물 타입을 반환한다")
    void getPieceTypeReturnsConstructedType() {
        assertThat(new Piece(Team.HAN, PieceType.CANNON).getPieceType()).isEqualTo(PieceType.CANNON);
        assertThat(new Piece(Team.CHO, PieceType.HORSE).getPieceType()).isEqualTo(PieceType.HORSE);
    }

    @Test
    @DisplayName("isChariot : 차 기물에만 true를 반환한다")
    void isChariotReturnsTrueOnlyForChariot() {
        assertThat(new Piece(Team.HAN, PieceType.CHARIOT).isChariot()).isTrue();
        assertThat(new Piece(Team.HAN, PieceType.CANNON).isChariot()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GENERAL).isChariot()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.HORSE).isChariot()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.ELEPHANT).isChariot()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GUARD).isChariot()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.SOLDIER).isChariot()).isFalse();
    }

    @Test
    @DisplayName("isCannon : 포 기물에만 true를 반환한다")
    void isCannonReturnsTrueOnlyForCannon() {
        assertThat(new Piece(Team.HAN, PieceType.CANNON).isCannon()).isTrue();
        assertThat(new Piece(Team.HAN, PieceType.CHARIOT).isCannon()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GENERAL).isCannon()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.SOLDIER).isCannon()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.HORSE).isCannon()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.ELEPHANT).isCannon()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GUARD).isCannon()).isFalse();
    }

    @Test
    @DisplayName("isStepPiece : 마와 상 기물에만 true를 반환한다")
    void isStepPieceReturnsTrueForHorseAndElephant() {
        assertThat(new Piece(Team.HAN, PieceType.HORSE).isStepPiece()).isTrue();
        assertThat(new Piece(Team.HAN, PieceType.ELEPHANT).isStepPiece()).isTrue();
        assertThat(new Piece(Team.HAN, PieceType.CHARIOT).isStepPiece()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.CANNON).isStepPiece()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GENERAL).isStepPiece()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GUARD).isStepPiece()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.SOLDIER).isStepPiece()).isFalse();
    }

    @Test
    @DisplayName("isSoldier : 졸 기물에만 true를 반환한다")
    void isSoldierReturnsTrueOnlyForSoldier() {
        assertThat(new Piece(Team.HAN, PieceType.SOLDIER).isSoldier()).isTrue();
        assertThat(new Piece(Team.HAN, PieceType.HORSE).isSoldier()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.ELEPHANT).isSoldier()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.CHARIOT).isSoldier()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.CANNON).isSoldier()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GENERAL).isSoldier()).isFalse();
        assertThat(new Piece(Team.HAN, PieceType.GUARD).isSoldier()).isFalse();
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
