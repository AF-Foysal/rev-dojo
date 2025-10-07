package dev.affoysal.backend.repository;

import dev.affoysal.backend.dto.api.IDocument;
import dev.affoysal.backend.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static dev.affoysal.backend.constant.Constants.SELECT_COUNT_DOCUMENTS_QUERY;
import static dev.affoysal.backend.constant.Constants.SELECT_COUNT_DOCUMENTS_BY_NAME_QUERY;
import static dev.affoysal.backend.constant.Constants.SELECT_DOCUMENTS_QUERY;
import static dev.affoysal.backend.constant.Constants.SELECT_DOCUMENTS_BY_NAME_QUERY;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    @Query(
            countQuery = SELECT_COUNT_DOCUMENTS_QUERY,
            value = SELECT_DOCUMENTS_QUERY,
            nativeQuery = true)
    Page<IDocument> findDocuments(Pageable pageable);

    @Query(
            countQuery = SELECT_COUNT_DOCUMENTS_BY_NAME_QUERY,
            value = SELECT_DOCUMENTS_BY_NAME_QUERY,
            nativeQuery = true)
    Page<IDocument> findDocumentsByName(@Param("documentName") String name, Pageable pageable);
}
