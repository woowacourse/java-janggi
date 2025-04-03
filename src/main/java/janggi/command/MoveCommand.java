package janggi.command;

import janggi.GameContext;
import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.player.Player;
import janggi.service.JanggiService;

public class MoveCommand implements Command {

    private final int departureRow;
    private final int departureColumn;
    private final int destinationRow;
    private final int destinationColumn;

    private MoveCommand(final int departureRow,
                        final int departureColumn,
                        final int destinationRow,
                        final int destinationColumn) {
        this.departureRow = departureRow;
        this.departureColumn = departureColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
    }

    public static MoveCommand of(final String departureRow,
                                 final String departureColumn,
                                 final String destinationRow,
                                 final String destinationColumn) {

        try {
            return new MoveCommand(
                    Integer.parseInt(departureRow),
                    Integer.parseInt(departureColumn),
                    Integer.parseInt(destinationRow),
                    Integer.parseInt(destinationColumn));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자 형식이어야 합니다.", e);
        }
    }

    @Override
    public void execute(final GameContext context, final JanggiService service) {
        final Board board = context.getBoard();
        final Player player = context.getCurrentPlayer();
        service.movePiece(board, player, getDeparturePosition(), getDestinationPosition());
    }

    @Override
    public boolean isExitCommand() {
        return false;
    }

    @Override
    public CommandType getType() {
        return CommandType.MOVE;
    }

    private Position getDeparturePosition() {
        return Position.of(departureRow, departureColumn);
    }

    private Position getDestinationPosition() {
        return Position.of(destinationRow, destinationColumn);
    }
}
