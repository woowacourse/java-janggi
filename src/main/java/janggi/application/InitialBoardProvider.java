package janggi.application;

import janggi.dto.PositionInfo;
import java.util.List;

public interface InitialBoardProvider {
    List<PositionInfo> load();
}
