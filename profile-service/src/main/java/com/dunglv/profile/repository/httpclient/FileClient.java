package com.dunglv.profile.repository.httpclient;


import com.dunglv.profile.configuration.AuthenticationRequestInterceptor;
import com.dunglv.profile.dto.response.ApiResponse;
import com.dunglv.profile.dto.response.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service", url = "http://localhost:8084",
        configuration = { AuthenticationRequestInterceptor.class })
@Component
public interface FileClient {
    @PostMapping(value = "/file/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileResponse> uploadMedia(@RequestPart("file") MultipartFile file);
}