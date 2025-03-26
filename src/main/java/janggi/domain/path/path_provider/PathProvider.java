package janggi.domain.path.path_provider;

import janggi.domain.path.Path;
import janggi.domain.position.Position;

import java.util.Set;

public interface PathProvider {

    Set<Path> get(Position position);
}
