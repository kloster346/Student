package demo.ch7_5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"demo.ch7_5.mapper"})
public class Ch75Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch75Application.class, args);
    }

}
