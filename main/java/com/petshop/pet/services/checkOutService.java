package com.petshop.pet.services;

import com.petshop.pet.model.CheckOut;

import java.util.List;

public interface checkOutService {
    CheckOut saveCheckout(CheckOut check);
    List<CheckOut> listofcheckout();

}
