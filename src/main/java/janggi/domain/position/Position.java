package janggi.domain.position;

import java.util.ArrayList;
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

    public boolean isValidToMove(final Direction direction) {
        return isValidToAdd(direction.getFileToAdd(), direction.getRankToAdd());
    }

    public Position move(final Direction direction) {
        return add(direction.getFileToAdd(), direction.getRankToAdd());
    }

    public boolean isValidToAdd(final int fileAmount, final int rankAmount) {
        return file.isValidToAdd(fileAmount) && rank.isValidToAdd(rankAmount);
    }

    public Position add(final int fileAmount, final int rankAmount) {
        return new Position(file.add(fileAmount), rank.add(rankAmount));
    }

    public List<Position> getAllCrossPositions() {
        final List<Position> positions = new ArrayList<>();
        PositionFile.getAllFiles().stream()
                .filter(f -> !f.equals(file))
                .forEach(f -> positions.add(new Position(f, rank)));
        PositionRank.getAllRanks().stream()
                .filter(r -> !r.equals(rank))
                .forEach(r -> positions.add(new Position(file, r)));
        return positions;
    }

    /**
     * 두 위치 사이의 위치들을 반환하는 기능입니다.
     * 첫 위치와 끝 위치를 포함하지 않습니다.
     *
     * @param nextPosition 현재 위치와 같은 선상의 다른 위치
     * @return 두 위치 사이의 위치들
     * @throws IllegalArgumentException 두 위치가 같은 선상에 위치하지 않은 경우 예외가 발생합니다.
     */
    public List<Position> createPositionsBetween(final Position nextPosition) {
        if (this.file == nextPosition.file) {
            return this.rank.getBetweenRanks(nextPosition.rank).stream()
                    .map(newRank -> new Position(file, newRank))
                    .toList();
        }

        if (this.rank == nextPosition.rank) {
            return this.file.getBetweenFiles(nextPosition.file).stream()
                    .map(newFile -> new Position(newFile, rank))
                    .toList();
        }

        throw new IllegalArgumentException("일자로 연결되지 않으면 위치를 생성할 수 없습니다.");
    }
}
