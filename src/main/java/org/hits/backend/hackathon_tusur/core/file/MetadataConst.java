package org.hits.backend.hackathon_tusur.core.file;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MetadataConst {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length");

    public final String name;
}
