package io;

import domain.Board;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final String CHO_COLOR = "\u001B[38;5;71m";
    private static final String HAN_COLOR = "\u001B[38;5;167m";
    private static final String RESET = "\u001B[0m";

    public void printGameStart() {
        System.out.println("장기 게임을 시작합니다.");
    }

    public void printFormationSelectionPrompt(TeamColor teamColor) {
        System.out.println(teamColor.displayName() + " 상차림을 선택하세요.");
        System.out.println("1. 안상차림");
        System.out.println("2. 바깥상차림");
        System.out.println("3. 좌상차림");
        System.out.println("4. 우상차림");
    }

    public void printCurrentTurn(TeamColor teamColor) {
        System.out.println();
        System.out.println("현재 턴: " + teamColor.displayName());
    }

    public void printPieceOptions(List<Map.Entry<Position, Piece>> pieces) {
        System.out.println("선택 가능한 기물:");
        for (int index = 0; index < pieces.size(); index++) {
            Map.Entry<Position, Piece> entry = pieces.get(index);
            System.out.println((index + 1) + ". " + formatPiece(entry.getValue()) + entry.getKey());
        }
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
                Optional<Piece> piece = board.findPiece(Position.of(row, column));
                line.append(" ").append(formatBoardCell(piece)).append(" │");
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

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    private String formatBoardCell(Optional<Piece> piece) {
        if (piece.isEmpty()) {
            return "  ";
        }
        Piece actualPiece = piece.get();
        String symbol = formatBoardSymbol(actualPiece);

        if (actualPiece.getTeamColor() == TeamColor.CHO) {
            return CHO_COLOR + symbol + RESET;
        }
        return HAN_COLOR + symbol + RESET;
    }

    private String formatPiece(Piece piece) {
        if (piece.getPieceType() == PieceType.ROOK) {
            return "차";
        }
        if (piece.getPieceType() == PieceType.HORSE) {
            return "마";
        }
        if (piece.getPieceType() == PieceType.ELEPHANT) {
            return "상";
        }
        if (piece.getPieceType() == PieceType.GUARD) {
            return "사";
        }
        if (piece.getPieceType() == PieceType.KING) {
            return "왕";
        }
        if (piece.getPieceType() == PieceType.CANNON) {
            return "포";
        }
        if (piece.getPieceType() == PieceType.PAWN) {
            return "졸";
        }
        throw new IllegalArgumentException("지원하지 않는 기물 타입입니다.");
    }

    private String formatBoardSymbol(Piece piece) {
        if (piece.getPieceType() == PieceType.ROOK) {
            return "차";
        }
        if (piece.getPieceType() == PieceType.HORSE) {
            return "마";
        }
        if (piece.getPieceType() == PieceType.ELEPHANT) {
            return "상";
        }
        if (piece.getPieceType() == PieceType.GUARD) {
            return "사";
        }
        if (piece.getPieceType() == PieceType.KING) {
            return "왕";
        }
        if (piece.getPieceType() == PieceType.CANNON) {
            return "포";
        }
        if (piece.getPieceType() == PieceType.PAWN) {
            return "졸";
        }
        throw new IllegalArgumentException("지원하지 않는 기물 타입입니다.");
    }
}
