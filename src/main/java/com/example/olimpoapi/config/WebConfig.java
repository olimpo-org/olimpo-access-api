import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
    public class WebConfig implements WebMvcConfigurer {    
        @Override    
        public void addCorsMappings(CorsRegistry registry) {        
            registry.addMapping("/**") // Permite CORS para todos os endpoints               
                .allowedOrigins("*") // Permite requisições de qualquer origem                
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos                
                .allowedHeaders("*")                
                .allowCredentials(false); // Se true, '*' não pode ser usado em allowedOrigins    
        }
    }
