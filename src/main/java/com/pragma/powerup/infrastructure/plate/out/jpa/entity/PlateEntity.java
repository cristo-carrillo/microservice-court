package com.pragma.powerup.infrastructure.plate.out.jpa.entity;

import com.pragma.powerup.infrastructure.category.out.jpa.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "platos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlateEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "id_categoria")
    private Long idCategory;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "precio")
    private Long price;
    @Column(name = "id_restaurante")
    private Long idRestaurant;
    @Column(name = "url_imagen")
    private String urlPicture;
    @Column(name = "activo")
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private CategoryEntity category;
}
