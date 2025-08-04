package com.variomex.variomex.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class IpfsClient {

    private static final String PINATA_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";
    private static final String API_KEY = "f2f7a178788b739850ef";
    private static final String SECRET_KEY = "4d1c1c232d08a367b149d65828d06fe1b2890e4457d0681291356775ee9ba98d";

    public String uploadFile(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("pinata_api_key", API_KEY);
        headers.set("pinata_secret_api_key", SECRET_KEY);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(PINATA_URL, request, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("IpfsHash")) {
            return response.getBody().get("IpfsHash").toString();
        } else {
            throw new RuntimeException("Failed to upload file to IPFS: " + response);
        }
    }
}
