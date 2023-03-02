package com.pragma.powerup.infrastructure.plate.out.jpa.repository;

import com.pragma.powerup.infrastructure.plate.out.jpa.entity.PlateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlateRepository extends JpaRepository<PlateEntity, Long> {

    Page<PlateEntity> findAllByIdRestaurant(Long id, Pageable pageable);
}
