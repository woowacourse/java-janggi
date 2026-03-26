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
                    new Position(0, 7), new Piece(PieceRule.ELEPHANT, Camp.CHO),
                    new Position(0, 6), new Piece(PieceRule.HORSE, Camp.CHO),
                    new Position(0, 2), new Piece(PieceRule.ELEPHANT, Camp.CHO),
                    new Position(0, 1), new Piece(PieceRule.HORSE, Camp.CHO)
            );
        }
    },
    CHO_RIGHT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.HORSE, Camp.CHO),
                    new Position(0, 6), new Piece(PieceRule.ELEPHANT, Camp.CHO),
                    new Position(0, 2), new Piece(PieceRule.HORSE, Camp.CHO),
                    new Position(0, 1), new Piece(PieceRule.ELEPHANT, Camp.CHO)
            );
        }
    },
    CHO_INNER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.HORSE, Camp.CHO),
                    new Position(0, 6), new Piece(PieceRule.ELEPHANT, Camp.CHO),
                    new Position(0, 2), new Piece(PieceRule.ELEPHANT, Camp.CHO),
                    new Position(0, 1), new Piece(PieceRule.HORSE, Camp.CHO)
            );
        }
    },
    CHO_OUTER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(0, 7), new Piece(PieceRule.ELEPHANT, Camp.CHO),
                    new Position(0, 6), new Piece(PieceRule.HORSE, Camp.CHO),
                    new Position(0, 2), new Piece(PieceRule.HORSE, Camp.CHO),
                    new Position(0, 1), new Piece(PieceRule.ELEPHANT, Camp.CHO)
            );
        }
    },
    HAN_LEFT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.ELEPHANT, Camp.HAN),
                    new Position(9, 2), new Piece(PieceRule.HORSE, Camp.HAN),
                    new Position(9, 6), new Piece(PieceRule.ELEPHANT, Camp.HAN),
                    new Position(9, 7), new Piece(PieceRule.HORSE, Camp.HAN)
            );
        }
    },
    HAN_RIGHT_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.HORSE, Camp.HAN),
                    new Position(9, 2), new Piece(PieceRule.ELEPHANT, Camp.HAN),
                    new Position(9, 6), new Piece(PieceRule.HORSE, Camp.HAN),
                    new Position(9, 7), new Piece(PieceRule.ELEPHANT, Camp.HAN)
            );
        }
    },
    HAN_INNER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.HORSE, Camp.HAN),
                    new Position(9, 2), new Piece(PieceRule.ELEPHANT, Camp.HAN),
                    new Position(9, 6), new Piece(PieceRule.ELEPHANT, Camp.HAN),
                    new Position(9, 7), new Piece(PieceRule.HORSE, Camp.HAN)
            );
        }
    },
    HAN_OUTER_ELEPHANT {
        @Override
        public Map<Position, Piece> makeElephants() {
            return Map.of(
                    new Position(9, 1), new Piece(PieceRule.ELEPHANT, Camp.HAN),
                    new Position(9, 2), new Piece(PieceRule.HORSE, Camp.HAN),
                    new Position(9, 6), new Piece(PieceRule.HORSE, Camp.HAN),
                    new Position(9, 7), new Piece(PieceRule.ELEPHANT, Camp.HAN)
            );
        }
    };

    abstract public Map<Position, Piece> makeElephants();
}
