package com.pdv.pdv.controller;

import com.pdv.pdv.dto.SaleDTO;
import com.pdv.pdv.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(@Autowired SaleService saleService){
        this.saleService = saleService;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity(saleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id){
        return new ResponseEntity(saleService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO){
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity("Venda realizada com sucesso: " + id, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
