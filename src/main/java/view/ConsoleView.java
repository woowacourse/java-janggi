package view;

import domain.board.BoardLocation;
import domain.piece.Team;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class ConsoleView {

    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleView(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void showBoard(Map<BoardLocation, Piece> pieces) {
        outputView.showBoard(pieces);
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

    public void printTurn(Team team) {
        outputView.printTurn(team);
    }

    public Map<BoardLocation, Piece> requestPlacements() {
        Map<BoardLocation, Piece> placements = new HashMap<>();
        Map<BoardLocation, Piece> hanPlacements = inputView.requestHanPlacements();
        Map<BoardLocation, Piece> choPlacements = inputView.requestChoPlacements();
        placements.putAll(hanPlacements);
        placements.putAll(choPlacements);
        return placements;
    }

    public void printMessage(String message) {
        outputView.printMessage(message);
    }
}
