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
        if (stateType.equals("DRAW")) {
            return new Draw(camp);
        }
        if (stateType.equals("CHECKMATE")) {
            return new Checkmate(camp);
        }
        if (stateType.equals("GIVE UP")) {
            return new GiveUp(camp);
        }
        throw new UnsupportedOperationException("지원되지 않는 게임 종료 상태입니다.");
    }

    private static GameState createRunningState(String stateType) {
        if (stateType.equals("CHO TURN")) {
            return new ChoTurn();
        }
        return new HanTurn();
    }
}
