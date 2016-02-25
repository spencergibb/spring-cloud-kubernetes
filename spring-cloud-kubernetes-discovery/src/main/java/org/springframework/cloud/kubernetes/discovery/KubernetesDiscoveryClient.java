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

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import lombok.extern.apachecommons.CommonsLog;

/**
 * @author Spencer Gibb
 */
@CommonsLog
public class KubernetesDiscoveryClient implements DiscoveryClient {

	private KubernetesDiscoveryProperties properties;
	private ServerProperties serverProperties;

	public KubernetesDiscoveryClient(
			KubernetesDiscoveryProperties properties, ServerProperties serverProperties) {
		this.properties = properties;
		this.serverProperties = serverProperties;
	}

	@Override
	public String description() {
		return "Spring Cloud Kubernetes Discovery Client";
	}

	@Override
	public ServiceInstance getLocalServiceInstance() {
		//TODO: return new DefaultServiceInstance(serviceId, host, port, false);
		return null;
	}

	@Override
	public List<ServiceInstance> getInstances(final String serviceId) {
		List<ServiceInstance> instances = new ArrayList<>();

		addInstancesToList(instances, serviceId);

		return instances;
	}

	private void addInstancesToList(List<ServiceInstance> instances, String serviceId) {
		//TODO:
	}

	public List<ServiceInstance> getAllInstances() {
		List<ServiceInstance> instances = new ArrayList<>();

		/*TODO: Response<Map<String, List<String>>> services = client .getCatalogServices(QueryParams.DEFAULT);
		for (String serviceId : services.getValue().keySet()) {
			addInstancesToList(instances, serviceId);
		}*/
		return instances;
	}

	@Override
	public List<String> getServices() {
		return new ArrayList<>(); //TODO:
	}
}
