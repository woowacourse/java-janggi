package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardFactory;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardStatus;
import team.janggi.domain.board.LocalMemoryBoardStatus;

public class SoldierTest {

    private BoardStatus boardStatus = new LocalMemoryBoardStatus();

    @BeforeEach
    void setUp() {
        new EmptyBoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH).initBoardStatus(boardStatus);
    }

    @Test
    void 초의_졸은_전진_할_수_있다() {
        // given
        Soldier soldier = new Soldier(Team.CHO);
        Position currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        Position definationPosition = new Position(5, 4);

        // when & then
        Assertions.assertTrue(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }


    @Test
    void 한의_졸은_전진_할_수_있다() {
        // given
        Soldier soldier = new Soldier(Team.HAN);
        Position currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        Position definationPosition = new Position(5, 6);

        // when & then
        Assertions.assertTrue(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }


    @Test
    void 초의_졸은_후퇴_할_수_없다() {
        // given
        Soldier soldier = new Soldier(Team.CHO);
        Position currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        Position definationPosition = new Position(5, 6);

        // when & then
        Assertions.assertFalse(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));

    }

    @Test
    void 한의_졸은_후퇴_할_수_없다() {
        // given
        Soldier soldier = new Soldier(Team.HAN);
        Position currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        Position definationPosition = new Position(5, 4);

        // when & then
        Assertions.assertFalse(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));

    }

    @Test
    void 초의_졸은_좌으로_이동할_수_있다() {
        var soldier = new Soldier(Team.CHO);
        var currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        var definationPosition = new Position(4, 5);
        // when & then
        Assertions.assertTrue(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }

    @Test
    void 한의_졸은_좌으로_이동할_수_있다() {
        var soldier = new Soldier(Team.HAN);
        var currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        var definationPosition = new Position(6, 5);

        // when & then
        Assertions.assertTrue(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }

    @Test
    void 초의_졸은_우로_이동할_수_있다() {
        var soldier = new Soldier(Team.CHO);
        var currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        var definationPosition = new Position(6, 5);

        // when & then
        Assertions.assertTrue(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }

    @Test
    void 한의_졸은_우로_이동할_수_있다() {
        var soldier = new Soldier(Team.HAN);
        var currentPosition = new Position(5, 5);

        boardStatus.setPiece(currentPosition, soldier);

        var definationPosition = new Position(4, 5);

        // when & then
        Assertions.assertTrue(soldier.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));

    }

    @ParameterizedTest
    @CsvSource({
            "3, 7, 4, 8",
            "5, 7, 4, 8",
            "4, 8, 3, 9",
            "4, 8, 5, 9"
    })
    void 한의_졸은_초의_궁성영역_에서_대각선_이동할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece solider = new Soldier(Team.HAN);
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);
        boardStatus.setPiece(startPosition, solider);

        // when
        boolean expected = solider.canMove(startPosition, endPosition, boardStatus.getBoardStatus());

        // then
        Assertions.assertTrue(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 2, 4, 1",
            "5, 2, 4, 1",
            "4, 1, 3, 0",
            "4, 1, 5, 0"
    })
    void 초의_졸은_한의_궁성영역_에서_대각선_이동할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece solider = new Soldier(Team.CHO);
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);
        boardStatus.setPiece(startPosition, solider);

        // when
        boolean expected = solider.canMove(startPosition, endPosition, boardStatus.getBoardStatus());

        // then
        Assertions.assertTrue(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 2, 5, 0",
            "5, 2, 3, 0",
            "3, 2, 3, 0",
            "5, 2, 5, 0"
    })
    void 초의_졸은_한의_궁성영역_에서_두칸_대각선_이동할_수_없다(int startX, int startY, int endX, int endY) {
        // given
        Piece solider = new Soldier(Team.CHO);
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);
        boardStatus.setPiece(startPosition, solider);

        // when
        boolean expected = solider.canMove(startPosition, endPosition, boardStatus.getBoardStatus());

        // then
        Assertions.assertFalse(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 1, 3, 2",
            "4, 1, 5, 2",
            "3, 0, 4, 1",
            "5, 0, 4, 1"
    })
    void 초의_졸은_한의_궁성영역에서_대각선_후퇴_할_수_없다(int startX, int startY, int endX, int endY) {
        // given
        Piece solider = new Soldier(Team.CHO);
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);
        boardStatus.setPiece(startPosition, solider);

        // when
        boolean expected = solider.canMove(startPosition, endPosition, boardStatus.getBoardStatus());

        // then
        Assertions.assertFalse(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 8, 3, 7",
            "4, 8, 5, 7",
            "3, 9, 4, 8",
            "5, 9, 4, 8"
    })
    void 한의_졸은_초의_궁성영역에서_대각선_후퇴_할_수_없다(int startX, int startY, int endX, int endY) {
        // given
        Piece solider = new Soldier(Team.CHO);
        Position startPosition = new Position(startX, startY);
        Position endPosition = new Position(endX, endY);
        boardStatus.setPiece(startPosition, solider);

        // when
        boolean expected = solider.canMove(startPosition, endPosition, boardStatus.getBoardStatus());

        // then
        Assertions.assertFalse(expected);
    }
}
