package io;

import domain.board.Board;
import domain.score.PieceScore;
import domain.palace.Palace;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.PiecePosition;
import domain.board.Position;
import domain.board.Route;
import domain.piece.TeamColor;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final int INNER_CELL_WIDTH = 3;
    private static final String FULL_WIDTH_SPACE = "　";
    private static final String GAME_START_MESSAGE = "장기 게임을 시작합니다.";
    private static final String RESUME_GAME_MESSAGE = "진행 중인 게임이 있습니다.";
    private static final String RESUME_GAME_OPTION = "1. 이어하기";
    private static final String NEW_GAME_OPTION = "2. 새 게임 시작";
    private static final String FORMATION_SELECTION_MESSAGE = " 상차림을 선택하세요.";
    private static final String INNER_FORMATION_OPTION = "1. 안상차림 (마상상마)";
    private static final String OUTER_FORMATION_OPTION = "2. 바깥상차림 (상마마상)";
    private static final String LEFT_FORMATION_OPTION = "3. 좌상차림 (마상마상)";
    private static final String RIGHT_FORMATION_OPTION = "4. 우상차림 (상마상마)";
    private static final String CURRENT_TURN_MESSAGE = "현재 턴: ";
    private static final String PIECE_SELECTION_MESSAGE = "선택 가능한 기물:";
    private static final String ROUTE_SELECTION_MESSAGE = "이동 가능한 경로:";
    private static final String BACK_OPTION_MESSAGE = "0. 뒤로가기";
    private static final String CURRENT_BOARD_MESSAGE = "현재 장기판";
    private static final String SCORE_MESSAGE_FORMAT = "초 점수: %.1f, 한 점수: %.1f";
    private static final String BOARD_PREFIX = "　　｜";
    private static final String ROW_SEPARATOR = "｜";
    private static final String DIVIDER_SYMBOL = "＝";
    private static final String ROUTE_FORMAT = "%d. %s -> %s";
    private static final String PIECE_OPTION_FORMAT = "%d. %s%s";
    private static final String MOVE_RESULT_FORMAT = "%s 가 %s 로 이동했습니다.";
    private static final String WINNER_MESSAGE_FORMAT = "게임 종료. %s 승리";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final Map<PieceType, String> PIECE_SYMBOLS = createPieceSymbols();
    private static final String CHO_COLOR = "\u001B[38;5;71m";
    private static final String HAN_COLOR = "\u001B[38;5;167m";
    private static final String PALACE_FRAME_COLOR = "\u001B[38;5;180m";
    private static final String RESET = "\u001B[0m";
    private static final Palace CHO_PALACE = Palace.of(TeamColor.CHO);
    private static final Palace HAN_PALACE = Palace.of(TeamColor.HAN);

    public void printGameStart() {
        System.out.println(GAME_START_MESSAGE);
    }

    public void printGameStartOptions() {
        System.out.println(RESUME_GAME_MESSAGE);
        System.out.println(RESUME_GAME_OPTION);
        System.out.println(NEW_GAME_OPTION);
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

    public void printPieceOptions(List<PiecePosition> pieces) {
        System.out.println(PIECE_SELECTION_MESSAGE);
        for (int index = 0; index < pieces.size(); index++) {
            final PiecePosition piecePosition = pieces.get(index);
            System.out.println(PIECE_OPTION_FORMAT.formatted(index + 1, formatPiece(piecePosition.piece()), piecePosition.position()));
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

    public void printBoard(Board board, PieceScore pieceScore) {
        System.out.println();
        System.out.println(CURRENT_BOARD_MESSAGE);
        System.out.println(SCORE_MESSAGE_FORMAT.formatted(pieceScore.cho(), pieceScore.han()));
        System.out.println(createBoardHeader());
        System.out.println(createBoardDivider());
        for (int row = 0; row <= Position.maxRow(); row++) {
            final StringBuilder line = new StringBuilder();
            line.append(toFullWidthNumber(row)).append(FULL_WIDTH_SPACE).append(ROW_SEPARATOR);
            for (int column = 0; column <= Position.maxColumn(); column++) {
                final Position position = Position.of(row, column);
                final Optional<Piece> piece = board.findPiece(position);
                line.append(formatBoardCell(position, piece));
                if (column < Position.maxColumn()) {
                    line.append(ROW_SEPARATOR);
                }
            }
            System.out.println(line);
            if (row < Position.maxRow()) {
                System.out.println(createBoardDivider());
            }
        }
        System.out.println(createBoardDivider());
        System.out.println(createBoardHeader());
    }

    public void printMoveResult(Piece piece, Position destination) {
        System.out.println(MOVE_RESULT_FORMAT.formatted(formatPiece(piece), destination));
    }

    public void printWinner(TeamColor teamColor) {
        System.out.println(WINNER_MESSAGE_FORMAT.formatted(teamColor.displayName()));
    }

    public void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    private String formatBoardCell(Position position, Optional<Piece> piece) {
        final boolean isPalacePosition = isPalacePosition(position);
        final String leftFrame = framePrefix(isPalacePosition);
        final String rightFrame = frameSuffix(isPalacePosition);

        if (piece.isEmpty()) {
            return createCell("", leftFrame, rightFrame);
        }
        final Piece actualPiece = piece.get();
        final String cell = createCell(formatPiece(actualPiece), leftFrame, rightFrame);
        final String foregroundColor = findTeamColor(actualPiece);

        return foregroundColor + cell + RESET;
    }

    private String formatPiece(Piece piece) {
        return findPieceSymbol(piece.getPieceType());
    }

    private String findPieceSymbol(PieceType pieceType) {
        return Optional.ofNullable(PIECE_SYMBOLS.get(pieceType))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 기물 타입입니다."));
    }

    private String createBoardHeader() {
        final StringBuilder header = new StringBuilder(BOARD_PREFIX);
        for (int column = 0; column <= Position.maxColumn(); column++) {
            header.append(createCell(toFullWidthNumber(column)));
            if (column < Position.maxColumn()) {
                header.append(ROW_SEPARATOR);
            }
        }
        return header.toString();
    }

    private String createBoardDivider() {
        final int contentWidth = (Position.maxColumn() + 1) * createCell("").length() + Position.maxColumn() * ROW_SEPARATOR.length();
        return BOARD_PREFIX + DIVIDER_SYMBOL.repeat(contentWidth);
    }

    private String createCell(String content) {
        return createCell(content, "［", "］");
    }

    private String createCell(String content, String leftFrame, String rightFrame) {
        return leftFrame + center(content, INNER_CELL_WIDTH) + rightFrame;
    }

    private boolean isPalacePosition(Position position) {
        return CHO_PALACE.contains(position) || HAN_PALACE.contains(position);
    }

    private String findTeamColor(Piece piece) {
        if (piece.getTeamColor() == TeamColor.CHO) {
            return CHO_COLOR;
        }
        return HAN_COLOR;
    }

    private String framePrefix(boolean isPalacePosition) {
        if (isPalacePosition) {
            return PALACE_FRAME_COLOR + "［" + RESET;
        }
        return "［";
    }

    private String frameSuffix(boolean isPalacePosition) {
        if (isPalacePosition) {
            return PALACE_FRAME_COLOR + "］" + RESET;
        }
        return "］";
    }

    private String center(String content, int width) {
        final int padding = Math.max(0, width - content.length());
        final int leftPadding = padding / 2;
        final int rightPadding = padding - leftPadding;
        return FULL_WIDTH_SPACE.repeat(leftPadding) + content + FULL_WIDTH_SPACE.repeat(rightPadding);
    }

    private String toFullWidthNumber(int number) {
        return String.valueOf((char) ('０' + number));
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
