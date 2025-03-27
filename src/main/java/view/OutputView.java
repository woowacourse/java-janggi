package view;

import domain.board.BoardLocation;
import domain.game.Turn;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.support.BoardComparator;
import view.support.OutputSupporter;

public class OutputView {

    private final OutputSupporter outputSupporter;

    public OutputView(OutputSupporter outputSupporter) {
        this.outputSupporter = outputSupporter;
    }

    public void showBoard(Map<BoardLocation, Piece> pieces) {
        Map<BoardLocation, Piece> filledPieces = outputSupporter.fillBoard(pieces);
        List<BoardLocation> locations = new ArrayList<>(filledPieces.keySet());
        locations.sort(new BoardComparator());

        StringBuilder builder = new StringBuilder();
        builder.append("   1   2   3   4   5   6   7   8   9").append("\n");

        BoardLocation printPosition = locations.getFirst();
        builder.append(printPosition.y()).append(" ");

        for (BoardLocation position : locations) {
            Piece piece = filledPieces.get(position);
            if (printPosition.y() != position.y()) {
                builder.append("\n").append(position.y()).append(" ");
                printPosition = position;
            }
            if (piece == null) {
                builder.append("ㅡㅡ ");
                continue;
            }
            String formattedPiece = outputSupporter.formatPiece(piece);
            builder.append(formattedPiece).append(" ");
        }
        System.out.println(builder);
    }

    public void printTurn(Turn turn) {
        System.out.println(outputSupporter.formatTurn(turn.getTeam()) + "턴 입니다.");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
