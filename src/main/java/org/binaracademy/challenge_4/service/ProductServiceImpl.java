package org.binaracademy.challenge_4.service;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.challenge_4.entity.Product;
import org.binaracademy.challenge_4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductById(Long id) {
        try {
            Product product = productRepository.findById(id).orElse(null);
            if (product == null){
                log.info("Produk tidak ada");
                return null;
            }else {
                log.info("Produk ada");
                return product;
            }
        }catch (Exception e){
            log.error("Terjadi exception : "+ e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateName(Long id, String name) {
        try {

            Product product = productRepository.findById(id).orElse(null);
            if (product == null){
                log.info("Produk tidak tersedia");
                return false;
            }else {
                product.setName(name);
                productRepository.save(product);
                log.info("Berhasil update nama produk");
                return true;
            }

        }catch (Exception e){
            log.error("Terjadi exception : "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePrice(Long id, Integer price) {
        try {

            Product product = productRepository.findById(id).orElse(null);
            if (product == null){
                log.info("Produk tidak tersedia");
                return false;
            }else {
                product.setPrice(price);
                productRepository.save(product);
                log.info("Berhasil update harga produk");
                return true;
            }

        }catch (Exception e){
            log.error("Terjadi exception : "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id).orElse(null);
            if (product == null){
                log.info("Produk tidak tersedia");
                return false;
            }else {
                productRepository.delete(product);
                log.info("Berhasil hapus harga produk");
                return true;
            }
        }catch (Exception e){
            log.error("Terjadi exception : "+e.getMessage());
            return false;
        }
    }
}
