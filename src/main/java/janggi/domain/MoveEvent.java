package janggi.domain;

import janggi.domain.space.Position;

public record MoveEvent(Position source, Position target) {
}
