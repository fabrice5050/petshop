package com.petshop.pet.controller;



import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.petshop.pet.exception.ResourceNotFoundException;
import com.petshop.pet.model.CheckOut;
import com.petshop.pet.model.Pet;
import com.petshop.pet.repository.CheckRepo;
import com.petshop.pet.repository.PetRepo;
import com.petshop.pet.services.DatabasePDFService;
import com.petshop.pet.services.petService;
import com.petshop.pet.services.petServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class PetController {


    @Autowired
    private PetRepo petRepository;
    @Autowired
    private CheckRepo checkRepository;
    @Autowired
    private petServiceImpl serviceImpl;

    //find by all
    @GetMapping
    public List<Pet>getAllPets(){
        return petRepository.findAll();
    }
    // save a pet
    @GetMapping("/add")
    public String addPets(Model model){
        model.addAttribute("pets",new Pet());
        return "addPet";
    }
    @PostMapping("savePet")
    public  String createPet(@ModelAttribute("pets") Pet pet){
        Pet pets= serviceImpl.savePet(pet);
        if(pets!=null){
            return "redirect:/view";
        }
        return "addPet";

    }
    //find by id
//    @GetMapping("{id}")
//    public ResponseEntity<Pet> getPetById(@PathVariable long id){
//        Pet pet = petRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("Pet not exist with id:"+id));
//        return ResponseEntity.ok(pet);
//    }
    // Update pet
    @GetMapping("/showUpdatePet/{id}")
    public String showUpdatePet(@PathVariable(value = "id") Long id,
                                      Model model){
        Pet pet = serviceImpl.findById(id);
        model.addAttribute("pets",pet);
        return "updatePet";

    }



    @GetMapping("/deletePet/{id}")
    public String deletePet(@PathVariable (value = "id") Long id){
        this.serviceImpl.deletePetById(id);
        return "redirect:/view";
    }
    @GetMapping("/view")
    public String showPets(Model model) {
        return findPaginated(1, "category", "asc", model);
    }

    @GetMapping("/adopt")
    public String buyPets(Model model) {
        List<Pet> pet = serviceImpl.listOfPets();
        model.addAttribute("pets", pet);
        return "Adopt";
    }


    @GetMapping(value = "/exportPdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> volunteerReport()  throws IOException {
        List<Pet> volunteers = (List<Pet>) petRepository.findAll();

        ByteArrayInputStream bis = DatabasePDFService.employeePDFReport(volunteers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=VolunteerReport.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/exportCsv")
    public void exportCSV(HttpServletResponse response)
            throws Exception {

        //set file name and content type
        String filename = "Pets-data.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<Pet> writer = new StatefulBeanToCsvBuilder<Pet>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();
        //write all employees data to csv file
        writer.write(serviceImpl.listOfPets());

    }

    @GetMapping("/search")
    public String searchMethod(Model model){
        model.addAttribute("search",new Pet());
        return "searchPet";
    }

    @PostMapping("/search")
    public String getEmployee(@ModelAttribute("search") Pet pet, Model model) {
        Pet pet1 = serviceImpl.findById(pet.getId());
        if (pet1 != null) {
            model.addAttribute("pet1", pet1);
            return "searchPet";
        } else {
            model.addAttribute("error", " not found");
            return "searchPet";
        }
    }
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 3;

        Page<Pet> page = serviceImpl.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Pet> listPets = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("listPets", listPets);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");


        return "viewPet";
    }


//    @GetMapping
//    public List<CheckOut> getAllCheck(){
//        return checkRepository.findAll();
//    }
    @GetMapping("/addCheckout")
    public String ch(Model model){
        model.addAttribute("checks",new CheckOut());
        return "payout";
    }
    @PostMapping("saveCheckout")
    public  String saveCheck(@ModelAttribute("checks") CheckOut check){
        CheckOut checks= serviceImpl.saveCheck(check);
        if(checks!=null){
            return "redirect:/thanks";
        }
        return "payout";

    }
    @GetMapping("/thanks")
    public String thanks(Model model){
        model.addAttribute("pets",new Pet());
        return "afterPayment";
    }
}
