package com.variomex.variomex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


@Entity
@Table(name = "genome_files")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenomeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String cid;
    private String ownerId;
    private String sha256;
    private String filename;
    private String fileType;
    private String uploadDate;


}
