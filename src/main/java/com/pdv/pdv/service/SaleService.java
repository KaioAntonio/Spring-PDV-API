package com.pdv.pdv.service;

import com.pdv.pdv.dto.ProductDTO;
import com.pdv.pdv.dto.ProductsInfoDTO;
import com.pdv.pdv.dto.SaleDTO;
import com.pdv.pdv.dto.SaleInfoDTO;
import com.pdv.pdv.entity.ItemSale;
import com.pdv.pdv.entity.Product;
import com.pdv.pdv.entity.Sale;
import com.pdv.pdv.entity.User;
import com.pdv.pdv.exceptions.InvalidOperationException;
import com.pdv.pdv.exceptions.NoItemException;
import com.pdv.pdv.repository.ItemSaleRepository;
import com.pdv.pdv.repository.ProductRepository;
import com.pdv.pdv.repository.SaleRepository;
import com.pdv.pdv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    public List<SaleInfoDTO> findAll(){
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    private SaleInfoDTO getSaleInfo(Sale sale) {
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        saleInfoDTO.setUser(sale.getUser().getName());
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getProductInfo(sale.getItems()));
        return saleInfoDTO;
    }

    private List<ProductsInfoDTO> getProductInfo(List<ItemSale> items) {
        return items.stream().map(item -> {
            ProductsInfoDTO productsInfoDTO = new ProductsInfoDTO();
            productsInfoDTO.setId(item.getId());
            productsInfoDTO.setDescription(item.getProduct().getDescription());
            productsInfoDTO.setQuantity(item.getQuantity());
            return productsInfoDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public long save(SaleDTO sale){

        Optional<User> optional = userRepository.findById(sale.getUserid());

        if (optional.isPresent()){
            User user = optional.get();

            Sale newSale = new Sale();
            newSale.setUser(user);
            newSale.setDate(LocalDate.now());
            List<ItemSale> items = getItemSale(sale.getItems());
            Sale s = saleRepository.save(newSale);

            saveItemSale(items, newSale);

            return newSale.getId();
        } else {
            throw new NoItemException("Usuário não encontrado!");
        }
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale) {
        for (ItemSale item: items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductDTO> products) {

        if(products.isEmpty()){
            throw new InvalidOperationException("Não é possivel adicionar a venda sem itens!");
        }

        return products.stream().map(item -> {
            Product product = productRepository.findById(item.getProductid())
                    .orElseThrow(() -> new NoItemException("Item da venda não encontrado"));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if(product.getQuantity() == 0) {
                throw new NoItemException("Produto sem estoque.");
            } else if (product.getQuantity() < item.getQuantity()){
                throw new InvalidOperationException(String.format("A quantidade de itens da venda (%s) " +
                        "é maior que a quantidade disponível no estoque (%s)", item.getQuantity(), product.getQuantity()));
            }

            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public SaleInfoDTO getById(long id) {
        Optional<Sale> optional = saleRepository.findById(id);
        if (optional.isPresent()){
            Sale sale = optional.get();
            return getSaleInfo(sale);
        } else {
            throw new NoItemException("Venda não encontrada!");
        }


    }
}
