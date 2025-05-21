package vn.edu.hcmuaf.fit.webbansach;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebBanSachApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebBanSachApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebBanSachApplication.class);
    }
}

