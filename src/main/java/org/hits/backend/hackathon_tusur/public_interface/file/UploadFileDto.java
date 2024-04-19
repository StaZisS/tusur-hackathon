package org.hits.backend.hackathon_tusur.public_interface.file;

import org.hits.backend.hackathon_tusur.core.file.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public record UploadFileDto(
        FileMetadata metadata,
        MultipartFile file
) {
}
