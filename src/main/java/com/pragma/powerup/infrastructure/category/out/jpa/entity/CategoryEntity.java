package com.pragma.powerup.infrastructure.category.out.jpa.entity;

import com.pragma.powerup.infrastructure.plate.out.jpa.entity.PlateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "nombre", length = 50)
    private String name;
    @Column(name = "descripcion", length = 200)
    private String description;
    @OneToMany(mappedBy = "category")
    private List<PlateEntity> plates;
}
