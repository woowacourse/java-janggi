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

public class KingTest {

    private BoardStatus boardStatus = new LocalMemoryBoardStatus();

    @BeforeEach
    void setUp() {
        new EmptyBoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH).initBoardStatus(boardStatus);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 8, 3, 8",
            "4, 8, 5, 8",
            "4, 8, 4, 7",
            "4, 8, 4, 9"
    })
    void 궁은_궁성_내부에서_상하좌우로_한칸_이동_할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece king = new King(Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardStatus.setPiece(currentPosition, king);

        Position definationPosition = new Position(endX, endY);

        // when & then
        Assertions.assertTrue(king.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));

    }

    @ParameterizedTest
    @CsvSource({
            "3, 7, 4, 8",
            "3, 9, 4, 8",
            "4, 8, 3, 7",
            "4, 8, 3, 9",
            "5, 9, 4, 8",
            "5, 7, 4, 8",
            "4, 8, 5, 7",
            "4, 8, 5, 9"
    })
    void 궁은_궁성_내부에서_대각선으로_한칸_이동_할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece king = new King(Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardStatus.setPiece(currentPosition, king);

        Position definationPosition = new Position(endX, endY);

        // when & then
        Assertions.assertTrue(king.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 7, 2, 7",
            "3, 7, 3, 6",
            "5, 7, 6, 7",
            "5, 7, 5, 6"
    })
    void 궁은_궁성_내부에서만_이동할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece king = new King(Team.CHO);
        Position currentPosition = Position.of(startX, startY);

        boardStatus.setPiece(currentPosition, king);

        Position definationPosition = Position.of(endX, endY);

        // when & then
        Assertions.assertFalse(king.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }

    @ParameterizedTest
    @CsvSource({
            "4, 7, 5, 8",
            "4, 7, 3, 8",
            "4, 9, 3, 8",
            "4, 9, 5, 8",
            "5, 8, 4, 7",
            "3, 8, 4, 7",
            "3, 8, 4, 9",
            "5, 8, 4, 9"
    })
    void 궁은_궁성_내부에서_대각선_이동할_수_없는곳에서_이동할_수_없다(int startX, int startY, int endX, int endY) {
        // given
        Piece king = new King(Team.CHO);
        Position currentPosition = Position.of(startX, startY);

        boardStatus.setPiece(currentPosition, king);

        Position definationPosition = Position.of(endX, endY);

        // when & then
        Assertions.assertFalse(king.canMove(currentPosition, definationPosition, boardStatus.getBoardStatus()));
    }
}
