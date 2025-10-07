package dev.affoysal.backend.service.impl;

import dev.affoysal.backend.dto.Document;
import dev.affoysal.backend.dto.api.IDocument;
import dev.affoysal.backend.entity.DocumentEntity;
import dev.affoysal.backend.exception.ApiException;
import dev.affoysal.backend.repository.DocumentRepository;
import dev.affoysal.backend.repository.UserRepository;
import dev.affoysal.backend.service.DocumentService;
import dev.affoysal.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static dev.affoysal.backend.constant.Constants.FILE_STORAGE;
import static dev.affoysal.backend.utils.DocumentUtils.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FileUtils.byteCountToDisplaySize;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.springframework.util.StringUtils.cleanPath;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Page<IDocument> getDocuments(int page, int size) {
        return documentRepository.findDocuments(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public Page<IDocument> getDocuments(int page, int size, String name) {
        return documentRepository.findDocumentsByName(name, PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public Collection<Document> saveDocuments(String userId, List<MultipartFile> documents) {
        List<Document> newDocuments = new ArrayList<>();
        var userEntity = userRepository.findUserByUserId(userId).get();
        var storage = Paths.get(FILE_STORAGE).toAbsolutePath().normalize();
        try {
            for (MultipartFile document : documents) {
                var fileName = cleanPath(requireNonNull(document.getOriginalFilename()));
                if ("..".contains(fileName)) {
                    throw new ApiException(String.format("Invalid file name: %s", fileName));
                }
                var documentEntity = DocumentEntity
                        .builder()
                        .documentId(UUID.randomUUID().toString())
                        .name(fileName)
                        .owner(userEntity)
                        .extension(getExtension(fileName))
                        .uri(getDocumentUri(fileName))
                        .formattedSize(byteCountToDisplaySize(document.getSize()))
                        .icon(setIcon(getExtension(fileName)))
                        .build();
                var savedDocument = documentRepository.save(documentEntity);
                Files.copy(document.getInputStream(), storage.resolve(fileName), REPLACE_EXISTING);
                Document newDocument = fromDocumentEntity(savedDocument,
                        userService.getUserById(savedDocument.getCreatedBy()),
                        userService.getUserById(savedDocument.getUpdatedBy()));
                newDocuments.add(newDocument);
            }
            return newDocuments;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public IDocument updateDocument(String documentId, String name, String description) {
        try {
            var documentEntity = getDocumentEntity(documentId);
            var document = Paths.get(FILE_STORAGE).resolve(documentEntity.getName()).toAbsolutePath().normalize();
            Files.move(document, document.resolveSibling(name), REPLACE_EXISTING);
            documentEntity.setName(name);
            documentEntity.setDescription(description);
            documentRepository.save(documentEntity);
            return getDocumentByDocumentId(documentId);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    private DocumentEntity getDocumentEntity(String documentId) {
        return documentRepository.findByDocumentId(documentId).orElseThrow(() -> new ApiException("Unable to find document"));
    }

    @Override
    public void deleteDocument(String documentId) {

    }

    @Override
    public IDocument getDocumentByDocumentId(String documentId) {
        return documentRepository.findDocumentByDocumentId(documentId).orElseThrow(() -> new ApiException("Document not found"));
    }

    @Override
    public Resource getResource(String documentName) {
        try {
            var filePath = Paths.get(FILE_STORAGE).toAbsolutePath().normalize().resolve(documentName);
            if (!Files.exists(filePath)) {
                throw new ApiException("File does not exist");
            }
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new ApiException("Unable to download document");
        }
    }
}
