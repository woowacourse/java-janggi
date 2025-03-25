package console;

import janggi.position.Board;

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

    public void display(Board board) {
        output.display(board);
    }

    public String[] move() {
        return input.read().split(" ");
    }

    public void retry(IllegalArgumentException e) {
        output.retry(e);
    }

    public void displayTurn(Board board) {
        output.displayTurn(board);
    }

//    public void result(TeamDto winner) {
//        output.result(winner);
//    }
}