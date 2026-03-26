package domain;

import domain.piece.Team;
import domain.position.Position;

public enum SettingType {
    LEFT, RIGHT, INNER, OUTER;

    public static SettingInfo getLeftInfo(Team team) {
        if (team == Team.CHO) {
            return new LeftSettingOfChoInfo();
        }
        return new LeftSettingOfHanInfo();
    }

    public static SettingInfo getRightInfo(Team team) {
        if (team == Team.CHO) {
            return new RightSettingOfChoInfo();
        }
        return new RightSettingOfHanInfo();
    }

    public static SettingInfo getInnerInfo(Team team) {
        if (team == Team.CHO) {
            return new InnerSettingOfChoInfo();
        }
        return new InnerSettingOfHanInfo();
    }

    public static SettingInfo getOuterInfo(Team team) {
        if (team == Team.CHO) {
            return new OuterSettingOfChoInfo();
        }
        return new OuterSettingOfHanInfo();
    }
}


abstract class SettingInfo {
    Position sang1;
    Position sang2;
    Position ma1;
    Position ma2;

    public SettingInfo(Position sang1, Position sang2, Position ma1, Position ma2) {
        this.sang1 = sang1;
        this.sang2 = sang2;
        this.ma1 = ma1;
        this.ma2 = ma2;
    }
}

// --- 왼상차림 (상마상마) ---
class LeftSettingOfChoInfo extends SettingInfo {
    public LeftSettingOfChoInfo() {
        super(Position.of(2, 10), Position.of(3, 10), Position.of(7, 10), Position.of(8, 10));
    }
}

class LeftSettingOfHanInfo extends SettingInfo {
    public LeftSettingOfHanInfo() {
        super(Position.of(2, 1), Position.of(3, 1), Position.of(7, 1), Position.of(8, 1));
    }
}

// --- 오른상차림 (마상마상) ---
class RightSettingOfChoInfo extends SettingInfo {
    public RightSettingOfChoInfo() {
        super(Position.of(3, 10), Position.of(2, 10), Position.of(8, 10), Position.of(7, 10));
    }
}

class RightSettingOfHanInfo extends SettingInfo {
    public RightSettingOfHanInfo() {
        super(Position.of(3, 1), Position.of(2, 1), Position.of(8, 1), Position.of(7, 1));
    }
}

// --- 안상차림 (마상상마) ---
class InnerSettingOfChoInfo extends SettingInfo {
    public InnerSettingOfChoInfo() {
        super(Position.of(3, 10), Position.of(2, 10), Position.of(7, 10), Position.of(8, 10));
    }
}

class InnerSettingOfHanInfo extends SettingInfo {
    public InnerSettingOfHanInfo() {
        super(Position.of(3, 1), Position.of(2, 1), Position.of(7, 1), Position.of(8, 1));
    }
}

// --- 바깥상차림 (상마마상) ---
class OuterSettingOfChoInfo extends SettingInfo {
    public OuterSettingOfChoInfo() {
        super(Position.of(2, 10), Position.of(3, 10), Position.of(8, 10), Position.of(7, 10));
    }
}

class OuterSettingOfHanInfo extends SettingInfo {
    public OuterSettingOfHanInfo() {
        super(Position.of(2, 1), Position.of(3, 1), Position.of(8, 1), Position.of(7, 1));
    }
}
