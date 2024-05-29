package com.petshop.pet.repository;

import com.petshop.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepo  extends JpaRepository<Pet, Long> {

}
