package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.formation.FormationType;
import domain.coordination.Coordination;
import view.dto.BoardDto;
import java.util.List;

public class JanggiGame {

    private final Board board;
    private Turn turn = Turn.CHO;

    private JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(FormationType choFormat, FormationType hanFormat) {
        return new JanggiGame(BoardFactory.create(choFormat, hanFormat));
    }

    public boolean isGameEnd() {
        return !board.hasTwoGenerals();
    }

    public Turn turn() {
        return turn;
    }

    public void playTurn(List<Integer> from, List<Integer> to) {
        board.move(
                Coordination.of(from.get(0), from.get(1)),
                Coordination.of(to.get(0), to.get(1))
        );

        turn = turn.reverse();
    }

    public BoardDto createBoardDto() {
        return BoardDto.from(board.getBoard());
    }

    public void checkSameTeam(List<Integer> pieceLocation, Turn turn) {
        board.checkSameTeam(pieceLocation, turn);
    }
}
