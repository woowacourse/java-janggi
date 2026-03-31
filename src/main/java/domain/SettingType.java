package domain;

import domain.piece.Team;
import domain.position.Position;

public enum SettingType {
    LEFT {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, convertToProperColumn(team, 2)),
                    Position.of(row, convertToProperColumn(team, 7)),
                    Position.of(row, convertToProperColumn(team, 3)),
                    Position.of(row, convertToProperColumn(team, 8))
            );
        }
    },
    RIGHT {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, convertToProperColumn(team, 3)),
                    Position.of(row, convertToProperColumn(team, 8)),
                    Position.of(row, convertToProperColumn(team, 2)),
                    Position.of(row, convertToProperColumn(team, 7))
            );
        }
    },
    INNER {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, convertToProperColumn(team, 3)),
                    Position.of(row, convertToProperColumn(team, 7)),
                    Position.of(row, convertToProperColumn(team, 2)),
                    Position.of(row, convertToProperColumn(team, 8))
            );
        }
    },
    OUTER {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, convertToProperColumn(team, 2)),
                    Position.of(row, convertToProperColumn(team, 8)),
                    Position.of(row, convertToProperColumn(team, 3)),
                    Position.of(row, convertToProperColumn(team, 7))
            );
        }
    };

    protected int selectProperRow(Team team) {
        if (team == Team.CHO) {
            return 1;
        }

        return 10;
    }

    protected int convertToProperColumn(Team team, int column) {
        if (team == Team.CHO) {
            return 10 - column;
        }
        return column;
    }

    public abstract SettingInfo generate(Team team);
}
