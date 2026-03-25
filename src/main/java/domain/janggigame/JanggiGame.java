package domain.janggigame;

import domain.piece.Side;
import domain.players.Players;

public class JanggiGame {
    private final Players players;

    public JanggiGame(Players players) {
        this.players = players;
    }

    public void run() {
        HanPlayerPlacement();
        ChoPlayerPlacement();
    }

    private void HanPlayerPlacement() {
        // 상배치 코드입력 받고
        players.initPlacementBySide(Side.HAN, 1);
        // 출력
    }

    private void ChoPlayerPlacement() {
        // 상배치 코드입력 받고
        players.initPlacementBySide(Side.CHO, 2);
        // 출력
    }

}
