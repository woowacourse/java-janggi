package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Jol;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @Test
    @DisplayName("기물을 이동하면 새 Pieces를 반환하고 원본은 유지한다.")
    void move() {
        // given
        Pieces pieces = Pieces.createChu();
        Position start = new Position(1, 4);
        Position end = new Position(1, 5);

        // when
        Pieces movedPieces = pieces.move(start, end);

        // then
        assertAll(
            () -> assertThat(pieces.findPiece(start)).get().isInstanceOf(Jol.class),
            () -> assertThat(pieces.findPiece(end)).isEmpty(),
            () -> assertThat(movedPieces.findPiece(start)).isEmpty(),
            () -> assertThat(movedPieces.findPiece(end)).get().isInstanceOf(Jol.class)
        );
    }

    @Test
    @DisplayName("기물을 제거하면 새 Pieces를 반환하고 원본은 유지한다.")
    void remove() {
        // given
        Pieces pieces = Pieces.createChu();
        Position target = new Position(1, 4);

        // when
        Pieces removedPieces = pieces.remove(target);

        // then
        assertAll(
            () -> assertThat(pieces.findPiece(target)).get().isInstanceOf(Jol.class),
            () -> assertThat(removedPieces.findPiece(target)).isEmpty(),
            () -> assertThat(removedPieces.makeSnapShot()).hasSize(15)
        );
    }

    @Test
    @DisplayName("보드 스냅샷은 좌표, 기물 이름, 팀 정보를 포함한다.")
    void makeSnapShot() {
        // given
        Pieces pieces = Pieces.createChu();

        // when
        var boardSpots = pieces.makeSnapShot();
        var gungSpot = boardSpots.get(new Position(5, 2));

        // then
        assertThat(gungSpot).isNotNull();
        assertAll(
            () -> assertThat(gungSpot.position()).isEqualTo(new Position(5, 2)),
            () -> assertThat(gungSpot.pieceName()).isEqualTo(PieceType.GUNG.getName()),
            () -> assertThat(gungSpot.teamType()).isEqualTo(janggi.domain.side.TeamType.CHU)
        );
    }
}
