package domain.position;

import java.util.Objects;

public class Position {

    private final PositionFile file;
    private final PositionRank rank;

    public Position(final PositionFile file, final PositionRank rank) {
        validateFile(file);
        validateRank(rank);
        this.file = file;
        this.rank = rank;
    }

    private void validateFile(final PositionFile file) {
        if (file == null) {
            throw new IllegalArgumentException("파일은 필수값입니다.");
        }
    }

    private void validateRank(final PositionRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("랭크는 필수값입니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Position position)) return false;
        return file == position.file && Objects.equals(rank, position.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return "Position{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}
