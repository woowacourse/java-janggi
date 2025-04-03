package janggi.view;

import janggi.GameContext;
import janggi.board.Board;
import janggi.command.Command;
import janggi.command.CommandType;
import janggi.coordinate.Column;
import janggi.coordinate.Position;
import janggi.coordinate.Row;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.player.Player;
import janggi.player.Team;

import java.util.List;

public class OutputView {

    public void displayBoard(final Board board) {
        final StringBuilder stringBuilder = new StringBuilder();
        final String space = " ";
        final String blank = " ＿ ";

        stringBuilder
                .append(space)
                .append(space)
                .append(space);

        for (final String number : List.of("１", "２", "３", "４", "５", "６", "７", "８", "９")) {
            stringBuilder
                    .append(space)
                    .append(number)
                    .append(space);
        }
        stringBuilder.append("\n");

        for (final Row row : Row.defaults()) {
            stringBuilder
                    .append(String.format("%2d", row.value()))
                    .append(space);
            for (final Column column : Column.defaults()) {
                final Position position = new Position(row, column);

                if (board.isExists(position)) {
                    final Piece piece = board.getPiece(position);
                    final String pieceName = getPieceName(piece);

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

    public void displayScore(final GameContext context) {
        for (final Team team : Team.values()) {
            display(String.format("%s나라 점수: %d",
                    team.getDescription(),
                    context.getScore(team).value()));
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

    public void display(final Command command) {
        if (command.getType() == CommandType.SAVE) {
            displaySave();

        }
        if (command.getType() == CommandType.QUIT) {
            displayQuit();
        }
    }

    public void displayQuit() {
        display("게임을 종료합니다.");
    }

    private void displaySave() {
        display("게임을 저장합니다.");
    }

    private void display(final String message) {
        System.out.println(message);
        System.out.println();
    }

    private String getPieceName(final Piece piece) {
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

    private String processRedColorString(final String pieceName) {
        return "\u001B[31m" + pieceName + "\u001B[0m";
    }

    private String processGreenColorString(final String pieceName) {
        return "\u001B[32m" + pieceName + "\u001B[0m";
    }

}
