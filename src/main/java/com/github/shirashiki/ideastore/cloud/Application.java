/*
 * 
 * Copyright 2014 Silvio Hirashiki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.github.shirashiki.ideastore.cloud;

import com.github.shirashiki.ideastore.cloud.entity.VideoRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Tell Spring to automatically inject any dependencies that are marked in
//our classes with @Autowired
@EnableAutoConfiguration

//Tell Spring that this object represents a Configuration for the
//application
@Configuration

//Tell Spring to turn on WebMVC (e.g., it should enable the DispatcherServlet
//so that requests can be routed to our Controllers)
@EnableWebMvc

//Tell Spring to automatically create a JPA implementation of our
//VideoRepository
@EnableJpaRepositories(basePackageClasses = VideoRepository.class)

//Tell Spring to go and scan our controller package (and all sub packages) to
//find any Controllers or other components that are part of our application.
//Any class in this package that is annotated with @Controller is going to be
//automatically discovered and connected to the DispatcherServlet.
@ComponentScan
public class Application {

	// Tell Spring to launch our app!
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


	}

}
