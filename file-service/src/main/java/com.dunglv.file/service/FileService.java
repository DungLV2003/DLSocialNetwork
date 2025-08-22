package com.dunglv.file.service;

import com.dunglv.file.dto.response.FileData;
import com.dunglv.file.dto.response.FileResponse;
import com.dunglv.file.entity.FileManagement;
import com.dunglv.file.exception.AppException;
import com.dunglv.file.exception.ErrorCode;
import com.dunglv.file.mapper.FileMgmtMapper;
import com.dunglv.file.repository.FileManagementRepository;
import com.dunglv.file.repository.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileService {
    FileRepository  fileRepository;
    FileManagementRepository fileManagementRepository;
    FileMgmtMapper fileMgmtMapper;

    public FileResponse uploadFile(MultipartFile file) throws IOException {
        //Store file in local storage
        var fileInfo = fileRepository.store(file);
        // Create file management info
        var fileMgmt = fileMgmtMapper.toFileMgmt(fileInfo);

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        fileMgmt.setOwnerId(userId);
        fileMgmt = fileManagementRepository.save(fileMgmt);
        return  FileResponse.builder()
                .originalFileName(file.getOriginalFilename())
                .url(fileInfo.getUrl())
                .build();
    }

    public FileData dowloadFile(String fileName) throws IOException {
       var fileMgmt = fileManagementRepository.findById(fileName)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

       var resource = fileRepository.read(fileMgmt);
       return new FileData(fileMgmt.getContentType(), resource);
    }
}
