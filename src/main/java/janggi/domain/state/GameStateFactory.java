package janggi.domain.state;

import janggi.domain.Camp;

public class GameStateFactory {

    private GameStateFactory() {
    }

    public static GameState create(String turn, boolean ongoing, String stateType) {
        Camp currentCamp = Camp.valueOf(turn);

        if (!ongoing) {
            return createFinishedState(currentCamp, stateType);
        }

        if (currentCamp.isCho()) {
            return new ChoTurn();
        }
        return new HanTurn();
    }

    private static GameState createFinishedState(Camp camp, String stateType) {
        if (stateType.equals("DRAW")) {
            return new Draw(camp);
        }
        if (stateType.equals("Checkmate")) {
            return new Checkmate(camp);
        }
        if (stateType.equals("GiveUp")) {
            return new GiveUp(camp);
        }
        throw new UnsupportedOperationException("지원되지 않는 게임 종료 상태입니다.");
    }
}
