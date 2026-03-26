package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import java.util.Map;

public enum ElephantSetting {

    // TODO: position 중복 제거, List.of(1,2,6,7) 과 같은 방식 고려...

    CHO_LEFT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.ELEPHANT, Camp.CHO, null),
                    new Position(0, 6), new Piece(PieceRule.HORSE, Camp.CHO, null),
                    new Position(0, 2), new Piece(PieceRule.ELEPHANT, Camp.CHO, null),
                    new Position(0, 1), new Piece(PieceRule.HORSE, Camp.CHO, null)
            );
        }
    },
    CHO_RIGHT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.HORSE, Camp.CHO, null),
                    new Position(0, 6), new Piece(PieceRule.ELEPHANT, Camp.CHO, null),
                    new Position(0, 2), new Piece(PieceRule.HORSE, Camp.CHO, null),
                    new Position(0, 1), new Piece(PieceRule.ELEPHANT, Camp.CHO, null)
            );
        }
    },
    CHO_INNER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.HORSE, Camp.CHO, null),
                    new Position(0, 6), new Piece(PieceRule.ELEPHANT, Camp.CHO, null),
                    new Position(0, 2), new Piece(PieceRule.ELEPHANT, Camp.CHO, null),
                    new Position(0, 1), new Piece(PieceRule.HORSE, Camp.CHO, null)
            );
        }
    },
    CHO_OUTER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.ELEPHANT, Camp.CHO, null),
                    new Position(0, 6), new Piece(PieceRule.HORSE, Camp.CHO, null),
                    new Position(0, 2), new Piece(PieceRule.HORSE, Camp.CHO, null),
                    new Position(0, 1), new Piece(PieceRule.ELEPHANT, Camp.CHO, null)
            );
        }
    },
    HAN_LEFT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.ELEPHANT, Camp.HAN, null),
                    new Position(9, 2), new Piece(PieceRule.HORSE, Camp.HAN, null),
                    new Position(9, 6), new Piece(PieceRule.ELEPHANT, Camp.HAN, null),
                    new Position(9, 7), new Piece(PieceRule.HORSE, Camp.HAN, null)
            );
        }
    },
    HAN_RIGHT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.HORSE, Camp.HAN, null),
                    new Position(9, 2), new Piece(PieceRule.ELEPHANT, Camp.HAN, null),
                    new Position(9, 6), new Piece(PieceRule.HORSE, Camp.HAN, null),
                    new Position(9, 7), new Piece(PieceRule.ELEPHANT, Camp.HAN, null)
            );
        }
    },
    HAN_INNER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.HORSE, Camp.HAN, null),
                    new Position(9, 2), new Piece(PieceRule.ELEPHANT, Camp.HAN, null),
                    new Position(9, 6), new Piece(PieceRule.ELEPHANT, Camp.HAN, null),
                    new Position(9, 7), new Piece(PieceRule.HORSE, Camp.HAN, null)
            );
        }
    },
    HAN_OUTER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.ELEPHANT, Camp.HAN, null),
                    new Position(9, 2), new Piece(PieceRule.HORSE, Camp.HAN, null),
                    new Position(9, 6), new Piece(PieceRule.HORSE, Camp.HAN, null),
                    new Position(9, 7), new Piece(PieceRule.ELEPHANT, Camp.HAN, null)
            );
        }
    };

    abstract public Map<Position, Piece> makeElephants();
}
