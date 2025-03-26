package janggi.view;

import janggi.coordinate.Position;

public record MoveCommand(int departureRow,
                          int departureColumn,
                          int destinationRow,
                          int destinationColumn) {

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
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자 형식이어야 합니다.", e);
        }
    }

    public Position getDeparturePosition() {
        return Position.of(departureRow, departureColumn);
    }

    public Position getDestinationPosition() {
        return Position.of(destinationRow, destinationColumn);
    }
}
