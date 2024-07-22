package lm.Gestion_pedidos;

import java.awt.EventQueue;
import lm.Gestion_pedidos.controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GestionPedidosApplication {

	public static void main(String[] args) {
            ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(GestionPedidosApplication.class)
                    .headless(false)
                    .web(WebApplicationType.NONE)
                    .run(args);
            
            EventQueue.invokeLater(() -> {
                Controller controller = contextSpring.getBean(Controller.class);
                controller.viewHomePage();
            });
	}

}
