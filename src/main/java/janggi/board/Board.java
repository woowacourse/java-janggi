package janggi.board;

import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

public class Board {
    private final Input input;
    private final Output output;

    public Board() {
        this.input = new Input();
        this.output = new Output();
    }

    public void run() {
        BoardCho boardCho = new BoardCho(receiveOption(Team.CHO));
        BoardHan boardHan = new BoardHan(receiveOption(Team.HAN));

        output.printBoard(boardHan, boardCho);
    }

    public BoardOption receiveOption(Team team) {
        return BoardOption.of(team, input.readPositionOption(team));
    }
}
