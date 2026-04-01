package domain.palace;

import domain.Position;
import domain.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class PalaceGeometry {

    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;

    private static final int HAN_PALACE_MIN_ROW = 0;
    private static final int HAN_PALACE_MAX_ROW = 2;

    private static final int CHO_PALACE_MIN_ROW = 7;
    private static final int CHO_PALACE_MAX_ROW = 9;

    public boolean isInsideMyPalace(Position position, TeamColor teamColor) {
        if (!isInsidePalaceColumns(position)) {
            return false;
        }

        if (teamColor == TeamColor.HAN) {
            return isBetween(position.row(), HAN_PALACE_MIN_ROW, HAN_PALACE_MAX_ROW);
        }
        return isBetween(position.row(), CHO_PALACE_MIN_ROW, CHO_PALACE_MAX_ROW);
    }

    private boolean isInsidePalaceColumns(Position position) {
        return isBetween(position.column(), PALACE_MIN_COLUMN, PALACE_MAX_COLUMN);
    }

    private boolean isBetween(int value, int minInclusive, int maxInclusive) {
        return value >= minInclusive && value <= maxInclusive;
    }

    public boolean isPalaceDiagonalLink(Position from, Position to) {
        if (!isSamePalace(from, to)) {
            return false;
        }

        if (isCenter(from) && isCorner(to)) {
            return true;
        }

        if (isCenter(to) && isCorner(from)) {
            return true;
        }

        return false;
    }

    private boolean isSamePalace(Position a, Position b) {
        if (isInsideMyPalace(a, TeamColor.HAN) && isInsideMyPalace(b, TeamColor.HAN)) {
            return true;
        }
        return isInsideMyPalace(a, TeamColor.CHO) && isInsideMyPalace(b, TeamColor.CHO);
    }

    private boolean isCenter(Position position) {
        return isHanCenter(position) || isChoCenter(position);
    }

    private boolean isHanCenter(Position position) {
        return position.row() == 1 && position.column() == 4;
    }

    private boolean isChoCenter(Position position) {
        return position.row() == 8 && position.column() == 4;
    }

    private boolean isCorner(Position position) {
        return isHanCorner(position) || isChoCorner(position);
    }

    private boolean isHanCorner(Position position) {
        if (!isInsideMyPalace(position, TeamColor.HAN)) {
            return false;
        }
        return (position.row() == 0 || position.row() == 2)
                && (position.column() == 3 || position.column() == 5);
    }

    private boolean isChoCorner(Position position) {
        if (!isInsideMyPalace(position, TeamColor.CHO)) {
            return false;
        }
        return (position.row() == 7 || position.row() == 9)
                && (position.column() == 3 || position.column() == 5);
    }

    public List<Position> adjacentPositionsInsideMyPalace(Position position, TeamColor teamColor) {
        if (!isInsideMyPalace(position, teamColor)) {
            return List.of();
        }

        List<Position> candidates = new ArrayList<>();
        addIfInsideMyPalace(candidates, teamColor, Position.of(position.row() - 1, position.column()));
        addIfInsideMyPalace(candidates, teamColor, Position.of(position.row() + 1, position.column()));
        addIfInsideMyPalace(candidates, teamColor, Position.of(position.row(), position.column() - 1));
        addIfInsideMyPalace(candidates, teamColor, Position.of(position.row(), position.column() + 1));

        addDiagonalIfLinked(candidates, position, teamColor);

        return List.copyOf(candidates);
    }

    private void addIfInsideMyPalace(List<Position> candidates, TeamColor teamColor, Position destination) {
        if (!isInsideMyPalace(destination, teamColor)) {
            return;
        }
        candidates.add(destination);
    }

    private void addDiagonalIfLinked(List<Position> candidates, Position position, TeamColor teamColor) {
        if (isCenter(position)) {
            addIfInsideMyPalace(candidates, teamColor, Position.of(position.row() - 1, position.column() - 1));
            addIfInsideMyPalace(candidates, teamColor, Position.of(position.row() - 1, position.column() + 1));
            addIfInsideMyPalace(candidates, teamColor, Position.of(position.row() + 1, position.column() - 1));
            addIfInsideMyPalace(candidates, teamColor, Position.of(position.row() + 1, position.column() + 1));
            return;
        }

        if (isCorner(position)) {
            Position center = getCenterOfMyPalace(teamColor);
            if (isPalaceDiagonalLink(position, center)) {
                candidates.add(center);
            }
        }
    }

    private Position getCenterOfMyPalace(TeamColor teamColor) {
        if (teamColor == TeamColor.HAN) {
            return Position.of(1, 4);
        }
        return Position.of(8, 4);
    }
}

