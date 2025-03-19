package domain.position;

import domain.piece.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public Position withAddingFile() {
        return new Position(file.add(1), rank);
    }

    public Position withAddingRank() {
        return new Position(file, rank.add(1));
    }

    public Position withSubtractingFile() {
        return new Position(file.add(-1), rank);
    }

    public Position withSubtractingRank() {
        return new Position(file, rank.add(-1));
    }

    public List<Position> getAllCrossPositions() {
        List<Position> positions = new ArrayList<>();
        Arrays.stream(PositionFile.values())
                .filter(f -> !f.equals(file))
                .forEach(f -> positions.add(new Position(f, rank)));
        PositionRank.getAllRanks().stream()
                .filter(r -> !r.equals(rank))
                .forEach(r -> positions.add(new Position(file, r)));
        return positions;
    }

    public List<Path> getPathsFrom(final List<MoveDirection> moveDirections) {
        List<Path> paths = new ArrayList<>();
        paths.add(Path.start(this));

        for (MoveDirection moveDirection : moveDirections) {
            for (Path path : paths) {
                List<Position> nextPositions = moveDirection.calculateNextPositionsFrom(path.getFinalPosition());
                paths = path.nextPath(nextPositions);
            }
        }

        return paths;
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
