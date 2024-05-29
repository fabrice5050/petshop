package com.petshop.pet.services;

import com.petshop.pet.model.CheckOut;
import com.petshop.pet.model.Pet;
import com.petshop.pet.repository.CheckRepo;
import com.petshop.pet.repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class petServiceImpl implements petService{
    @Autowired
    private PetRepo repo;
    @Autowired
    private CheckRepo checkRepo;
    @Override
    public Pet savePet(Pet pet) {
        return repo.save(pet);
    }

    @Override
    public CheckOut saveCheck(CheckOut check) {
        return checkRepo.save(check);
    }

    @Override
    public List<Pet> listOfPets() {
        return repo.findAll();
    }

    @Override
    public List<CheckOut> listOfCheck() {
        return checkRepo.findAll();
    }

    @Override
    public void deletePetById(Long id) {
repo.deleteById(id);
    }

    @Override
    public Pet findById(Long id) {
        Optional<Pet> optional=repo.findById(id);
        Pet pets=null;
        if(optional.isPresent()){
           pets= optional.get();
        }else {
throw new RuntimeException("Pets not found !!");
        }
   return pets;
    }

    @Override
    public Page<Pet> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repo.findAll(pageable);
    }
}
