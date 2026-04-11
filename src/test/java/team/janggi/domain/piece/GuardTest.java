package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardFactory;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardStatus;
import team.janggi.domain.board.LocalMemoryBoardStatus;

public class GuardTest {

    private BoardStatus boardStatus = new LocalMemoryBoardStatus();

    @BeforeEach
    void setUp() {
        new EmptyBoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH).initBoardStatus(boardStatus);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 9, 4, 9",
            "5, 9, 4, 9",
            "3, 9, 3, 8",
            "5, 9, 5, 8"
    })
    void 사는_궁성_내부에서_상하좌우로_한칸_이동_할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece guard = new Guard(Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardStatus.setPiece(currentPosition, guard);

        Position definationPosition = new Position(endX, endY);

        // when & then
        Assertions.assertTrue(guard.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));

    }

    @ParameterizedTest
    @CsvSource({
            "3, 9, 4, 8",
            "5, 9, 4, 8",
            "3, 7, 4, 8",
            "5, 7, 4, 8"
    })
    void 사는_궁성_내부에서_대각선으로_한칸_이동_할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece guard = new Guard(Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardStatus.setPiece(currentPosition, guard);

        Position definationPosition = new Position(endX, endY);

        // when & then
        Assertions.assertTrue(guard.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));

    }

    @ParameterizedTest
    @CsvSource({
            "3, 0, 2, 0",
            "5, 0, 6, 0",
            "3, 2, 3, 3",
            "5, 2, 5, 3"
    })
    void 사는_궁성_내부에서만_이동할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece guard = new Guard(Team.HAN);
        Position currentPosition = Position.of(startX, startY);

        boardStatus.setPiece(currentPosition, guard);

        Position definationPosition = Position.of(endX, endY);

        // when & then
        Assertions.assertFalse(guard.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 1, 4, 0",
            "3, 1, 4, 2",
            "5, 1, 4, 0",
            "5, 1, 4, 2",
            "4, 0, 3, 1",
            "4, 0, 5, 1",
            "5, 1, 4, 0",
            "5, 1, 4, 2"
    })
    void 사는_궁성_내부에서_대각선_이동할_수_없는곳에서_이동할_수_없다(int startX, int startY, int endX, int endY) {
        // given
        Piece guard = new Guard(Team.HAN);
        Position currentPosition = Position.of(startX, startY);

        boardStatus.setPiece(currentPosition, guard);

        Position definationPosition = Position.of(endX, endY);

        // when & then
        Assertions.assertFalse(guard.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }
}
