package console;

import dto.BoardDto;
import dto.CommandDto;
import dto.TeamDto;

public class Console {
    private final Input input;
    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }


    public void startGame() {
        output.startGame();
    }

    public void board(BoardDto board) {
        output.board(board);
    }

    public void turn(TeamDto teamDto) {
        output.turn(teamDto);
    }

    public CommandDto command() {
        return input.command();
    }

    public void retry(IllegalArgumentException e) {
        output.retry(e);
    }

    public void result(TeamDto winner) {
        output.result(winner);
    }
}
