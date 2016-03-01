/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.springframework.cloud.kubernetes.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class SampleKubernetesApp {

	@Value("${spring.application.name:kubernetes}")
	String appName;

	@Autowired
	LoadBalancerClient lb;

	@Autowired
	DiscoveryClient discovery;

	@RequestMapping("/")
	public ServiceInstance choose() {
		return this.lb.choose(this.appName);
	}

	@RequestMapping("/list")
	public List<ServiceInstance> instances() {
		return this.discovery.getInstances(this.appName);
	}

	@RequestMapping("/local")
	public ServiceInstance local() {
		return this.discovery.getLocalServiceInstance();
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleKubernetesApp.class, args);
	}
}