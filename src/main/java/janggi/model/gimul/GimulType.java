package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.gimul.byeong.Byeong;
import janggi.model.gimul.diagonalMove.Ma;
import janggi.model.gimul.diagonalMove.Sang;
import janggi.model.gimul.linearMove.Cha;
import janggi.model.gimul.linearMove.Pho;
import janggi.model.gimul.palace.Jang;
import janggi.model.gimul.palace.Sa;

public enum GimulType {
    CHA {
        @Override
        public AbstractGimul create(Team team) {
            return new Cha(team);
        }
    },
    PHO {
        @Override
        public AbstractGimul create(Team team) {
            return new Pho(team);
        }
    },
    MA {
        @Override
        public AbstractGimul create(Team team) {
            return new Ma(team);
        }
    },
    SANG {
        @Override
        public AbstractGimul create(Team team) {
            return new Sang(team);
        }
    },
    SA {
        @Override
        public AbstractGimul create(Team team) {
            return new Sa(team);
        }
    },
    BYEONG {
        @Override
        public AbstractGimul create(Team team) {
            return new Byeong(team);
        }
    },
    JANG {
        @Override
        public AbstractGimul create(Team team) {
            return new Jang(team);
        }
    };

    public abstract AbstractGimul create(Team team);
}
