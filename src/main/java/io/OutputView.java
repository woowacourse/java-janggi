package io;

import java.util.List;
import java.util.Map;

import domain.Board;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.TeamScores;

public class OutputView {

    private static final String CHO_COLOR = "\u001B[38;5;71m";
    private static final String HAN_COLOR = "\u001B[38;5;167m";
    private static final String RESET = "\u001B[0m";

    public void printGameStart() {
        System.out.println("장기 게임을 시작합니다.");
    }

    public void printResumePrompt() {
        System.out.println("저장된 게임이 있습니다.");
        System.out.println("1. 이어하기");
        System.out.println("2. 새 게임");
    }

    public void printTimeLimitPrompt() {
        System.out.println("게임 제한 시간을 입력하세요.");
    }

    public void printFormationSelectionPrompt(TeamColor teamColor) {
        System.out.println(teamColor.getDisplayName() + " 상차림을 선택하세요.");
        System.out.println("1. 안상차림");
        System.out.println("2. 바깥상차림");
        System.out.println("3. 좌상차림");
        System.out.println("4. 우상차림");
    }

    public void printCurrentTurn(TeamColor teamColor) {
        System.out.println();
        System.out.println("현재 턴: " + teamColor.getDisplayName());
    }

    public void printPieceOptions(List<Map.Entry<Position, Piece>> pieces) {
        System.out.println("선택 가능한 기물:");
        for (int index = 0; index < pieces.size(); index++) {
            Map.Entry<Position, Piece> entry = pieces.get(index);
            System.out.println((index + 1) + ". " + formatPiece(entry.getValue()) + entry.getKey());
        }
    }

    public void printCheck() {
        System.out.println("장군입니다!");
    }

    public void printRouteOptions(List<Route> routes) {
        System.out.println("이동 가능한 경로:");
        System.out.println("0. 뒤로가기");
        for (int index = 0; index < routes.size(); index++) {
            Route route = routes.get(index);
            System.out.println((index + 1) + ". " + route.startPos() + " -> " + route.endPos());
        }
    }

    public void printBoard(Board board) {
        System.out.println();
        System.out.println("현재 장기판");
        System.out.println("      0    1    2    3    4    5    6    7    8");
        System.out.println("   ┌────┬────┬────┬────┬────┬────┬────┬────┬────┐");
        for (int row = 0; row <= 9; row++) {
            StringBuilder line = new StringBuilder();
            line.append(String.format("%2d │", row));
            for (int column = 0; column <= 8; column++) {
                Position position = Position.of(row, column);
                Piece piece = board.findPiece(position).orElse(null);
                line.append(" ").append(formatBoardCell(board, position, piece)).append(" │");
            }
            System.out.println(line);
            if (row < 9) {
                System.out.println("   ├────┼────┼────┼────┼────┼────┼────┼────┼────┤");
            }
        }
        System.out.println("   └────┴────┴────┴────┴────┴────┴────┴────┴────┘");
    }

    public void printMoveResult(Piece piece, Position destination) {
        System.out.println(formatPiece(piece) + " 가 " + destination + " 로 이동했습니다.");
    }

    public void printGameEnd(TeamColor winner) {
        System.out.println("게임이 종료되었습니다. 승자: " + winner.getDisplayName());
    }

    public void printTimeOverByScore(TeamScores scores, TeamColor winner) {
        if (winner == null) {
            System.out.println(
                    "시간이 종료되었습니다. 무승부입니다. (초: "
                            + scores.pointsFor(TeamColor.CHO).value()
                            + ", 한: "
                            + scores.pointsFor(TeamColor.HAN).value()
                            + ")");
            return;
        }
        System.out.println(
                "시간이 종료되었습니다. 점수 승자: "
                        + winner.getDisplayName()
                        + " (초: "
                        + scores.pointsFor(TeamColor.CHO).value()
                        + ", 한: "
                        + scores.pointsFor(TeamColor.HAN).value()
                        + ")");
    }

    public void printSavedGameEnded(TeamColor winner) {
        if (winner == null) {
            System.out.println("저장된 게임은 종료되어 새 게임을 진행합니다.");
            return;
        }
        System.out.println("저장된 게임은 종료되어 새 게임을 진행합니다. 승자: " + winner.getDisplayName());
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String formatBoardCell(Board board, Position position, Piece piece) {
        if (piece != null) {
            return formatOccupiedCell(piece);
        }
        if (board.isInsidePalace(position)) {
            return formatEmptyPalaceCell(position);
        }
        return "  ";
    }

    private String formatOccupiedCell(Piece piece) {
        String symbol = piece.getPieceType().getDisplayName();
        if (piece.isOnTeam(TeamColor.CHO)) {
            return CHO_COLOR + symbol + RESET;
        }
        return HAN_COLOR + symbol + RESET;
    }

    private String formatEmptyPalaceCell(Position position) {
        if (isPalaceCenterTile(position)) {
            return "\u00B7\u00B7";
        }
        return diagonalCornerMarkRelativeToPalaceCenter(position);
    }

    private boolean isPalaceCenterTile(Position position) {
        if (position.equals(Position.of(1, 4))) {
            return true;
        }
        return position.equals(Position.of(8, 4));
    }

    private String diagonalCornerMarkRelativeToPalaceCenter(Position position) {
        Position center = palaceCenterNear(position);
        int rowDiff = position.row() - center.row();
        int colDiff = position.column() - center.column();
        if (rowDiff == 0 || colDiff == 0) {
            return "  ";
        }
        return cornerSlashFromQuadrant(rowDiff, colDiff);
    }

    private Position palaceCenterNear(Position position) {
        if (position.row() <= 2) {
            return Position.of(1, 4);
        }
        return Position.of(8, 4);
    }

    private String cornerSlashFromQuadrant(int rowDiff, int colDiff) {
        if (rowDiff < 0 && colDiff < 0) {
            return "\u2572 ";
        }
        if (rowDiff < 0 && colDiff > 0) {
            return "\u2571 ";
        }
        if (rowDiff > 0 && colDiff < 0) {
            return "\u2571 ";
        }
        return "\u2572 ";
    }

    private String formatPiece(Piece piece) {
        return piece.getPieceType().getDisplayName();
    }
}
