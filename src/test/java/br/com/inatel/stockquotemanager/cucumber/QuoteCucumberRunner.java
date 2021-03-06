package br.com.inatel.stockquotemanager.cucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features")
@SpringBootTest
@ContextConfiguration(classes = SpringBootApplication.class)
public class QuoteCucumberRunner {

}
