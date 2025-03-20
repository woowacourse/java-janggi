package view;

import domain.BoardLocation;
import domain.Turn;

public class ConsoleView {

    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleView(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public String inputHanHorseElephantLocation() {
        return inputView.inputHanHorseElephantLocation();
    }

    public void showBoard() {
        outputView.showBoard();
    }

    public BoardLocation requestCurrent() {
        int x = inputView.requestStartX();
        int y = inputView.requestStartY();
        return new BoardLocation(x, y);
    }

    public BoardLocation requestDestination() {
        int x = inputView.requestDestinationX();
        int y = inputView.requestDestinationY();
        return new BoardLocation(x, y);
    }

    public void printResult(Turn turn) {
        outputView.showResult(turn);
    }

    public void printTurn(Turn turn) {
        outputView.printTurn(turn);
    }
}
