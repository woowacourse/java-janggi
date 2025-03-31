package domain.board;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Palace {
    HAN_PALACE(
            List.of(new BoardLocation(4, 1),
                    new BoardLocation(6, 1),
                    new BoardLocation(5, 2),
                    new BoardLocation(4, 3),
                    new BoardLocation(6, 3)
            ),
            new BoardLocation(4, 1),
            new BoardLocation(6, 3)
    ),

    CHO_PALACE(
            List.of(new BoardLocation(4, 10),
                    new BoardLocation(6, 10),
                    new BoardLocation(5, 9),
                    new BoardLocation(4, 8),
                    new BoardLocation(6, 8)
            ),
            new BoardLocation(4, 8),
            new BoardLocation(6, 10)
    );

    private final List<BoardLocation> locations;
    private final BoardLocation startLocation;
    private final BoardLocation endLocation;

    Palace(List<BoardLocation> locations, BoardLocation startLocation, BoardLocation endLocation) {
        this.locations = locations;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public static boolean isDiagonalMoveAllowed(BoardLocation current, BoardLocation destination) {
        Optional<Palace> currentPalace = findByDiagonalMovableLocation(current);
        Optional<Palace> destinationPalace = findByDiagonalMovableLocation(destination);

        if (currentPalace.isPresent() && destinationPalace.isPresent()) {
            return currentPalace.get() == destinationPalace.get();
        }

        return false;
    }

    public void validateInPalace(BoardLocation current, BoardLocation destination) {
        if (isNotInPalace(current) || isNotInPalace(destination)) {
            throw new IllegalArgumentException("[ERROR] 궁성 안에서 움직여야 합니다.");
        }
    }

    private boolean isNotInPalace(BoardLocation location) {
        return startLocation.x() > location.x()
                || startLocation.y() > location.y()
                || endLocation.x() < location.x()
                || endLocation.y() < location.y();
    }

    private static Optional<Palace> findByDiagonalMovableLocation(BoardLocation location) {
        return Arrays.stream(Palace.values())
                .filter(palace -> palace.locations.contains(location))
                .findFirst();
    }
}
