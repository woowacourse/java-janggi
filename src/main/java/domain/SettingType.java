package domain;

import domain.piece.Team;
import domain.position.Position;

public enum SettingType {
    LEFT {
        @Override
        public SettingInfo generate(Team team) {
            if (team == Team.CHO) {
                return new SettingInfo(Position.of(1, 3), Position.of(1, 8), Position.of(1, 2), Position.of(1, 7));
            }
            return new SettingInfo(Position.of(10, 2), Position.of(10, 7), Position.of(10, 3), Position.of(10, 8));
        }
    },
    RIGHT {
        @Override
        public SettingInfo generate(Team team) {
            if (team == Team.CHO) {
                return new SettingInfo(Position.of(1, 2), Position.of(1, 7), Position.of(1, 3), Position.of(1, 8));
            }
            return new SettingInfo(Position.of(10, 3), Position.of(10, 8), Position.of(10, 2), Position.of(10, 7));
        }
    },
    INNER {
        @Override
        public SettingInfo generate(Team team) {
            if (team == Team.CHO) {
                return new SettingInfo(Position.of(1, 3), Position.of(1, 7), Position.of(1, 2), Position.of(1, 8));
            }
            return new SettingInfo(Position.of(10, 3), Position.of(10, 7), Position.of(10, 2), Position.of(10, 8));
        }
    },
    OUTER {
        @Override
        public SettingInfo generate(Team team) {
            if (team == Team.CHO) {
                return new SettingInfo(Position.of(1, 2), Position.of(1, 8), Position.of(1, 3), Position.of(1, 7));
            }
            return new SettingInfo(Position.of(10, 2), Position.of(10, 8), Position.of(10, 3), Position.of(10, 7));
        }
    };

    public abstract SettingInfo generate(Team team);
}
