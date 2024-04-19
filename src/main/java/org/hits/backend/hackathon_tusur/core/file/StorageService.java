package org.hits.backend.hackathon_tusur.core.file;

import org.hits.backend.hackathon_tusur.public_interface.file.UploadFileDto;
import reactor.core.publisher.Mono;

public interface StorageService {
    Mono<Void> uploadFile(UploadFileDto dto);
    String getDownloadLinkByName(String name);
}
