package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.fake.EmptyChessPiecePositionsGenerator;
import domain.fake.ExistCannonPositionsGenerator;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("궁안에서 폰의 이동 가능한 경로를 반환한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(2, 3);
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(
                new EmptyChessPiecePositionsGenerator());
        final Pawn pawn = new Pawn(ChessTeam.BLUE, chessPosition);
        final List<ChessPosition> expectDestinations = List.of(
                new ChessPosition(1,3),
                new ChessPosition(1,4),
                new ChessPosition(2,4),
                new ChessPosition(2,2)
        );

        //when
        final Board board = new Board(piecePositions);
        final List<ChessPosition> availableDestinations = board.getAvailableDestinations(chessPosition, pawn);

        //then
        assertThat(availableDestinations).containsExactlyInAnyOrderElementsOf(expectDestinations);
    }

    @Test
    @DisplayName("궁안에서 차의 이동 경로를 계산하여 반환한다")
    void test2() {
        //given
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(new EmptyChessPiecePositionsGenerator());
        final ChessPosition chessPosition = new ChessPosition(2, 3);
        final Chariot chariot = new Chariot(ChessTeam.BLUE, chessPosition);
        final List<ChessPosition> expectedChessPositions = List.of(
                new ChessPosition(1, 4),
                new ChessPosition(0, 5)
        );

        //when
        final Board board = new Board(piecePositions);
        final List<ChessPosition> availableDestinations = board.getAvailableDestinations(chessPosition, chariot);

        //then
        assertThat(availableDestinations).containsAll(expectedChessPositions);

    }
    
    @Test
    @DisplayName("궁안에서 포의 이동 경로를 계산하여 반환한다")
    void test3() {
        //given
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(new ExistCannonPositionsGenerator());
        final ChessPosition chessPosition = new ChessPosition(2, 3);
        final Cannon cannon = new Cannon(ChessTeam.BLUE, chessPosition);
        final List<ChessPosition> expectedChessPositions = List.of(
                new ChessPosition(0, 5)
        );
        
        //when
        final Board board = new Board(piecePositions);
        final List<ChessPosition> availableDestinations = board.getAvailableDestinations(chessPosition, cannon);

        //then
        assertThat(availableDestinations).containsAll(expectedChessPositions);
        
    }

    @Test
    @DisplayName("상대방의 포가 존재할때 포의 이동 경로를 계산한다")
    void test4() {
        //given
        final ChessPosition chessPosition = new ChessPosition(7, 4);
        final Cannon cannon = new Cannon(ChessTeam.BLUE, chessPosition);
        final ChessPiecePositions piecePositions = ChessPiecePositions.from(new ExistCannonPositionsGenerator());

        final List<ChessPosition> expected = List.of(
                new ChessPosition(7,6),
                new ChessPosition(7,7),
                new ChessPosition(7,8)
        );

        //when
        final Board board = new Board(piecePositions);
        final List<ChessPosition> destinations = board.getAvailableDestinations(chessPosition, cannon);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

}
