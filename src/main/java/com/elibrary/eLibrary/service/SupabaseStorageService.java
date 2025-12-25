package com.elibrary.eLibrary.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupabaseStorageService {

    @Value("${SUPABASE_URL}")
    private String supabaseUrl;

    @Value("${SUPABASE_SERVICE_KEY}")
    private String serviceKey;

    @Value("${SUPABASE_BUCKET:books}")
    private String bucket;

    public String upload(MultipartFile file, String fileName) throws IOException {

        String uploadUrl =
            supabaseUrl + "/storage/v1/object/" + bucket + "/" + fileName;

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(serviceKey);     
        headers.add("apikey", serviceKey);    

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("x-upsert", "true");

        HttpEntity<byte[]> entity =
            new HttpEntity<>(file.getBytes(), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(uploadUrl, HttpMethod.POST, entity, String.class);

        // Return public URL (for DB storage)
        return supabaseUrl +
            "/storage/v1/object/public/" + bucket + "/" + fileName;
    }
}
