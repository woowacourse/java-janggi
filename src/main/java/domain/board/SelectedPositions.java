package domain.board;

public record SelectedPositions(
        BoardPosition selectPosition,
        BoardPosition destinationPosition
) {

    public SelectedPositions {
        if (selectPosition.equals(destinationPosition)) {
            throw new IllegalArgumentException("기물을 같은 위치로 이동시킬 수 없습니다.");
        }
    }
}
