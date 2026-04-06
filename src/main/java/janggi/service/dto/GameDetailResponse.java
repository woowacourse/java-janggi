package janggi.service.dto;

import janggi.model.Janggi;

public record GameDetailResponse(
        Long gameId,
        Janggi janggi
) {}
