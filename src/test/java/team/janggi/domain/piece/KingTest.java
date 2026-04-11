package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardPiecesInitializer;
import team.janggi.domain.Palace;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardPieces;

public class KingTest {

    private BoardPieces boardPieces = new BoardPieces();

    @BeforeEach
    void setUp() {
        new EmptyBoardPiecesInitializer().initBoardStatus(boardPieces);
    }
    
    @ParameterizedTest
    @CsvSource({
            "3,7,   4,8",
            "5,7,   4,8",
            "3,9,   4,8",
            "5,9,   4,8"
    })
    void 초나라의_궁은_초나라_초기_궁_위치에서_네각을_대각선_이동_할_수_있다(
            int startX, int startY,
            int destinationX, int destinationY) {
        // given
        Piece king = Piece.of(PieceType.KING, Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardPieces.setPiece(currentPosition, king);

        Position destinationPosition = new Position(destinationX, destinationY);

        // when & then
        Assertions.assertTrue(king.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "3,7,   4,8",
            "5,7,   4,8",
            "3,9,   4,8",
            "5,9,   4,8"
    })
    void 초나라의_궁은_초나라_궁의_네각에서_초기_위치로_이동_할_수_있다(
            int startX, int startY,
            int destinationX, int destinationY) {
        // given
        Piece king = Piece.of(PieceType.KING, Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardPieces.setPiece(currentPosition, king);

        Position destinationPosition = new Position(destinationX, destinationY);

        // when & then
        Assertions.assertTrue(king.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "4,7,   3,6",
            "4,7,   4,6",
            "4,7,   5,6",

            "3,8,   2,7",
            "3,8,   2,8",
            "3,8,   2,9",

            "5,8,   6,7",
            "5,8,   6,8",
            "5,8,   6,9",

            "3,7,   2,6",
            "3,7,   3,6",
            "3,7,   2,7",
            "3,7,   2,8",
            "3,7,   4,6",

            "5,7,   4,6",
            "5,7,   5,6",
            "5,7,   6,6",
            "5,7,   6,7",
            "5,7,   6,8",

            "3,9,   2,8",
            "3,9,   2,9",
            "5,9,   6,8",
            "5,9,   6,9",
    })
    void 초나라의_궁은_초나라_초나라_궁_바깥으로_이동할수_없다(
            int startX, int startY,
            int destinationX, int destinationY) {
        // given
        Piece king = Piece.of(PieceType.KING, Team.CHO);
        Position currentPosition = new Position(startX, startY);
        Position destinationPosition = new Position(destinationX, destinationY);

        // when
        boardPieces.setPiece(currentPosition, king);

        //  then
        Assertions.assertFalse(king.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @Test
    void 초나라_궁은_이동위치에_같은_팀의_말이_있으면_이동할수_없다() {
        // given
        Team ourTeam = Team.CHO;
        Piece king = Piece.of(PieceType.KING, ourTeam);
        Position currentPosition = Palace.CHO_KING_POSITION;
        Position destinationPosition = new Position(4, 7);

        boardPieces.setPiece(currentPosition, king);
        boardPieces.setPiece(destinationPosition, Piece.of(PieceType.GUARD, ourTeam));

        // when & then
        Assertions.assertFalse(king.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @Test
    void 초나라_궁은_이동위치에_다른_팀의_말이_있으면_이동할수_있다() {
        // given
        Team opponentTeam = Team.HAN;
        Piece opponentPiece = Piece.of(PieceType.SOLDIER, opponentTeam);
        Position destinationPosition = new Position(4, 7);

        Team ourTeam = Team.CHO;
        Piece king = Piece.of(PieceType.KING, ourTeam);
        Position currentPosition = Palace.CHO_KING_POSITION;

        boardPieces.setPiece(currentPosition, king);
        boardPieces.setPiece(destinationPosition, opponentPiece);

        // when & then
        Assertions.assertTrue(king.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }
}
