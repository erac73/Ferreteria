package com.lunaris.Ferreteria.service;

import com.lunaris.Ferreteria.model.Producto;
import com.lunaris.Ferreteria.repository.IProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{


    private final IProductoRepository prodRepo;

    public ProductoService(IProductoRepository prodRepo) {
        this.prodRepo = prodRepo;
    }

    @Override
    public List<Producto> getProducto() {
        return prodRepo.findAll();
    }

    @Override
    public Producto searchProducto(Long codProd) {
        return prodRepo.findById(codProd).orElse(null);
    }

    @Override
    public Producto createProducto(Producto prod) {

        //validación
        if (prod == null){

            return null;
        }

        //validar cada uno de los elementos
        boolean valido = this.validatorDate(prod);

        if (valido == false){
            return null;
        }

        //Id se genera automaticamente en la DB y con esto la devolvemos junto con el producto
        return prodRepo.save(prod);

    }

    @Override
    public Producto editProducto(Long codProd, Producto prod) {

        //buscar si existe el producto
        Producto prodExistente = searchProducto(codProd);

        //validar
        if (prodExistente == null){
            return null;
        }

        //validar cada uno de los elementos
        boolean valido = this.validatorDate(prod);

        if (valido == false){
            return null;
        }

        //actulizamos los datos con el producto nuevo
        prodExistente.setCategoria(prod.getCategoria());
        prodExistente.setDescripcion(prod.getDescripcion());
        prodExistente.setMarca(prod.getMarca());
        prodExistente.setStock(prod.getStock());
        prodExistente.setPrecio(prod.getPrecio());
        prodExistente.setNombre(prod.getNombre());

        return prodRepo.save(prodExistente);
    }

    @Override
    public boolean deleteProducto(Long codProd) {

        //validar que exista
        Producto prodExistente = searchProducto(codProd);

        if (prodExistente == null){
            return false;
        }
        prodRepo.delete(prodExistente);
        return true;
    }

    public boolean validatorDate(Producto prod){

        if (prod.getNombre() == null || prod.getNombre().isBlank()){
            return false;
        }

        if (prod.getMarca() == null || prod.getMarca().isBlank()){
            return false;
        }

        if (prod.getCategoria() == null || prod.getCategoria().isBlank()){
            return false;
        }

        if (prod.getPrecio() == null || prod.getPrecio()<=0){
            return false;
        }

        if (prod.getStock() <=0){
            return false;
        }

        return  true;
    }

}
