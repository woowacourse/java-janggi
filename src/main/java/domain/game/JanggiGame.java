package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.formation.FormationType;
import domain.coordination.Coordination;
import domain.piece.Team;
import java.util.List;
import view.dto.BoardDto;

public class JanggiGame {

    private final Board board;
    private Turn turn = Turn.CHO;

    private JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(FormationType choFormat, FormationType hanFormat) {
        return new JanggiGame(BoardFactory.create(choFormat, hanFormat));
    }

    static JanggiGame from(Board board) {
        return new JanggiGame(board);
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
        updateTurn();
    }

    public BoardDto createBoardDto() {
        return BoardDto.from(board.getBoard());
    }

    public void checkSameTeam(List<Integer> pieceLocation, Turn turn) {
        board.checkSameTeam(Coordination.of(pieceLocation.get(0), pieceLocation.get(1)), turn);
    }

    public double scoreOf(Team team) {
        return board.scoreOf(team);
    }

    private void updateTurn() {
        if (isGameEnd()) {
            return;
        }
        turn = turn.reverse();
    }
}
