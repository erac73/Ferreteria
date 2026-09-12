package com.lunaris.Ferreteria.controller;

import com.lunaris.Ferreteria.model.Producto;
import com.lunaris.Ferreteria.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    //@Autowired
    private final IProductoService prodServ;

    public ProductoWebController(IProductoService prodServ) {
        this.prodServ = prodServ;
    }

    //traer la lista de  productos
    @GetMapping
    public String traerProductos(Model model){

        model.addAttribute("productos",
                prodServ.getProducto()
        );

        return  "productos/lista";
    }

    //traer el formulario
    @GetMapping("/nuevo")
    public String mostarFormulario(Model model){

        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Registrar Porducto");

        return "productos/formulario";
    }

    //guardar el formulario
    @PostMapping("/crear")
    public String crearPorducto(@ModelAttribute Producto producto,Model model){

        Producto resultado;

        if (producto.getCodProducto() == null) {
            resultado = prodServ.createProducto(producto);
        }else {
            resultado  = prodServ.editProducto(
                    producto.getCodProducto(), producto
            );
        }

        if (resultado == null){
            model.addAttribute("producto", producto);
            model.addAttribute("titulo",
                    producto.getCodProducto() == null
                    ? "Registrar producto" : "Editar producto"
            );
            model.addAttribute(
                    "error",
                    "Revisa los datos. Nombre, marca y categoria son obligatorios. " +
                            "El precio debe ser mayor a cero y el stock no puede se negativo."
            );

            return "productos/formulario";
        }
        return "redirect:/productos";
    }

    @GetMapping("/editar/{codProd}")
    public String mostrarFomularioEditar(
            @PathVariable Long codProd,
            Model model){

        Producto producto = prodServ.searchProducto(codProd);

        if (producto == null){
            return  "redirect:/productos";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar producto");

        return  "productos/formulario";
    }

    @PostMapping("/eliminar/{codProd}")
    public String eliminarProducto(@PathVariable Long codProd){

        prodServ.deleteProducto(codProd);

        return "redirect:/productos";
    }
}
