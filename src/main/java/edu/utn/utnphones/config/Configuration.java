package edu.utn.utnphones.config;

import edu.utn.utnphones.session.ClientSessionFilter;
import edu.utn.utnphones.session.EmployeeSessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableScheduling
@EnableCaching
public class Configuration {

    @Autowired
    ClientSessionFilter clientSessionFilter;

    @Autowired
    EmployeeSessionFilter employeeSessionFilter;

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(clientSessionFilter);
        registration.addUrlPatterns("/api/web/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean myFilterBis() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(employeeSessionFilter);
        registration.addUrlPatterns("/api/backoffice/*");
        return registration;
    }

}
