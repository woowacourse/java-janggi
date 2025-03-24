package janggi.view;

import janggi.Board;
import janggi.Player;
import janggi.Players;
import janggi.Team;
import janggi.coordinate.Column;
import janggi.coordinate.Position;
import janggi.coordinate.Row;
import janggi.piece.Piece;
import janggi.piece.PieceType;

import java.util.List;

public class OutputView {

    private static String getPieceName(final Piece piece) {
        String pieceName = piece.getType().getDescription();

        if (piece.getTeam().isHan()) {
            if (piece.getType() == PieceType.SOLDIER) {
                pieceName = "병";
            }
            pieceName = processRedColorString(pieceName);
        }

        if (piece.getTeam().isCho()) {
            if (piece.getType() == PieceType.SOLDIER) {
                pieceName = "졸";
            }
            pieceName = processGreenColorString(pieceName);
        }
        return pieceName;
    }

    private static String processRedColorString(String pieceName) {
        return "\u001B[31m" + pieceName + "\u001B[0m";
    }

    private static String processGreenColorString(final String pieceName) {
        return "\u001B[32m" + pieceName + "\u001B[0m";
    }

    public void displayBoard(final Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        String space = " ";
        String blank = " ＿ ";

        stringBuilder
                .append(space)
                .append(space)
                .append(space);

        for (String number : List.of("１", "２", "３", "４", "５", "６", "７", "８", "９")) {
            stringBuilder
                    .append(space)
                    .append(number)
                    .append(space);
        }
        stringBuilder.append("\n");

        for (Row row : Row.defaults()) {
            stringBuilder
                    .append(String.format("%2d", row.value()))
                    .append(space);
            for (Column column : Column.defaults()) {
                Position position = new Position(row, column);

                if (board.isExists(position)) {
                    Piece piece = board.getPiece(position);
                    String pieceName = getPieceName(piece);

                    stringBuilder
                            .append(space)
                            .append(pieceName)
                            .append(space);
                    continue;
                }
                stringBuilder.append(blank);
            }
            stringBuilder.append("\n");
        }


        System.out.println(stringBuilder);
    }

    public void displayScore(final Players players) {
        for (Team team : Team.values()) {
            display(String.format("%s나라 점수: %d",
                    team.getDescription(),
                    players.getPlayer(team).getScore().value()));
        }
    }

    public void displayWinner(final Player player) {
        display(player.getTeam().getDescription() + "나라가 이겼습니다.");
    }

    public void displayError(final String message) {
        display("[ERROR] " + message);
    }

    public void displayError() {
        displayError("올바른 입력이 필요합니다.");
    }

    public void display(final String message) {
        System.out.println(message);
        System.out.println();
    }
}
