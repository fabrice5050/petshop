package com.petshop.pet.services;

import com.petshop.pet.model.Pet;
import com.petshop.pet.model.CheckOut;

import org.springframework.data.domain.Page;

import java.util.List;

public interface petService {
    Pet savePet(Pet pet);
    CheckOut saveCheck(CheckOut check);
    List<Pet> listOfPets( );
    List<CheckOut> listOfCheck( );
    void deletePetById(Long id);
    Pet findById(Long id);
    Page<Pet> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
