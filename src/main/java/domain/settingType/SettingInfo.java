package domain.settingType;

import domain.piece.Team;
import domain.position.Position;

public class SettingInfo {
    private final Position sang1;
    private final Position sang2;
    private final Position ma1;
    private final Position ma2;

    private SettingInfo(Position sang1, Position sang2, Position ma1, Position ma2) {
        this.sang1 = sang1;
        this.sang2 = sang2;
        this.ma1 = ma1;
        this.ma2 = ma2;
    }

    public static SettingInfo of(SettingType type, Team team) {
        if (SettingType.LEFT == type) {
            return getLeftSettingInfoByTeam(team);
        }

        if (SettingType.RIGHT == type) {
            return getRightSettingByTeam(team);
        }

        if (SettingType.INNER == type) {
            return getInnerSettingByTeam(team);
        }
        return getOuterSettingByTeam(team);
    }

    private static SettingInfo getLeftSettingInfoByTeam(Team team) {
        if (team.isCho()) {
            return new SettingInfo(Position.of(1, 3), Position.of(1, 8), Position.of(1, 2), Position.of(1, 7));
        }
        return new SettingInfo(Position.of(10, 2), Position.of(10, 7), Position.of(10, 3), Position.of(10, 8));
    }

    private static SettingInfo getRightSettingByTeam(Team team) {
        if (team.isCho()) {
            return new SettingInfo(Position.of(1, 2), Position.of(1, 7), Position.of(1, 3), Position.of(1, 8));
        }
        return new SettingInfo(Position.of(10, 3), Position.of(10, 8), Position.of(10, 2), Position.of(10, 7));
    }

    private static SettingInfo getInnerSettingByTeam(Team team) {
        if (team.isCho()) {
            return new SettingInfo(Position.of(1, 3), Position.of(1, 7), Position.of(1, 2), Position.of(1, 8));
        }
        return new SettingInfo(Position.of(10, 3), Position.of(10, 7), Position.of(10, 2), Position.of(10, 8));
    }

    private static SettingInfo getOuterSettingByTeam(Team team) {
        if (team.isCho()) {
            return new SettingInfo(Position.of(1, 2), Position.of(1, 8), Position.of(1, 3), Position.of(1, 7));
        }
        return new SettingInfo(Position.of(10, 2), Position.of(10, 8), Position.of(10, 3), Position.of(10, 7));
    }

    public Position getSang1() {
        return sang1;
    }

    public Position getSang2() {
        return sang2;
    }

    public Position getMa1() {
        return ma1;
    }

    public Position getMa2() {
        return ma2;
    }
}