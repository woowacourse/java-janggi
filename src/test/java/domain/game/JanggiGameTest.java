package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.piece.Piece;
import domain.piece.Team;
import fixture.BoardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("이동가능한 좌표로 이동한다면 위치를 변경한다")
    void process() {
        //given
        Board board = BoardFixture.createBoard();
        JanggiGame janggiGame = new JanggiGame(board, new Turn(Team.HAN));
        BoardLocation current = new BoardLocation(1,1);
        BoardLocation destination = new BoardLocation(1,2);
        Piece expect = board.getPieces().get(current);
        //when
        janggiGame.process(current, destination);
        //then
        Piece result = board.getPieces().get(destination);
        assertThat(result).isEqualTo(expect);
    }
}

