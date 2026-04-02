package janggi.service;

import janggi.dto.PositionInfo;
import java.util.List;

public class FakeInitialBoardProvider implements InitialBoardProvider {

    private final List<PositionInfo> positionInfos;

    public FakeInitialBoardProvider(List<PositionInfo> positionInfos) {
        this.positionInfos = positionInfos;
    }

    @Override
    public List<PositionInfo> load() {
        return positionInfos;
    }
}
