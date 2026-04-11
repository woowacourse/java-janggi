package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardPiecesInitializer;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardPieces;

public class ChariotTest {

    private final BoardPieces boardPieces = new BoardPieces();

    @BeforeEach
    void setUp() {
        new EmptyBoardPiecesInitializer().initBoardStatus(boardPieces);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 5, 4, true, true", // 상 1칸 이동, 목표 위치에 기물 있음
            "5, 5, 5, 6, true, true", // 하 1칸 이동, 목표 위치에 기물 있음
            "5, 5, 4, 5, true, true", // 좌 1칸 이동, 목표 위치에 기물 있음
            "5, 5, 6, 5, true, true", // 우 1칸 이동, 목표 위치에 기물 있음

            "5, 5, 5, 3, true, true", // 상 2칸 이동, 목표 위치에 기물 있음
            "5, 5, 5, 7, true, true", // 하 2칸 이동, 목표 위치에 기물 있음
            "5, 5, 3, 5, true, true", // 좌 2칸 이동, 목표 위치에 기물 있음
            "5, 5, 7, 5, true, true", // 우 2칸 이동, 목표 위치에 기물 있음

            "5, 5, 5, 2, true, true", // 상 3칸 이동, 목표 위치에 기물 있음
            "5, 5, 5, 8, true, true", // 하 3칸 이동, 목표 위치에 기물 있음
            "5, 5, 2, 5, true, true", // 좌 3칸 이동, 목표 위치에 기물 있음
            "5, 5, 8, 5, true, true", // 우 3칸 이동, 목표 위치에 기물 있음


            "5, 5, 5, 4, false, true", // 상 1칸 이동, 목표 위치에 기물 없음
            "5, 5, 5, 6, false, true", // 하 1칸 이동, 목표 위치에 기물 없음
            "5, 5, 4, 5, false, true", // 좌 1칸 이동, 목표 위치에 기물 없음
            "5, 5, 6, 5, false, true", // 우 1칸 이동, 목표 위치에 기물 없음

            "5, 5, 5, 3, false, true", // 상 2칸 이동, 목표 위치에 기물 없음
            "5, 5, 5, 7, false, true", // 하 2칸 이동, 목표 위치에 기물 없음
            "5, 5, 3, 5, false, true", // 좌 2칸 이동, 목표 위치에 기물 없음
            "5, 5, 7, 5, false, true", // 우 2칸 이동, 목표 위치에 기물 없음

            "5, 5, 5, 2, false, true", // 상 3칸 이동, 목표 위치에 기물 없음
            "5, 5, 5, 8, false, true", // 하 3칸 이동, 목표 위치에 기물 없음
            "5, 5, 2, 5, false, true", // 좌 3칸 이동, 목표 위치에 기물 없음
            "5, 5, 8, 5, false, true", // 우 3칸 이동, 목표 위치에 기물 없음

            "5, 5, 6, 6, false, false", // 우하 대각선
            "5, 5, 6, 4, false, false", // 우상 대각선
            "5, 5, 4, 4, false, false", // 좌상 대각선
            "5, 5, 4, 6, false, false",  // 좌하 대각선
    })
    void 차는_상하좌우로만_이동하여_적을_잡을수있다(
            int startX, int startY,
            int endX, int endY,
            boolean isEnemyExist,
            boolean expected
    ) {
        // given
        final Team myTeam = Team.CHO;
        final Team opponentTeam = Team.HAN;

        Piece me = Piece.of(PieceType.CHARIOT, myTeam);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        // 피스 세팅
        boardPieces.setPiece(from, me);
        if (isEnemyExist) {
            boardPieces.setPiece(to, Piece.of(PieceType.SOLDIER, opponentTeam));
        }

        // when
        boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 5, 4, false", // 상 1칸 이동
            "5, 5, 5, 6, false", // 하 1칸 이동
            "5, 5, 4, 5, false", // 좌 1칸 이동
            "5, 5, 6, 5, false", // 우 1칸 이동

            "5, 5, 5, 3, false", // 상 2칸 이동
            "5, 5, 5, 7, false", // 하 2칸 이동
            "5, 5, 3, 5, false", // 좌 2칸 이동
            "5, 5, 7, 5, false", // 우 2칸 이동

            "5, 5, 5, 2, false", // 상 3칸 이동
            "5, 5, 5, 8, false", // 하 3칸 이동
            "5, 5, 2, 5, false", // 좌 3칸 이동
            "5, 5, 8, 5, false", // 우 3칸 이동

            "5, 5, 6, 6, false", // 우하 대각선
            "5, 5, 6, 4, false", // 우상 대각선
            "5, 5, 4, 4, false", // 좌상 대각선
            "5, 5, 4, 6, false"  // 좌하 대각선
    })
    void 차는_상하좌우로만_이동할수있지만_아군은_잡을순_없다(
            int startX, int startY,
            int endX, int endY,
            boolean expected
    ) {
        // given
        final Team myTeam = Team.CHO;

        Piece me = Piece.of(PieceType.CHARIOT, myTeam);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        // 피스 세팅
        boardPieces.setPiece(from, me);
        boardPieces.setPiece(to, Piece.of(PieceType.SOLDIER, myTeam)); // 목표 위치에 아군 기물 세팅

        // when
        boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "3,7,   4,8",
            "5,7,   4,8",
            "3,9,   4,8",
            "5,9,   4,8"
    })
    void 차는_궁성에서_정가운데_위치에서_네각으로_대각선_이동_할_수_있다(
            int startX, int startY,
            int definationX, int destinationY) {
        // given
        Piece chariot = Piece.of(PieceType.CHARIOT, Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardPieces.setPiece(currentPosition, chariot);

        Position destinationPosition = new Position(definationX, destinationY);

        // when & then
        Assertions.assertTrue(chariot.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "3,7,   4,8",
            "5,7,   4,8",
            "3,9,   4,8",
            "5,9,   4,8",

            "3,7,   5,9",
            "5,7,   3,9",
            "3,9,   5,7",
            "5,9,   3,7"
    })
    void 차는_궁의_네각에서_정가운데_혹은_끝단으로_이동_할_수_있다(
            int startX, int startY,
            int destinationX, int destinationY) {
        // given
        Piece king = Piece.of(PieceType.CHARIOT, Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardPieces.setPiece(currentPosition, king);

        Position destinationPosition = new Position(destinationX, destinationY);

        // when & then
        Assertions.assertTrue(king.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @Test
    void 차는_궁성_밖에서_대각선_이동_할_수_없다() {
        // given
        Piece chariot = Piece.of(PieceType.CHARIOT, Team.CHO);
        Position currentPosition = new Position(5, 5);

        boardPieces.setPiece(currentPosition, chariot);

        Position destinationPosition = new Position(6, 6); // 대각선 이동

        // when
        boolean canMove = chariot.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader());

        // then
        Assertions.assertFalse(canMove);
    }
}
