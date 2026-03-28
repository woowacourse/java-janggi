package domain;

import domain.position.Position;

public class SettingInfo {
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