package janggi.domain.gung;

import janggi.domain.Country;
import janggi.domain.path.Path;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Gung {

    public static Gung of(Country country) {
        if (country == Country.CHO) return new ChoGung();
        return new HanGung();
    }

    public boolean isInGung(final Path path) {
        return path.pathPositions().stream()
                .allMatch(this::isInGungPosition);
    }

    private boolean isInGungPosition(final Position position) {
        return position.file().isBetween(getMinFile(), getMaxFile())
               && position.rank().isBetween(getMinRank(), getMaxRank());
    }

    public boolean isAvailablePathInGung(final Path path) {
        return getPaths().stream()
                .anyMatch(availablePath -> availablePath.isSuperPathOf(path));
    }

    public Set<Path> getAllPathsFrom(final Position position) {
        if (!isInGungPosition(position)) {
            return Set.of();
        }

        return getPaths().stream()
                .filter(path -> path.isStartWith(position))
                .collect(Collectors.toSet());
    }

    protected abstract PositionFile getMinFile();

    protected abstract PositionFile getMaxFile();

    protected abstract PositionRank getMinRank();

    protected abstract PositionRank getMaxRank();

    protected abstract Set<Path> getPaths();
}
