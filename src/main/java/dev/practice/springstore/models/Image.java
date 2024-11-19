package dev.practice.springstore.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private Blob image;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String fileName;

    private String fileType;

    private String downloadUrl;

}
