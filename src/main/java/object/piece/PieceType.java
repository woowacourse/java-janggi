package object.piece;

import static object.coordinate.CrossMoveVector.DOWN;
import static object.coordinate.CrossMoveVector.LEFT;
import static object.coordinate.CrossMoveVector.RIGHT;
import static object.coordinate.CrossMoveVector.UP;
import static object.coordinate.DiagonalMoveVector.LEFT_DOWN;
import static object.coordinate.DiagonalMoveVector.LEFT_UP;
import static object.coordinate.DiagonalMoveVector.RIGHT_DOWN;
import static object.coordinate.DiagonalMoveVector.RIGHT_UP;

import java.util.List;
import object.piece.generator.CrossOneInCastlePathGenerator;
import object.piece.generator.CrossPathGenerator;
import object.piece.generator.DiagonalInCastlePathGenerator;
import object.piece.generator.DiagonalOneInCastlePathGenerator;
import object.piece.generator.PathGenerator;
import object.piece.generator.SpecificOnePathGenerator;
import object.piece.generator.SpecificPathGenerator;
import object.piece.validator.CrossPathValidator;
import object.piece.validator.MaPathValidator;
import object.piece.validator.PathValidator;
import object.piece.validator.PoPathValidator;
import object.piece.validator.SangPathValidator;

public enum PieceType {
    궁(0,
            List.of(new CrossOneInCastlePathGenerator(),
                    new DiagonalOneInCastlePathGenerator()),
            List.of()
    ),
    사(3,
            List.of(new CrossOneInCastlePathGenerator(),
                    new DiagonalOneInCastlePathGenerator()),
            List.of()
    ),
    졸(2,
            List.of(new SpecificOnePathGenerator(List.of(UP, RIGHT, LEFT)),
                    new DiagonalOneInCastlePathGenerator()),
            List.of()
    ),
    병(2,
            List.of(new SpecificOnePathGenerator(List.of(DOWN, RIGHT, LEFT)),
                    new DiagonalOneInCastlePathGenerator()),
            List.of()
    ),
    상(3,
            List.of(new SpecificPathGenerator(List.of(
                    List.of(UP, RIGHT_UP, RIGHT_UP),
                    List.of(UP, LEFT_UP, LEFT_UP),
                    List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),
                    List.of(DOWN, LEFT_DOWN, LEFT_DOWN),
                    List.of(RIGHT, RIGHT_UP, RIGHT_UP),
                    List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
                    List.of(LEFT, LEFT_UP, LEFT_UP),
                    List.of(LEFT, LEFT_DOWN, LEFT_DOWN)
            ))),
            List.of(new SangPathValidator())
    ),
    마(5,
            List.of(new SpecificPathGenerator(List.of(
                    List.of(UP, RIGHT_UP),
                    List.of(UP, LEFT_UP),
                    List.of(DOWN, RIGHT_DOWN),
                    List.of(DOWN, LEFT_DOWN),
                    List.of(RIGHT, RIGHT_UP),
                    List.of(RIGHT, RIGHT_DOWN),
                    List.of(LEFT, LEFT_UP),
                    List.of(LEFT, LEFT_DOWN)))),
            List.of(new MaPathValidator())
    ),
    포(7,
            List.of(new CrossPathGenerator(),
                    new DiagonalInCastlePathGenerator()),
            List.of(new PoPathValidator())
    ),
    차(13,
            List.of(new CrossPathGenerator(),
                    new DiagonalInCastlePathGenerator()),
            List.of(new CrossPathValidator())
    ),
    ;

    private final int score;
    private final List<PathGenerator> pathGenerators;
    private final List<PathValidator> pathValidators;

    PieceType(int score, List<PathGenerator> pathGenerators, List<PathValidator> pathValidators) {
        this.score = score;
        this.pathGenerators = pathGenerators;
        this.pathValidators = pathValidators;
    }

    public int getScore() {
        return score;
    }

    public List<PathGenerator> getMovableValidators() {
        return pathGenerators;
    }

    public List<PathValidator> getObstacleValidators() {
        return pathValidators;
    }
}
