package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardPiecesInitializer;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardPieces;

public class HorseTest {

    private BoardPieces boardPieces = new BoardPieces();

    @BeforeEach
    void setUp() {
        new EmptyBoardPiecesInitializer().initBoardStatus(boardPieces);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 7, 4",
            "5, 5, 7, 6",
            "5, 5, 6, 7",
            "5, 5, 6, 3",
            "5, 5, 4, 3",
            "5, 5, 3, 4",
            "5, 5, 3, 6",
            "5, 5, 4, 7"
    })
    void 마는_장애물이_없으면_날일자_경로로_이동할_수_있다(int startX, int startY, int endX, int endY){
        // given
        Piece horse = Piece.of(PieceType.HORSE, Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        boardPieces.setPiece(from, horse);
        // when & then
        Assertions.assertTrue(horse.canMove(from, to, boardPieces.getBoardStateReader()));
    }
    @ParameterizedTest
    @CsvSource({
            "5, 5, 7, 4, 6, 5",
            "5, 5, 7, 6, 6, 5",
            "5, 5, 6, 7, 5, 6"
    })
    void 마는_장애물이_존재하면_날일자_경로로_이동할_수_없다(int startX, int startY, int endX, int endY, int obstacleX, int obstacleY) {
        // given
        Piece horse = Piece.of(PieceType.HORSE, Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        Piece obstacle = Piece.of(PieceType.SOLDIER, Team.CHO);
        Position obstaclePosition = new Position(obstacleX, obstacleY);
        boardPieces.setPiece(from, horse);
        boardPieces.setPiece(obstaclePosition, obstacle);

        // when & then
        Assertions.assertFalse(horse.canMove(from, to, boardPieces.getBoardStateReader()));

    }
}
