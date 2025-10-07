package dev.affoysal.backend.utils;

import dev.affoysal.backend.dto.Document;
import dev.affoysal.backend.dto.User;
import dev.affoysal.backend.entity.DocumentEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class DocumentUtils {

    public static Document fromDocumentEntity(DocumentEntity documentEntity, User createdBy, User updatedBy){
        var document = new Document();
        BeanUtils.copyProperties(documentEntity, document);
        document.setOwnerName(createdBy.getFirstName() + " " + createdBy.getLastName());
        document.setOwnerEmail(createdBy.getEmail());
        document.setOwnerPhone(createdBy.getPhone());
        document.setOwnerLastLogin(createdBy.getLastLogin());
        document.setUpdaterName(updatedBy.getFirstName() + " " + updatedBy.getLastName());
        return document;
    }

    public static String getDocumentUri(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(String.format("/documents/%s", fileName)).toUriString();
    }

    public static String setIcon(String fileExtension){
        String extension = StringUtils.trimAllWhitespace(fileExtension);
        if (extension.equalsIgnoreCase("DOC") || extension.equalsIgnoreCase("DOCX")){
            return "https://htmlstream.com/preview/front-dashboard-v2.1.1/assets/svg/brands/word-icon.svg";
        }
        if (extension.equalsIgnoreCase("XLS") || extension.equalsIgnoreCase("XLSX")){
            return "https://htmlstream.com/preview/front-dashboard-v2.1.1/assets/svg/brands/excel-icon.svg";
        }
        if (extension.equalsIgnoreCase("PDF")){
            return "https://htmlstream.com/preview/front-dashboard-v2.1.1/assets/svg/brands/pdf-icon.svg";
        }
        else {
            return "https://htmlstream.com/preview/front-dashboard-v2.1.1/assets/svg/brands/word-icon.svg";
        }
    }
}
