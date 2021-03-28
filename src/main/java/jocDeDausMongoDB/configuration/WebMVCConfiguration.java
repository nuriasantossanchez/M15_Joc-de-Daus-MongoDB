package jocDeDausMongoDB.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;
import java.util.TimeZone;


/**
 * Clase de la capa de Configuration de Spring
 * Implementa la interface WebMvcConfigurer que proporciona la configuración principal de Spring MVC.
 *
 * Anotaciones:
 *
 * @ComponentScan
 * Utilizada junto a la anotacion @Configuration.
 * Configura directivas de análisis de componentes
 * Proporciona soporte en paralelo con el elemento <context:component-scan> de Spring XML.
 * Especifica basePackages(valor de alias), para definir paquetes específicos para escanear.
 * Si no se definen paquetes específicos, se realizará un escaneo desde el paquete de la
 * clase que declara esta anotación
 *
 * @Configuration
 * Indica que una clase declara uno o más métodos @Bean y puede ser procesada por el contenedor Spring
 * para generar definiciones de beans y solicitudes de servicio para esos beans en tiempo de ejecución
 *
 * @EnableWebMvc
 * Utilizada junto a la anotacion @Configuration.
 * Habilita la configuración predeterminada de Spring MVC y registra los componentes de infraestructura
 * MVC de Spring esperados por el DispatcherServlet.
 * A su vez, importa DelegatingWebMvcConfiguration, que proporciona la configuración predeterminada
 * de Spring MVC
 *
 */
@ComponentScan(basePackages = {"jocDeDausMongoDB"})
@Configuration
@EnableWebMvc
public class WebMVCConfiguration implements WebMvcConfigurer {

    /**
     * Crea un controlador de recursos proporcionando los patrones de ruta de URL para lo cual se
     * debe invocar al controlador para que sirva recursos estáticos (por ejemplo, "/**").
     * Usa metodos adicionales del ResourceHandlerRegistration para agregar una o más
     * ubicaciones desde las que entregar contenido estático (por ejemplo, {"/", "classpath:/resources/"})
     * o para especificar un período de caché para los recursos servidos.
     *
     * @param registry, instancia de tipo ResourceHandlerRegistry, almacena registros de controladores de
     *                  recursos para servir recursos estáticos como imágenes, archivos css y otros a través
     *                  de Spring MVC, incluida la configuración de encabezados de caché optimizados para una
     *                  carga eficiente en un navegador web.
     *                  Los recursos se pueden servir desde ubicaciones en la raíz de la aplicación web,
     *                  desde la classpath y otros.
     */
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    /**
     * Proporciona funcionalidad para leer y escribir JSON, ya sea hacia y desde POJOs basicos,
     * o hacia y desde un modelo de arbol JSON de proposito general (JsonNode), asi como funcionalidad
     * relacionada para realizar conversiones.
     *
     * Es altamente personalizable para trabajar con diferentes estilos de contenido JSON y para admitir
     * conceptos de objetos mas avanzados, como polimorfismo e identidad de objetos.
     *
     * ObjectMapper tambien actua como una fabrica para clases ObjectReader y ObjectWriter mas avanzadas.
     * Mapper (y ObjectReaders, ObjectWriters) usara instancias de JsonParser y JsonGenerator
     * para implementar la lectura/escritura real de JSON
     *
     * @return instancia de tipo ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getDefault());
        return mapper;
    }

    /**
     * Realiza el mapeo de objetos, mantiene la configuracion y almacena TypeMaps, que
     * encapsulan la configuración de mapeo para un par de tipos origen y destino
     *
     * @return instancia de tipo ModelMapper, para el mapero de objetos de tipo fuente y destino
     */
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


}
