package com.mj.holley.ims.config;



import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.xml.ws.Endpoint;

import com.mj.holley.ims.service.CommonService;
import com.mj.holley.ims.service.CommonServiceImp;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;

import java.util.Arrays;

@Configuration
public class CxfConfig {

//    @Bean
//    public ServletRegistrationBean dispatcherServlet() {
//
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CXFServlet(), "/soap/*");
//        registrationBean.setName("soap");
//        return registrationBean;
//    }
    /**
     * Initialize cxf - ws
     */
    private void initCxf(ServletContext servletContext) {
//        log.debug("Initialize cxf - ws");
        ServletRegistration.Dynamic cxfServlet = servletContext.addServlet("CxfWS", new CXFServlet());
        cxfServlet.addMapping("/soap/*");
        cxfServlet.setLoadOnStartup(3);
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public Bus springBus() {
        return new SpringBus();
    }

    @Autowired
    CommonService commonService;

    /** JAX-WS **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), commonService);
        endpoint.publish("/CommonService");
        return endpoint;
    }

}
