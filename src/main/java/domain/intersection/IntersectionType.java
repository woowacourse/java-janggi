package domain.intersection;

import domain.intersection.palace.*;
import domain.piece.Piece;
import domain.point.Point;

public enum IntersectionType {

    CENTER_PALACE {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new CenterPalace(point, piece);
        }
    },
    LEFT_TOP_PALACE {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new LeftTopPalace(point, piece);
        }
    },
    LEFT_BOTTOM_PALACE {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new LeftBottomPalace(point, piece);
        }
    },
    RIGHT_TOP_PALACE {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new RightTopPalace(point, piece);
        }
    },
    RIGHT_BOTTOM_PALACE {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new RightBottomPalace(point, piece);
        }
    },
    NORMAL_PALACE {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new NormalPalace(point, piece);
        }
    },
    NORMAL_INTERSECTION {
        @Override
        public Intersection create(Point point, Piece piece) {
            return new NormalIntersection(point, piece);
        }
    };

    public abstract Intersection create(Point point, Piece piece);

}
