package util;

import janggi.position.Position;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TestPositionGenerator {

    public static Stream<Arguments> makeOutOfBoundPositions() {
        return Stream.of(
                Arguments.arguments(
                        new Position(0,-1)
                ),
                Arguments.arguments(
                        new Position(-1,0)
                ),
                Arguments.arguments(
                        new Position(-1,9)
                ),
                Arguments.arguments(
                        new Position(0,10)
                ),
                Arguments.arguments(
                        new Position(8,10)
                ),
                Arguments.arguments(
                        new Position(9,9)
                ),
                Arguments.arguments(
                        new Position(9,0)
                ),
                Arguments.arguments(
                        new Position(8,-1)
                )
        );
    }

    public static Stream<Arguments> makeWithinBoundPositions() {
        return Stream.of(
                Arguments.arguments(
                        new Position(1,1)
                ),
                Arguments.arguments(
                        new Position(8,1)
                ),
                Arguments.arguments(
                        new Position(8,9)
                ),
                Arguments.arguments(
                        new Position(1,9)
                )
        );
    }
}
