package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardPiecesInitializer;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardPieces;

public class ElephantTest {
    private final BoardPieces boardPieces = new BoardPieces();

    @BeforeEach
    void setUp() {
        new EmptyBoardPiecesInitializer().initBoardStatus(boardPieces);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 7, 2",
            "5, 5, 8, 3",
            "5, 5, 3, 2",
            "5, 5, 2, 3",
            "5, 5, 2, 7",
            "5, 5, 3, 8",
            "5, 5, 8, 7",
            "5, 5, 7, 8"
    })
    void 상은_상하좌우_한칸_후_대각선_두칸_위치로_이동한다(int startX, int startY, int endX, int endY) {
        // given
        Piece elephant = Piece.of(PieceType.ELEPHANT, Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        boardPieces.setPiece(from, elephant);

        // then & then
        Assertions.assertTrue(elephant.canMove(from, to, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 7, 2, 5, 4",
            "5, 5, 7, 2, 6, 3",
            "5, 5, 3, 2, 5, 4",
            "5, 5, 3, 2, 4, 3",
    })
    void 상은_이동_경로_중간에_기물이_있으면_멱에_걸린다(int startX, int startY, int endX, int endY, int obstacleX, int obstacleY) {
        // given
        Piece elephant = Piece.of(PieceType.ELEPHANT, Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        Piece obstacle = Piece.of(PieceType.SOLDIER, Team.HAN);
        Position obstaclePosition = new Position(obstacleX, obstacleY);

        boardPieces.setPiece(from, elephant);
        boardPieces.setPiece(obstaclePosition, obstacle);

        // when & then
        Assertions.assertFalse(elephant.canMove(from, to, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 3, 3",
            "5, 5, 7 ,7",
            "5, 5, 6, 6",
            "5, 5, 10, 11"
    })
    void 상은_정해진_행마법_이외의_곳으로는_이동할_수_없다(int startX, int startY, int endX, int endY){
        Piece elephant = Piece.of(PieceType.ELEPHANT, Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        boardPieces.setPiece(from, elephant);

        // then & then
        Assertions.assertFalse(elephant.canMove(from, to, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 7, 2",
            "5, 5, 8, 3",
            "5, 5, 3, 2",
            "5, 5, 2, 3",
            "5, 5, 2, 7",
            "5, 5, 3, 8",
            "5, 5, 8, 7",
            "5, 5, 7, 8"
    })
    void 상은_목적지에_아군_기물이_있으면_이동할_수_없다(int startX, int startY, int endX, int endY){
        // given
        Piece elephant = Piece.of(PieceType.ELEPHANT, Team.CHO);
        Position from = new Position(startX, startY);

        Piece soldier = Piece.of(PieceType.SOLDIER, Team.CHO);
        Position to = new Position(endX, endY);

        // when
        boardPieces.setPiece(from, elephant);
        boardPieces.setPiece(to, soldier);
        Assertions.assertFalse(elephant.canMove(from, to, boardPieces.getBoardStateReader()));
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 7, 2",
            "5, 5, 8, 3",
            "5, 5, 3, 2",
            "5, 5, 2, 3",
            "5, 5, 2, 7",
            "5, 5, 3, 8",
            "5, 5, 8, 7",
            "5, 5, 7, 8"
    })
    void 상은_목적지의_상대_기물을_잡으며_이동할_수_있다(int startX, int startY, int endX, int endY){
        // given
        Piece elephant = Piece.of(PieceType.ELEPHANT, Team.CHO);
        Position from = new Position(startX, startY);

        Piece soldier = Piece.of(PieceType.SOLDIER, Team.HAN);
        Position to = new Position(endX, endY);

        // when
        boardPieces.setPiece(from, elephant);
        boardPieces.setPiece(to, soldier);
        Assertions.assertTrue(elephant.canMove(from, to, boardPieces.getBoardStateReader()));
    }

}
