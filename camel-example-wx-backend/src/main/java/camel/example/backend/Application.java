/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package camel.example.backend;

import camel.example.backend.domain.Attendee;
import camel.example.backend.domain.AttendeeInfo;
import camel.example.backend.repository.Database;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
            new CamelHttpTransportServlet(), "/camel-rest-example/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            restConfiguration()
                .contextPath("/camel-rest-example").apiContextPath("/api-doc")
                    .apiProperty("api.title", "Camel REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true")
                    .apiContextRouteId("doc-api")
                .bindingMode(RestBindingMode.json);

            rest("/meeting/").description("The service for the meeting")
                .post("/checkin").type(Attendee.class).description("Attend the meeting")
                    .route().routeId("checkin-api")
                    .to("direct:register")
                    .endRest()
                .get("checkin/{id}").outType(AttendeeInfo.class).description("Get the attendee information by ID")
                    .route().routeId("checkin-date-api")
                    .bean(Database.class, "findAttendee(${header.id})")
                    .endRest()
                .get("checkin/All").outTypeList(AttendeeInfo.class).description("Get all attendee inforamtions")
                    .route().routeId("checkin-information-api")
                    .bean(Database.class, "findAllAttendee")
                    .endRest();
            
        }
    }

    @Component
    class Backend extends RouteBuilder {

        @Override
        public void configure() {
            // A first route generates attendeeInfo and store them in DB
            from("direct:register")
                .routeId("generate-order").convertBodyTo(AttendeeInfo.class)
                .to("jpa:org.apache.camel.example.spring.boot.rest.jpa.AttendeeInfo")
                .log("Inserted new attendee information ${body.id}");

           
        }
    }
}
