package domain;

import domain.piece.Team;
import domain.position.Position;

public enum SettingType {
    LEFT {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, 2),
                    Position.of(row, 7),
                    Position.of(row, 3),
                    Position.of(row, 8)
            );
        }
    },
    RIGHT {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, 3),
                    Position.of(row, 8),
                    Position.of(row, 2),
                    Position.of(row, 7)
            );
        }
    },
    INNER {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, 3),
                    Position.of(row, 7),
                    Position.of(row, 2),
                    Position.of(row, 8)
            );
        }
    },
    OUTER {
        @Override
        public SettingInfo generate(Team team) {
            int row = selectProperRow(team);
            return new SettingInfo(
                    Position.of(row, 2),
                    Position.of(row, 8),
                    Position.of(row, 3),
                    Position.of(row, 7)
            );
        }
    };

    protected int selectProperRow(Team team) {
        if (team == Team.CHO) {
            return 1;
        }

        return 10;
    }

    public abstract SettingInfo generate(Team team);
}
