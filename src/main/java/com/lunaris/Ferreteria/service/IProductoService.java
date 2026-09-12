package com.lunaris.Ferreteria.service;

import com.lunaris.Ferreteria.model.Producto;

import java.util.List;

public interface IProductoService {

    //Métodos para el CRUD

    //READ
    List<Producto> getProducto();
    Producto searchProducto(Long codProd);

    //CREATE
    Producto createProducto(Producto prod);

    //UPDTE
    Producto editProducto(Long codProd, Producto prod);

    //DELETE
    boolean deleteProducto(Long codProd);

}
