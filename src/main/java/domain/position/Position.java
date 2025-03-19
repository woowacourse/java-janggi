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

    public Position add(final int fileAmount, final int rankAmount) {
        return new Position(file.add(fileAmount), rank.add(rankAmount));
    }

    public boolean isValidToAdd(final int fileAmount, final int rankAmount) {
        return file.validateAdd(fileAmount) && rank.validateAdd(rankAmount);
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

    public int distance(Position other) {
        return Math.max(this.file.distance(other.file), this.rank.distance(other.rank));
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


    public List<Position> createPositionsUntil(final Position nextPosition) {
        if (this.file == nextPosition.file) {
            List<PositionRank> ranks = this.rank.getBetweenRanks(nextPosition.rank);
            return ranks.stream()
                    .map(newRank -> new Position(file, newRank))
                    .toList();
        }

        if (this.rank == nextPosition.rank) {
            List<PositionFile> files = this.file.getBetweenFiles(nextPosition.file);
            return files.stream()
                    .map(newFile -> new Position(newFile, rank))
                    .toList();
        }

        throw new IllegalArgumentException("일자로 연결되지 않으면 위치를 생성할 수 없습니다.");
    }
}
