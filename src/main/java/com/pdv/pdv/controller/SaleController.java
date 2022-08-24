package com.pdv.pdv.controller;

import com.pdv.pdv.dto.ResponseDTO;
import com.pdv.pdv.dto.SaleDTO;
import com.pdv.pdv.dto.SaleInfoDTO;
import com.pdv.pdv.exceptions.InvalidOperationException;
import com.pdv.pdv.exceptions.NoItemException;
import com.pdv.pdv.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(@Autowired SaleService saleService){
        this.saleService = saleService;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity(new ResponseDTO<>("",saleService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id){
        try {
            return new ResponseEntity(new ResponseDTO<>("",saleService.getById(id)), HttpStatus.OK);
        } catch (NoItemException | InvalidOperationException error) {
            return new ResponseEntity(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO){
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity(new ResponseDTO<>("Venda realizada com sucesso!", id), HttpStatus.OK);
        } catch (NoItemException | InvalidOperationException error){
            return new ResponseEntity(new ResponseDTO<>(error.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception error){
            return new ResponseEntity(new ResponseDTO<>(error.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
