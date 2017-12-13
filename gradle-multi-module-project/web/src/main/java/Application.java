import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yangxiuchu
 * @DATE 2017/12/10 13:03
 */
@ComponentScan(basePackages = {"org.yxc"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
