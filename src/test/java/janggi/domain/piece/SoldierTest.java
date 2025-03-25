package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_ELEPHANT;
import static janggi.domain.TestFixture.BLUE_SOLDIER;
import static janggi.domain.TestFixture.RED_ELEPHANT;
import static janggi.domain.TestFixture.RED_SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SoldierTest {
    @Test
    void 졸병이_앞으로_이동_가능() {
        // given
        Piece soldier = RED_SOLDIER;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FIVE, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 졸병이_옆으로_이동_가능() {
        // given
        Piece soldier = RED_SOLDIER;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 빨간_쫄병이_뒤로_이동_불가능() {
        // given
        Piece soldier = RED_SOLDIER;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.THREE, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 파란_쫄병이_아래로_이동_불가능() {
        // given
        Piece soldier = BLUE_SOLDIER;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FIVE, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_목적지에_같은팀이_있으면_이동불가() {
        Piece soldier = RED_SOLDIER;
        Piece destinationPiece = RED_ELEPHANT;

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = soldier.canMove(soldier, destinationPiece, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_있으면_이동불가() {
        Piece soldier = RED_SOLDIER;
        Piece destinationPiece = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = List.of(destinationPiece);

        boolean canMove = soldier.canMove(soldier, destinationPiece, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece soldier = RED_SOLDIER;
        Piece destinationPiece = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = soldier.canMove(soldier, destinationPiece, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @DisplayName("파란 졸병이 궁성 안에 있을 때 궁성 대각선으로도 움직일 수 있다")
    @Test
    void BlueSolider_canMoveDiagonal_inPalace() {
        // given
        Piece soldier = BLUE_SOLDIER;

        Position source = new Position(Row.THREE, Column.FOUR);
        Position destination = new Position(Row.TWO, Column.FIVE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("파란 졸병이 궁성 안에 있을 때도 뒤로는 움직일 수 없다.")
    @Test
    void BlueSolider_cannotMoveBack_inPalace() {
        // given
        Piece soldier = BLUE_SOLDIER;

        Position source = new Position(Row.TWO, Column.FIVE);
        Position destination = new Position(Row.THREE, Column.FOUR);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @DisplayName("빨간 졸병이 궁성 안에 있을 때 궁성 대각선으로도 움직일 수 있다")
    @Test
    void RedSolider_canMoveDiagonal_inPalace() {
        // given
        Piece soldier = RED_SOLDIER;

        Position source = new Position(Row.EIGHT, Column.FOUR);
        Position destination = new Position(Row.NINE, Column.FIVE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("빨간 졸병이 궁성 안에 있을 때도 뒤로는 움직일 수 없다.")
    @Test
    void RedSolider_cannotMoveBack_inPalace() {
        // given
        Piece soldier = RED_SOLDIER;

        Position source = new Position(Row.NINE, Column.FIVE);
        Position destination = new Position(Row.EIGHT, Column.FOUR);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

}
