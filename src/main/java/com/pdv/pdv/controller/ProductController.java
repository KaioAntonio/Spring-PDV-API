package com.pdv.pdv.controller;

import com.pdv.pdv.dto.ProductDTO;
import com.pdv.pdv.dto.ResponseDTO;
import com.pdv.pdv.entity.Product;
import com.pdv.pdv.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;
    private ModelMapper mapper;

    public ProductController(@Autowired ProductRepository productRepository){
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody ProductDTO product){
        try{
            return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.CREATED);
        }catch (Exception error){
            return new ResponseEntity(new ResponseDTO<>(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody ProductDTO product){
        try {
            return new ResponseEntity(productRepository.save(mapper.map(product,Product.class)), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity(new ResponseDTO<>(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        try {
            productRepository.deleteById(id);
            return new ResponseEntity(new ResponseDTO<>("Product removed with sucess!"), HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity(new ResponseDTO<>(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
