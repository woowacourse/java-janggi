package domain.board;

import java.util.Set;

public class Movement {

    // @formatter:off
    private static final Set<BoardPosition> PALACE_BOARD_POSITIONS = Set.of(
        BoardPosition.GREEN_PALACE_SOUTH_WEST, BoardPosition.GREEN_PALACE_SOUTH_CENTER, BoardPosition.GREEN_PALACE_SOUTH_EAST,
        BoardPosition.GREEN_PALACE_MIDDLE_WEST, BoardPosition.GREEN_PALACE_MIDDLE_CENTER, BoardPosition.GREEN_PALACE_MIDDLE_EAST,
        BoardPosition.GREEN_PALACE_NORTH_WEST, BoardPosition.GREEN_PALACE_NORTH_CENTER, BoardPosition.GREEN_PALACE_NORTH_EAST,
        BoardPosition.RED_PALACE_SOUTH_WEST, BoardPosition.RED_PALACE_SOUTH_CENTER, BoardPosition.RED_PALACE_SOUTH_EAST,
        BoardPosition.RED_PALACE_MIDDLE_WEST, BoardPosition.RED_PALACE_MIDDLE_CENTER, BoardPosition.RED_PALACE_MIDDLE_EAST,
        BoardPosition.RED_PALACE_NORTH_WEST, BoardPosition.RED_PALACE_NORTH_CENTER, BoardPosition.RED_PALACE_NORTH_EAST
    );

    private static final Set<Set<BoardPosition>> ILLEGAL_MOVEMENTS = Set.of(
        Set.of(BoardPosition.GREEN_PALACE_SOUTH_CENTER, BoardPosition.GREEN_PALACE_MIDDLE_WEST),
        Set.of(BoardPosition.GREEN_PALACE_SOUTH_CENTER, BoardPosition.GREEN_PALACE_MIDDLE_EAST),
        Set.of(BoardPosition.GREEN_PALACE_MIDDLE_WEST, BoardPosition.GREEN_PALACE_NORTH_CENTER),
        Set.of(BoardPosition.GREEN_PALACE_MIDDLE_EAST, BoardPosition.GREEN_PALACE_NORTH_CENTER),
        Set.of(BoardPosition.RED_PALACE_NORTH_CENTER, BoardPosition.RED_PALACE_MIDDLE_WEST),
        Set.of(BoardPosition.RED_PALACE_NORTH_CENTER, BoardPosition.RED_PALACE_MIDDLE_EAST),
        Set.of(BoardPosition.RED_PALACE_MIDDLE_WEST, BoardPosition.RED_PALACE_SOUTH_CENTER),
        Set.of(BoardPosition.RED_PALACE_MIDDLE_EAST, BoardPosition.RED_PALACE_SOUTH_CENTER)
    );
    // @formatter:on

    private final BoardPosition selectBoardPosition;
    private final BoardPosition destinationBoardPosition;

    public Movement(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        validateNotNull(selectBoardPosition, destinationBoardPosition);
        this.selectBoardPosition = selectBoardPosition;
        this.destinationBoardPosition = destinationBoardPosition;
    }

    private void validateNotNull(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        if (selectBoardPosition == null || destinationBoardPosition == null) {
            throw new IllegalArgumentException("움직임은 시작 위치와 도착 위치를 가져야 합니다.");
        }
    }

    public boolean isPalaceMovement() {
        if (!isPositionsInsidePalace() || isIllegalPalacePath()) {
            return false;
        }

        final Offset offset = calculateOffset();

        return offset.isLinear() || offset.isDiagonal();
    }

    private boolean isPositionsInsidePalace() {
        return PALACE_BOARD_POSITIONS.contains(selectBoardPosition)
            && PALACE_BOARD_POSITIONS.contains(destinationBoardPosition);
    }

    private boolean isIllegalPalacePath() {
        return ILLEGAL_MOVEMENTS.contains(Set.of(selectBoardPosition, destinationBoardPosition));
    }

    public Offset calculateOffset() {
        return destinationBoardPosition.calculateOffset(selectBoardPosition);
    }
}
