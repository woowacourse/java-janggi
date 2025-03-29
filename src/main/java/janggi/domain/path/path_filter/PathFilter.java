package janggi.domain.path.path_filter;

import janggi.domain.path.Path;

import java.util.Set;

public interface PathFilter {

    Set<Path> filter(Set<Path> paths, PathFilterRequest request);
}
