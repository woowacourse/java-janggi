package domain;

import java.util.List;

public class MoveInfos {

    private final List<MoveInfo> moveInfos;

    public MoveInfos(final List<MoveInfo> moveInfos) {
        this.moveInfos = moveInfos;
    }

    public int countPiecesInPath() {
        return (int) moveInfos.stream()
                .filter(MoveInfo::isPieceInPath)
                .count();
    }
}
