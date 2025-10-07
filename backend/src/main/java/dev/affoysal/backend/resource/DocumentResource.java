package dev.affoysal.backend.resource;

import dev.affoysal.backend.domain.Response;
import dev.affoysal.backend.dto.User;
import dev.affoysal.backend.dtorequset.UpdateDocRequest;
import dev.affoysal.backend.exception.ApiException;
import dev.affoysal.backend.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static dev.affoysal.backend.constant.Constants.FILE_NAME;
import static java.net.URI.create;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static dev.affoysal.backend.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/documents"})
public class DocumentResource {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Response> saveDocuments(@AuthenticationPrincipal User user, @RequestParam("files") List<MultipartFile> documents, HttpServletRequest request) {
        var newDocuments = documentService.saveDocuments(user.getUserId(), documents);
        return ResponseEntity.created(create("")).body(getResponse(request, Map.of("documents", newDocuments), "Document(s) uploaded", CREATED));
    }

    @GetMapping
    public ResponseEntity<Response> getDocuments(@AuthenticationPrincipal User user, HttpServletRequest request,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "5") int size) {
        var documents = documentService.getDocuments(page, size);
        return ResponseEntity.ok(getResponse(request, Map.of("documents", documents), "Document(s) retrieved.", OK));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchDocuments(@AuthenticationPrincipal User user, HttpServletRequest request,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                                 @RequestParam(value = "name", defaultValue = "") String name) {
        var documents = documentService.getDocuments(page, size, name);
        return ResponseEntity.ok(getResponse(request, Map.of("documents", documents), "Document(s) retrieved.", OK));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<Response> getDocument(@AuthenticationPrincipal User user,
                                                @PathVariable("documentId") String documentId,
                                                HttpServletRequest request) {
        var document = documentService.getDocumentByDocumentId(documentId);
        return ResponseEntity.ok(getResponse(request, Map.of("document", document), "Document retrieved.", OK));
    }

    @PatchMapping
    public ResponseEntity<Response> updateDocument(@AuthenticationPrincipal User user,
                                                @RequestBody UpdateDocRequest document,
                                                HttpServletRequest request) {
        var updatedDocument = documentService.updateDocument(document.getDocumentId(), document.getName(), document.getDescription());
        return ResponseEntity.ok(getResponse(request, Map.of("document", updatedDocument), "Document updated.", OK));
    }

    @GetMapping("/download/{documentName}")
    public ResponseEntity<Resource> downloadDocument(@AuthenticationPrincipal User user,
                                                     @PathVariable("documentName") String documentName) throws IOException {
        var resource = documentService.getResource(documentName);
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(FILE_NAME, documentName);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;File-Name=%s", resource.getFilename()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .headers(httpHeaders).body(resource);
    }
}
