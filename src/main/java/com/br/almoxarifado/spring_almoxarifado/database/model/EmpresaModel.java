package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name="empresas")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmpresaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Integer id;
    @Column(nullable=false, unique=true)
    private String nome;
    @Column(nullable=false)
    private String endereco;
    @Column(nullable=false)
    private String telefone;


}
