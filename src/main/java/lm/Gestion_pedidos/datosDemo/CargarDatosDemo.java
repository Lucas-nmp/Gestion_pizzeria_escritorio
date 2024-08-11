package lm.Gestion_pedidos.datosDemo;

import java.util.Arrays;
import java.util.List;
import lm.Gestion_pedidos.service.CategoryService;

/**
 *
 * @author Lucas
 */
public class CargarDatosDemo {
    
    
    private final CategoryService categoryService;
    private final List<String> CATEGORIES;
    private final List<String> INGREDIENTS;
    private final List<String> PRODUCTS;

    public CargarDatosDemo(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.CATEGORIES = Arrays.asList("Pizzas", "Pasta", "Carne", "Tortillas", "Bocadillos", "Aperitivos", "Bebidas");
        this.INGREDIENTS = Arrays.asList("Masa casera", "tomate", "mozzarella", "orégano", "cebolla", "york", "olivas", "champiñón", "anchoas", "alcaparras",
                "espárragos", "alcachofas", "roquefort", "pimiento", "guindilla", "pepinillos", "pepperoni", "bacon", "atún", "calamares", "gambas", "ajo", 
                "anchoas", "salsa barbacoa", "carne", "chorizo", "maíz", "tomate natural", "nata", "kebab de pollo", "salmón", "carne con tomate", "salchichas", "huevo",
                "pollo", "jamón serrano", "emmental", "edam", "piña", "palitos de mar");
        this.PRODUCTS = Arrays.asList("Pericote", "Roquefort", "Olimpia");
    }

    
    
    
    
}
