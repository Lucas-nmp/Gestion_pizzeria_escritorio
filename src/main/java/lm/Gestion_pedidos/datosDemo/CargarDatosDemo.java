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
    private final List<String> PIZZAS;
    private final List<String> PASTA;
    private final List<String> CARNE;
    private final List<String> BOCADILLOS;
    private final List<String> TORTILLAS;
    private final List<String> APERITIVOS;
    private final List<String> BEBIDAS;

    public CargarDatosDemo(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.CATEGORIES = Arrays.asList("Pizzas", "Pasta", "Carne", "Tortillas", "Bocadillos", "Aperitivos", "Bebidas");
        this.INGREDIENTS = Arrays.asList("Masa casera", "tomate", "mozzarella", "orégano", "cebolla", "york", "olivas", "champiñón", "anchoas", "alcaparras",
                "espárragos", "alcachofas", "roquefort", "pimiento", "guindilla", "pepinillos", "pepperoni", "bacon", "atún", "calamares", "gambas", "ajo", 
                "anchoas", "salsa barbacoa", "carne", "chorizo", "maíz", "tomate natural", "nata", "kebab de pollo", "salmón", "carne con tomate", "salchichas", "huevo",
                "pollo", "jamón serrano", "emmental", "edam", "piña", "palitos de mar", "Sin lactosa");
        this.PIZZAS = Arrays.asList("Pericote", "Roquefort", "Olimpia", "Ciccio", "Don Pepino", "Bacon", "Fruto del mar", "Vegetal", "Diávolo", "Napolitana", "Atún", "Barbacoa",
                "4 Estaciones", "Primavera", "Prosciutto", "York", "Filipensas", "Carbonara", "Finiculi", "Siciliana", "Pepperoni", "Láctea", "Kebab", "Bella Napoli", 
                "Margherita", "Gamsal", "Bolognesa" , "Santiago", "pollinata", "Serrana", "Fungi", "4 Quesos", "Pollo", "Piña", "Hawaiana") ;
        this.PASTA = Arrays.asList("Lasaña", "Macarrones") ;
        this.CARNE = Arrays.asList("Callos Caseros") ;
        this.TORTILLAS = Arrays.asList( "Francesa", "Jamón", "Gambas") ;
        this.BOCADILLOS = Arrays.asList( "Francesa", "Jamón", "Gambas", 
                "Lomo", "Perrito", "Hamburguesa", "Bacon", "Sandwich", "Hamburguesa completa") ;
        this.APERITIVOS = Arrays.asList("olivas rellenas de anchoa", "Pepinillos", "Olivas violadas", "Banderitas picantes") ;
        this.BEBIDAS = Arrays.asList("Cerveza", "Coca-cola", "Fanta Naranja", "Fanta Limón", "Agua 50cl");
        
        
    }

    
    
    
    
}
