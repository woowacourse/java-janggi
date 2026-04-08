package domain.state;

import domain.setup.Arrangements;

public enum GameStateName {
    READY_HAN {
        @Override
        public GameState toGameState() {
            return new ReadyState(new Arrangements());
        }
    },
    READY_CHO {
        @Override
        public GameState toGameState() {
            return new ReadyState(new Arrangements());
        }
    },
    PLAYING {
        @Override
        public GameState toGameState() {
            return new PlayingState();
        }
    },
    BIKJANG {
        @Override
        public GameState toGameState() {
            return new BikjangState();
        }
    },
    END {
        @Override
        public GameState toGameState() {
            return new EndGameState(GameResult.DRAW);
        }
    };

    public abstract GameState toGameState();

    public boolean isSetupState() {
        return this == READY_HAN || this == READY_CHO;
    }
}