package com.petshop.pet.services;

import com.petshop.pet.model.CheckOut;
import com.petshop.pet.repository.CheckRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class checkOutServiceImpl implements checkOutService{
    @Autowired
    private CheckRepo repo;
    @Override
    public CheckOut saveCheckout(CheckOut check) {
        return repo.save(check);
    }

    @Override
    public List<CheckOut> listofcheckout() {
        return repo.findAll();
    }
}
