package com.dunglv.file.mapper;

import com.dunglv.file.dto.FileInfo;
import com.dunglv.file.entity.FileManagement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMgmtMapper {
    @Mapping(target = "id", source = "name")
    FileManagement toFileMgmt(FileInfo fileInfo);
}
