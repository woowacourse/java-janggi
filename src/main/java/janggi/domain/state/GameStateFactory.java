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
        return createRunningState(stateType);
    }

    private static GameState createFinishedState(Camp camp, String stateType) {
        if ("DRAW".equals(stateType)) {
            return new Draw(camp);
        }
        if ("CHECKMATE".equals(stateType)) {
            return new Checkmate(camp);
        }
        if ("GIVE UP".equals(stateType)) {
            return new GiveUp(camp);
        }
        throw new UnsupportedOperationException("지원되지 않는 게임 종료 상태입니다.");
    }

    private static GameState createRunningState(String stateType) {
        if ("CHO TURN".equals(stateType)) {
            return new ChoTurn();
        }
        return new HanTurn();
    }
}
