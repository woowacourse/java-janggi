package domain.position;

import domain.piece.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Position(
        PositionFile file,
        PositionRank rank
) {
    public Position {
        validateFile(file);
        validateRank(rank);
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
