package jocDeDausMongoDB.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de la capa de Configuration de Spring
 *
 * Anotaciones:
 *
 * @Configuration
 * Indica que una clase declara uno o mas metodos @Bean y puede ser procesada por el contenedor Spring
 * para generar definiciones de beans y solicitudes de servicio para esos beans en tiempo de ejecucion
 *
 * @EnableScheduling
 * Utilizada junto a la anotacion @Configuration.
 * Habilita la capacidad de ejecucion de tareas programadas de Spring, similar a la funcionalidad que
 * se encuentra en el espacio de nombres XML <task: *> de Spring
 *
 * @EnableAsync
 * Utilizada junto a la anotacion @Configuration.
 * Habilita la capacidad de ejecucion de metodos asincronicos de Spring, similar a la funcionalidad que
 * se encuentra en el espacio de nombres de Spring XML <task:*>
 *
 * @Bean
 * Anotación a nivel de metodo y un analogo directo del elemento XML <bean/>. La anotación admite la mayoria
 * de los atributos ofrecidos por <bean/>, tales como: metodo de inicio (init-method), metodo de destruccion
 * (destroy-method), autowiring (cableado automático), inicio lento (lazy-init), verificacion de dependencia
 * (dependency-check), depender de (depends-on) y alcance (scope).
 *
 */
@Configuration
@EnableScheduling
@EnableAsync
public class SpringConfiguration {

    /**
     * Especializacion de PlaceholderConfigurerSupport que resuelve los marcadores de posición, $ {...},
     * dentro de los valores de propiedades de definicion de un bean y las anotaciones @Value contra
     * el entorno de Spring actual y su conjunto de PropertySources.
     *
     * Cualquier propiedad local (por ejemplo, PropertiesLoaderSupport.setProperties(java.util.Properties),
     * PropertiesLoaderSupport.setLocations(org.springframework.core.io.Resource ...)) se agregan como
     * PropertySource (Propiedades Fuente).
     * La precedencia de busqueda de propiedades locales se basa en el valor de la propiedad localOverride,
     * que por defecto es falso, lo que significa que las propiedades locales deben buscarse en ultimo lugar,
     * despues de todas las PropertySources del entorno
     *
     * Este metodo diseñada como un reemplazo general de PropertyPlaceholderConfigurer. Se utiliza de forma
     * predeterminada para admitir el elemento property-placeholder al trabajar contra spring-context-3.1
     * o superior, spring-context XSD; mientras, las versiones spring-context <= 3.0 predeterminadas en
     * PropertyPlaceholderConfigurer garantizan la compatibilidad con versiones anteriores.
     *
     * @return instancia de tipo PropertyPlaceholderConfigurer que permite hacer referencia a propiedades de archivos
     * de texto en la configuracion de Spring.
     *
     * Permite externalizar ciertos valores (por ejemplo, los valores de propiedades de definicion de un bean) a un
     * archivo de texto .properties (formato Java Properties) y tambien permite obtener variables de entorno con Spring.
     *
     * Esto resulta muy util para el despliege aplicaciones ya que en este archivo se pueden configurar de forma simple
     * valores específicos del entorno (URLs de base de datos, nombres de usuario y contraseñas, etc.) sin la complejidad
     * de modificar una definición en XML.
     * Ademas, es muy facil tener varias versiones de este archivo properties, una para cada entorno en donde se
     * despliegue la aplicacion (desarrollo, testing, producción, etc.)
     *
     */

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Representa una solicitud HTTP del lado del cliente que puede recibir una ClientHttpResponse.
     * Establece un tiempo de espera a la conexion URL de 10 segundos
     *
     * @return solicitud HTTP del lado del cliente creada mediante una implementacion de
     * ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory ret = new SimpleClientHttpRequestFactory();
        ret.setReadTimeout(10000); // 10 sec
        return ret;
    }

    /**
     * Reperesenta un cliente sincrono para realizar solicitudes HTTP, exponiendo una API
     * basada en una plantilla simple sobre bibliotecas de cliente HTTP subyacentes, como
     * JDK HttpURLConnection, Apache HttpComponents y otras
     *
     * @param clientHttpRequestFactory, solicitud HTTP del lado del cliente creada mediante
     *                                 una implementación de ClientHttpRequestFactory.
     * @return cliente sincrono para realizar solicitudes HTTP
     */
    @Bean
    public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }
}
