package janggi.fixture;

import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.List;

public class PositionInfoFixture {

    private PositionInfoFixture() {
    }

    public static PositionInfo from(List<String> data) {
        return PositionInfo.from(
                Team.valueOf(data.get(0)),
                data.get(1),
                Integer.parseInt(data.get(2)),
                Integer.parseInt(data.get(3))
        );
    }
}
