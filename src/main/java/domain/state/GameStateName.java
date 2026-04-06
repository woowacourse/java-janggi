package domain.state;

import domain.setup.Arrangement;
import domain.setup.Arrangements;
import domain.piece.Team;

public enum GameStateName {
    READY_HAN {
        @Override
        public GameState toGameState(Arrangement hanArrangement) {
            return new ReadyState(new Arrangements());
        }
    },
    READY_CHO {
        @Override
        public GameState toGameState(Arrangement hanArrangement) {
            return new ReadyState(new Arrangements().assignArrangement(Team.HAN, hanArrangement));
        }
    },
    PLAYING {
        @Override
        public GameState toGameState(Arrangement hanArrangement) {
            return new PlayingState();
        }
    },
    BIKJANG {
        @Override
        public GameState toGameState(Arrangement hanArrangement) {
            return new BikjangState();
        }
    },
    END {
        @Override
        public GameState toGameState(Arrangement hanArrangement) {
            return new EndGameState(GameResult.DRAW);
        }
    };

    public abstract GameState toGameState(Arrangement hanArrangement);
}