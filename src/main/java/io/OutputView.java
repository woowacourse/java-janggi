package io;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final int BOARD_LAST_ROW = 9;
    private static final int BOARD_LAST_COLUMN = 8;
    private static final String EMPTY_CELL = "  ";
    private static final String GAME_START_MESSAGE = "장기 게임을 시작합니다.";
    private static final String FORMATION_SELECTION_MESSAGE = " 상차림을 선택하세요.";
    private static final String INNER_FORMATION_OPTION = "1. 안상차림";
    private static final String OUTER_FORMATION_OPTION = "2. 바깥상차림";
    private static final String LEFT_FORMATION_OPTION = "3. 좌상차림";
    private static final String RIGHT_FORMATION_OPTION = "4. 우상차림";
    private static final String CURRENT_TURN_MESSAGE = "현재 턴: ";
    private static final String PIECE_SELECTION_MESSAGE = "선택 가능한 기물:";
    private static final String ROUTE_SELECTION_MESSAGE = "이동 가능한 경로:";
    private static final String BACK_OPTION_MESSAGE = "0. 뒤로가기";
    private static final String CURRENT_BOARD_MESSAGE = "현재 장기판";
    private static final String BOARD_HEADER = "      0    1    2    3    4    5    6    7    8";
    private static final String BOARD_TOP_BORDER = "   ┌────┬────┬────┬────┬────┬────┬────┬────┬────┐";
    private static final String BOARD_MIDDLE_BORDER = "   ├────┼────┼────┼────┼────┼────┼────┼────┼────┤";
    private static final String BOARD_BOTTOM_BORDER = "   └────┴────┴────┴────┴────┴────┴────┴────┴────┘";
    private static final String ROW_PREFIX_FORMAT = "%2d │";
    private static final String CELL_SEPARATOR = " │";
    private static final String ROUTE_FORMAT = "%d. %s -> %s";
    private static final String PIECE_OPTION_FORMAT = "%d. %s%s";
    private static final String MOVE_RESULT_FORMAT = "%s 가 %s 로 이동했습니다.";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final Map<PieceType, String> PIECE_SYMBOLS = createPieceSymbols();
    private static final String CHO_COLOR = "\u001B[38;5;71m";
    private static final String HAN_COLOR = "\u001B[38;5;167m";
    private static final String RESET = "\u001B[0m";

    public void printGameStart() {
        System.out.println(GAME_START_MESSAGE);
    }

    public void printFormationSelectionPrompt(TeamColor teamColor) {
        System.out.println(teamColor.displayName() + FORMATION_SELECTION_MESSAGE);
        System.out.println(INNER_FORMATION_OPTION);
        System.out.println(OUTER_FORMATION_OPTION);
        System.out.println(LEFT_FORMATION_OPTION);
        System.out.println(RIGHT_FORMATION_OPTION);
    }

    public void printCurrentTurn(TeamColor teamColor) {
        System.out.println();
        System.out.println(CURRENT_TURN_MESSAGE + teamColor.displayName());
    }

    public void printPieceOptions(List<Map.Entry<Position, Piece>> pieces) {
        System.out.println(PIECE_SELECTION_MESSAGE);
        for (int index = 0; index < pieces.size(); index++) {
            final Map.Entry<Position, Piece> entry = pieces.get(index);
            System.out.println(PIECE_OPTION_FORMAT.formatted(index + 1, formatPiece(entry.getValue()), entry.getKey()));
        }
    }

    public void printRouteOptions(List<Route> routes) {
        System.out.println(ROUTE_SELECTION_MESSAGE);
        System.out.println(BACK_OPTION_MESSAGE);
        for (int index = 0; index < routes.size(); index++) {
            final Route route = routes.get(index);
            System.out.println(ROUTE_FORMAT.formatted(index + 1, route.startPos(), route.endPos()));
        }
    }

    public void printBoard(Board board) {
        System.out.println();
        System.out.println(CURRENT_BOARD_MESSAGE);
        System.out.println(BOARD_HEADER);
        System.out.println(BOARD_TOP_BORDER);
        for (int row = 0; row <= BOARD_LAST_ROW; row++) {
            final StringBuilder line = new StringBuilder();
            line.append(ROW_PREFIX_FORMAT.formatted(row));
            for (int column = 0; column <= BOARD_LAST_COLUMN; column++) {
                final Optional<Piece> piece = board.findPiece(Position.of(row, column));
                line.append(" ").append(formatBoardCell(piece)).append(CELL_SEPARATOR);
            }
            System.out.println(line);
            if (row < BOARD_LAST_ROW) {
                System.out.println(BOARD_MIDDLE_BORDER);
            }
        }
        System.out.println(BOARD_BOTTOM_BORDER);
    }

    public void printMoveResult(Piece piece, Position destination) {
        System.out.println(MOVE_RESULT_FORMAT.formatted(formatPiece(piece), destination));
    }

    public void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    private String formatBoardCell(Optional<Piece> piece) {
        if (piece.isEmpty()) {
            return EMPTY_CELL;
        }
        final Piece actualPiece = piece.get();
        final String symbol = formatBoardSymbol(actualPiece);

        if (actualPiece.getTeamColor() == TeamColor.CHO) {
            return CHO_COLOR + symbol + RESET;
        }
        return HAN_COLOR + symbol + RESET;
    }

    private String formatPiece(Piece piece) {
        return findPieceSymbol(piece.getPieceType());
    }

    private String formatBoardSymbol(Piece piece) {
        return findPieceSymbol(piece.getPieceType());
    }

    private String findPieceSymbol(PieceType pieceType) {
        return Optional.ofNullable(PIECE_SYMBOLS.get(pieceType))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 기물 타입입니다."));
    }

    private static Map<PieceType, String> createPieceSymbols() {
        final Map<PieceType, String> pieceSymbols = new EnumMap<>(PieceType.class);
        pieceSymbols.put(PieceType.ROOK, "차");
        pieceSymbols.put(PieceType.HORSE, "마");
        pieceSymbols.put(PieceType.ELEPHANT, "상");
        pieceSymbols.put(PieceType.GUARD, "사");
        pieceSymbols.put(PieceType.KING, "왕");
        pieceSymbols.put(PieceType.CANNON, "포");
        pieceSymbols.put(PieceType.PAWN, "졸");
        return pieceSymbols;
    }
}


