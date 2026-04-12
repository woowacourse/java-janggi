package view;

import controller.response.BoardView;
import controller.response.Turn;
import domain.board.ElephantSetup;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Team;

import java.util.List;

public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String EMPTY = "    ";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String BOARD_OUTER_SPACES = "   ";

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }


    public void printEnterPlayerNamePrompt(final Team team) {
        System.out.println(getTeamName(team) + " 플레이어의 이름을 입력하세요(2~5자, 영어만 사용):");
    }

    public void printChoiceElephantSetupPrompt(final Team team) {
        System.out.println(getTeamName(team) + " 플레이어가 사용할 상차림 번호를 입력하세요 ");
        printElephantSetups();
    }


    public void printBoard(final BoardView board) {
        printHorizontal(board);

        for (int row = board.minRow(); row <= board.maxRow(); row++) {
            printRow(board, row);
            printHorizontal(board);
        }

        printColumnNumbers(board);
    }


    public void printCurrentTurn(final Turn turn) {
        System.out.println(colorOf(turn.team()) + "[" + getTeamName(turn.team()) + " 턴] " + turn.name() + RESET);
    }


    public void printSelectablePieces(final List<Position> positions, final BoardView board) {
        System.out.println("이동할 기물을 선택하세요.");

        for (int i = 0; i < positions.size(); i++) {
            final Position pos = positions.get(i);
            final Piece piece = board.findPiece(pos).orElseThrow(); // Optional 활용 고민

            System.out.printf("%d. %s(%d, %d)%s",
                    i + 1,
                    piece.getPieceType().getNameOf(piece.getTeam()),
                    pos.row(),
                    pos.column(),
                    NEW_LINE);
        }

        System.out.println();
    }


    public void printMovablePositions(final BoardView board, final List<Position> moves, final Team team) {
        printBoard(board, moves, team);

        System.out.println("이동할 좌표를 선택하세요.");

        for (int i = 0; i < moves.size(); i++) {
            final Position pos = moves.get(i);

            System.out.printf("%d. (%d, %d)%s",
                    i + 1,
                    pos.row(),
                    pos.column(),
                    NEW_LINE);
        }

        System.out.println();
    }

    public void printWinner(final Player winner) {
        System.out.println("승자는  " + winner.getName() + "입니다!");
    }


    private void printBoard(final BoardView board, final List<Position> moves, final Team team) {
        printHorizontal(board);

        for (int row = board.minRow(); row <= board.maxRow(); row++) {
            System.out.printf("%2d ", row);
            System.out.print("|");

            for (int col = board.minCol(); col <= board.maxCol(); col++) {
                final Position pos = Position.of(row, col);
                System.out.print(renderCell(board, pos, moves, team));
                System.out.print("|");
            }

            System.out.println();
            printHorizontal(board);
        }

        printColumnNumbers(board);
    }

    private String renderCell(
            final BoardView board,
            final Position position,
            final List<Position> moves,
            final Team currentTeam
    ) {
        final boolean movable = moves.contains(position);

        return board.findPiece(position)
                .map(piece -> {
                    if (movable && !piece.isSameTeam(currentTeam)) {
                        return colorOf(currentTeam) + "[" + piece.getPieceType().getNameOf(piece.getTeam()) + "]" + RESET;
                    }
                    return renderPiece(piece);
                })
                .orElseGet(() -> {
                    if (movable) {
                        return colorOf(currentTeam) + " ●  " + RESET;
                    }
                    return EMPTY;
                });
    }


    private void printRow(final BoardView board, final int row) {
        System.out.printf("%2d ", row);
        System.out.print("|");

        for (int col = board.minCol(); col <= board.maxCol(); col++) {
            final Position pos = Position.of(row, col);
            System.out.print(renderCell(board, pos));
            System.out.print("|");
        }

        System.out.println();
    }

    private String renderCell(final BoardView board, final Position pos) {
        return board.findPiece(pos)
                .map(this::renderPiece)
                .orElse(EMPTY);
    }

    private String renderPiece(final Piece piece) {
        final String name = piece.getPieceType().getNameOf(piece.getTeam());
        final String centered = " " + name + " ";

        return colorOf(piece.getTeam()) + centered + RESET;
    }

    private String colorOf(final Team team) {
        if (team == Team.HAN) {
            return RED;
        }
        return BLUE;
    }

    private void printHorizontal(final BoardView board) {
        final int cols = board.maxCol() - board.minCol() + 1;

        final String line = BOARD_OUTER_SPACES
                + "+----".repeat(Math.max(0, cols))
                + "+";

        System.out.println(line);
    }

    private void printColumnNumbers(final BoardView board) {
        System.out.print(BOARD_OUTER_SPACES);

        for (int col = board.minCol(); col <= board.maxCol(); col++) {
            System.out.printf("  %2d ", col);
        }

        System.out.println();
    }


    private void printElephantSetups() {
        final StringBuilder promptBuilder = new StringBuilder();
        final List<String> descriptions = ElephantSetup.descriptions();
        for (int i = 0; i < descriptions.size(); i++) {
            promptBuilder.append(String.format("%d. %s ", i + 1, descriptions.get(i)));
        }
        System.out.println(promptBuilder);
    }


    private String getTeamName(final Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }

        return "초나라";
    }
}
