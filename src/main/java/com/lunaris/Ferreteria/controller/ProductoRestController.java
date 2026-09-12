package com.lunaris.Ferreteria.controller;

import com.lunaris.Ferreteria.model.Producto;
import com.lunaris.Ferreteria.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final IProductoService prodServ;

    public ProductoRestController(IProductoService prodServ) {
        this.prodServ = prodServ;
    }

    //READ
    @GetMapping
    public List<Producto> getProductos(){
        return prodServ.getProducto();
    }

    //Read producto especifico
    @GetMapping("/{codProd}")
    public ResponseEntity<?> searchProducto(@PathVariable Long codProd){

        Producto prod = prodServ.searchProducto(codProd);

        if (prod == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encuentra un producto con ese codigo");
        }

        return ResponseEntity.ok(prod);
    }

    //CREATE
    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody Producto prod){

        Producto productoCreado = prodServ.createProducto(prod);

        if (prod == null){
            return ResponseEntity.badRequest()
                    .body("Los datos del producto no son validos");
        }

        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(productoCreado);
    }

    //UPDATE
    @PutMapping("/{codProd}")
    public ResponseEntity<?> editPorducto(@PathVariable Long codProd,
                                          @RequestBody Producto prodUpdate){

        Producto prodEdit = prodServ.editProducto(codProd,prodUpdate);

        if (prodUpdate == null){
            return ResponseEntity.badRequest()
                    .body("No fue posible editar el producto");
        }

        return  ResponseEntity.ok(prodEdit);
    }

    //DELETE
    @DeleteMapping("/{codProd}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long codProd){

        boolean delete = prodServ.deleteProducto(codProd);

        if (delete == false){

            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro u producto con el codigo: " + codProd);
        }

        return ResponseEntity.ok("Producto eliminado");

    }

}
