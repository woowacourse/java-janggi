package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.MovePath;
import domain.board.Position;
import domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SoldierTest {
    @Test
    void 졸병이_앞으로_이동_가능() {
        Soldier soldier = new Soldier(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FIVE, Column.ONE);
        MovePath movePath = new MovePath(source, destination);
        boolean canMove = soldier.isValidMovement(movePath);

        assertThat(canMove).isTrue();
    }

    @Test
    void 졸병이_옆으로_이동_가능() {
        Soldier soldier = new Soldier(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.TWO);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = soldier.isValidMovement(movePath);

        assertThat(canMove).isTrue();
    }

    @Test
    void 쫄병이_뒤로_이동_불가능() {
        Soldier soldier = new Soldier(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.THREE, Column.ONE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = soldier.isValidMovement(movePath);

        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_있으면_이동불가() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = List.of(elephant);

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @Test
    void 졸병은_궁성_안에서_대각선_한칸_이동가능() {
        Piece piece = new Soldier(PieceColor.RED);
        Position source = new Position(Row.EIGHT, Column.FOUR);
        Position destination = new Position(Row.NINE, Column.FIVE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = piece.isValidMovement(movePath);

        assertThat(canMove).isTrue();
    }


    @Test
    void 졸병은_한나라팀이면_윗방향_이동불가능() {
        Piece piece = new Soldier(PieceColor.RED);
        Position source = new Position(Row.ZERO, Column.FOUR);
        Position destination = new Position(Row.NINE, Column.FIVE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = piece.isValidMovement(movePath);

        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병은_궁성_밖에서_대각선_이동_불가능() {
        Piece piece = new Soldier(PieceColor.RED);
        Position source = new Position(Row.SEVEN, Column.FOUR);
        Position destination = new Position(Row.SIX, Column.FIVE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = piece.isValidMovement(movePath);

        assertThat(canMove).isFalse();
    }
}
