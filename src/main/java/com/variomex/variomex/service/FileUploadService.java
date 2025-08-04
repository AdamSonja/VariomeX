package com.variomex.variomex.service;

import com.variomex.variomex.client.IpfsClient;
import com.variomex.variomex.model.GenomeFile;
import com.variomex.variomex.repository.GenomeFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class FileUploadService {

    @Autowired
    private GenomeFileRepository genomeFileRepository;

    @Autowired
    private IpfsClient ipfsClient;

    public GenomeFile uploadGenomeFile(MultipartFile file, String ownerId) throws Exception {
        // Compute SHA-256
        String sha256 = DigestUtils.sha256Hex(file.getInputStream());

        // Upload to IPFS
        String cid = ipfsClient.uploadFile(file);

        // Save to DB
        GenomeFile genomeFile = new GenomeFile();
        genomeFile.setCid(cid);
        genomeFile.setOwnerId(ownerId);
        genomeFile.setSha256(sha256);
        genomeFile.setFilename(file.getOriginalFilename());
        genomeFile.setFileType(getFileType(Objects.requireNonNull(file.getOriginalFilename())));
        genomeFile.setUploadDate(String.valueOf(LocalDateTime.now()));

        return genomeFileRepository.save(genomeFile);
    }

    private String getFileType(String filename) {
        if (filename.endsWith(".vcf")) return "vcf";
        if (filename.endsWith(".fasta")) return "fasta";
        if (filename.endsWith(".gz")) return "gz";
        return "unknown";
    }
}
