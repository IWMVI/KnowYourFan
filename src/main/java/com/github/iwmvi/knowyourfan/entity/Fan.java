package com.github.iwmvi.knowyourfan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Fan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String endereco;
    private String interesses;
    private String documentoPath;

    @ElementCollection
    @CollectionTable(name = "fan_redes_sociais", joinColumns = @JoinColumn(name = "fan_id"))
    @Column(name = "rede_social")
    private List<String> redesSociais;

    @ElementCollection
    @CollectionTable(name = "fan_links_esports", joinColumns = @JoinColumn(name = "fan_id"))
    @Column(name = "link")
    private List<String> linksEsports;
}
