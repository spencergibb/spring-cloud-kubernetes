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

package org.springframework.cloud.kubernetes.discovery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationListener;

import io.fabric8.kubernetes.api.model.EndpointAddress;
import io.fabric8.kubernetes.api.model.EndpointPort;
import io.fabric8.kubernetes.api.model.EndpointSubset;
import io.fabric8.kubernetes.api.model.Endpoints;
import io.fabric8.kubernetes.api.model.EndpointsList;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.apachecommons.CommonsLog;

/**
 * @author Spencer Gibb
 */
@CommonsLog
public class KubernetesDiscoveryClient implements DiscoveryClient, ApplicationListener<EmbeddedServletContainerInitializedEvent> {

	private KubernetesClient kubernetes;
	private KubernetesDiscoveryProperties properties;
	private ServerProperties serverProperties;

	@Value("${spring.application.name:application}")
	private String appName;

	@Value("${spring.cloud.client.ipAddress:127.0.0.1}")
	private String ipAddress;

	private AtomicInteger port = new AtomicInteger(0);

	public KubernetesDiscoveryClient(
			KubernetesClient kubernetes, KubernetesDiscoveryProperties properties, ServerProperties serverProperties) {
		this.kubernetes = kubernetes;
		this.properties = properties;
		this.serverProperties = serverProperties;
	}

	@Override
	public String description() {
		return "Spring Cloud Kubernetes Discovery Client";
	}

	@Override
	public ServiceInstance getLocalServiceInstance() {
		return new DefaultServiceInstance(this.appName, this.ipAddress, this.port.get(), false);
	}

	@Override
	public List<ServiceInstance> getInstances(final String serviceId) {
		EndpointsList endpointsList = this.kubernetes.endpoints().withField("metadata.name", serviceId).list();

		return getInstances(endpointsList);
	}

	private List<ServiceInstance> getInstances(EndpointsList endpointsList) {
		List<ServiceInstance> instances = new ArrayList<>();

		if (endpointsList != null) {
			for (Endpoints endpoints : endpointsList.getItems()) {
				String name = endpoints.getMetadata().getName();
				String uid = endpoints.getMetadata().getUid();

				for (EndpointSubset subset : endpoints.getSubsets()) {
					EndpointAddress address = subset.getAddresses().iterator().next();
					EndpointPort port = subset.getPorts().iterator().next();
					instances.add(new DefaultServiceInstance(name, address.getIp(), port.getPort(), false, endpoints.getMetadata().getLabels()));
				}
			}
		}

		return instances;
	}

	public List<ServiceInstance> getAllInstances() {
		EndpointsList endpointsList = this.kubernetes.endpoints().list();
		return getInstances(endpointsList);
	}

	@Override
	public List<String> getServices() {
		EndpointsList endpointsList = this.kubernetes.endpoints().list();
		List<String> services = new ArrayList<>();

		if (endpointsList != null) {
			for (Endpoints endpoints : endpointsList.getItems()) {
				String name = endpoints.getMetadata().getName();
				if (!services.contains(name)) {
					services.add(name);
				}
			}
		}
		return services;
	}


	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		// TODO: take SSL into account
		// Don't register the management port as THE port
		if (!"management".equals(event.getApplicationContext().getNamespace())) {
			this.port.compareAndSet(0, event.getEmbeddedServletContainer().getPort());
		}
	}
}
