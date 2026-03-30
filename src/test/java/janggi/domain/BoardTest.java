package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class BoardTest {

    @ParameterizedTest
    @CsvSource({

            "CHO,KING,5,9",
            "CHO,SA,4,10","CHO,SA,6,10",
            "CHO,SANG,3,10","CHO,SANG,8,10",
            "CHO,MA,2,10","CHO,MA,7,10",
            "CHO,CHA,1,10","CHO,CHA,9,10",
            "CHO,PO,2,8","CHO,PO,8,8",
            "CHO,ZOL,1,7", "CHO,ZOL,3,7", "CHO,ZOL,5,7", "CHO,ZOL,7,7", "CHO,ZOL,9,7",

            "HAN,KING,5,2",
            "HAN,SA,4,1","HAN,SA,6,1",
            "HAN,SANG,3,1","HAN,SANG,8,1",
            "HAN,MA,2,1","HAN,MA,7,1",
            "HAN,CHA,1,1","HAN,CHA,9,1",
            "HAN,PO,2,3","HAN,PO,8,3",
            "HAN,ZOL,1,4", "HAN,ZOL,3,4", "HAN,ZOL,5,4", "HAN,ZOL,7,4", "HAN,ZOL,9,4"

    })
    @DisplayName("초기 위치에 각나라 졸이 있다.")
    void 초기_위치에_각나라_졸_세팅(Team team, PieceType pieceType, int x, int y){
        //given
        Board board = new Board();
        Position position = new Position(x,y);
        Piece zol = new Piece(team,pieceType);

        //when
        Map<Position, Piece> checkZol = board.getBoard();

        //then
        assertThat(checkZol.get(position)).isEqualTo(zol);
    }

    @Test
    @DisplayName("졸의 이동규칙을 통해 갈 수 있는 경로의 위치를 알아낼 수 있다")
    void 졸_이동가능_좌표_확인_다_가능() {
        //given
        Board board = new Board();
        Position position = new Position(5,7);
        board.getBoard().put(position, new Piece(Team.CHO, PieceType.ZOL));
        Position zolUp = new Position(5, 6);
        Position zolLeft = new Position(4, 7);
        Position zolRight = new Position(6, 7);
        List<Position> rightAnswer = List.of(zolUp, zolRight, zolLeft);

        //when
        List<Position> zolRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(rightAnswer).isEqualTo(zolRoutesPositions);
    }

    @Test
    @DisplayName("졸의 이동규칙을 통해 갈 수 있는 경로의 위치를 알아낼 수 있다")
    void 졸_이동가능_좌표_확인_위_불가능() {
        //given
        Board board = new Board();
        Position position = new Position(5,7);
        board.getBoard().put(position, new Piece(Team.CHO, PieceType.ZOL));
        Position zolUp = new Position(5, 6);
        board.getBoard().put(zolUp, new Piece(Team.CHO, PieceType.ZOL));
        Position zolLeft = new Position(4, 7);
        Position zolRight = new Position(6, 7);
        List<Position> rightAnswer = List.of(zolRight, zolLeft);

        //when
        List<Position> zolRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(rightAnswer).isEqualTo(zolRoutesPositions);
    }

    @Test
    @DisplayName("마의 이동규칙을 통해 갈 수 있는 경로의 위치를 알아낼 수 있다")
    void 마_이동가능_좌표_확인_다_가능() {
        //given
        Board board = new Board();
        Position position = new Position(4,6);
        board.getBoard().put(position, new Piece(Team.CHO, PieceType.MA));
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(5, 4);
        Position maPos3 = new Position(6, 5);
        Position maPos4 = new Position(6, 7);
        Position maPos5 = new Position(3, 8);
        Position maPos6 = new Position(5, 8);
        Position maPos7 = new Position(2,5);
        Position maPos8 = new Position(2,7);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7, maPos8);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(8)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("마의 이동규칙을 통해 갈 수 있는 경로의 위치를 알아낼 수 있다")
    void 마_이동가능_좌표_확인_경로에_다른_기물() {
        //given
        Board board = new Board();
        Position position = new Position(4,6);
        board.getBoard().put(position, new Piece(Team.CHO, PieceType.MA));
        board.getBoard().put(new Position(4,5), new Piece(Team.HAN, PieceType.CHA));
        Position maPos1 = new Position(6, 5);
        Position maPos2 = new Position(6, 7);
        Position maPos3 = new Position(3, 8);
        Position maPos4 = new Position(5, 8);
        Position maPos5 = new Position(2,5);
        Position maPos6 = new Position(2,7);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(6)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("마의 이동규칙을 통해 갈 수 있는 경로의 위치를 알아낼 수 있다")
    void 마_이동가능_좌표_확인_목적지에_같은팀_기물() {
        //given
        Board board = new Board();
        Position position = new Position(4,6);
        board.getBoard().put(position, new Piece(Team.CHO, PieceType.MA));
        board.getBoard().put(new Position(2,7), new Piece(Team.CHO, PieceType.CHA));
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(5, 4);
        Position maPos3 = new Position(6, 5);
        Position maPos4 = new Position(6, 7);
        Position maPos5 = new Position(3, 8);
        Position maPos6 = new Position(5, 8);
        Position maPos7 = new Position(2,5);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(7)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }
}
