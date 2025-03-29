package view;

import domain.board.BoardLocation;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.Score;
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
        String location = inputView.requestStartLocation();
        String[] locationPart = location.split(",");
        return new BoardLocation(Integer.parseInt(locationPart[0]), Integer.parseInt(locationPart[1]));
    }

    public BoardLocation requestDestination() {
        String location = inputView.requestDestination();
        String[] locationPart = location.split(",");
        return new BoardLocation(Integer.parseInt(locationPart[0]), Integer.parseInt(locationPart[1]));
    }

    public void showTurn(Turn turn) {
        outputView.showTurn(turn);
    }

    public Map<BoardLocation, Piece> requestPlacements() {
        Map<BoardLocation, Piece> placements = new HashMap<>();
        Map<BoardLocation, Piece> hanPlacements = inputView.requestHanPlacements();
        Map<BoardLocation, Piece> choPlacements = inputView.requestChoPlacements();
        placements.putAll(hanPlacements);
        placements.putAll(choPlacements);
        return placements;
    }

    public void showMessage(String message) {
        outputView.showMessage(message);
    }

    public void showScore(Score hanScore, Score choScore) {
        outputView.printScore(hanScore, choScore);
    }

    public void showWinner(Turn turn) {
        turn.opposite();
        outputView.showWinner(turn);
    }
}
