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

import java.util.List;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;

/**
 * @author Spencer Gibb
 */
public class KubernetesServerList extends AbstractServerList<KubernetesServer> {

	private final KubernetesDiscoveryProperties properties;

	private String serviceId;

	public KubernetesServerList(KubernetesDiscoveryProperties properties) {
		this.properties = properties;
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		this.serviceId = clientConfig.getClientName();
	}

	@Override
	public List<KubernetesServer> getInitialListOfServers() {
		return getServers();
	}

	@Override
	public List<KubernetesServer> getUpdatedListOfServers() {
		return getServers();
	}

	private List<KubernetesServer> getServers() {
		/*TODO: if (this.client == null) {
			return Collections.emptyList();
		}
		Response<List<HealthService>> response = this.client.getHealthServices(
				this.serviceId, tag, this.properties.isQueryPassing(),
				QueryParams.DEFAULT);
		if (response.getValue() == null || response.getValue().isEmpty()) {
			return Collections.emptyList();
		}
		List<KubernetesServer> servers = new ArrayList<>();
		for (HealthService service : response.getValue()) {
			servers.add(new KubernetesServer(service));
		}
		return servers;*/
		return null;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("KubernetesServerList{");
		sb.append("serviceId='").append(this.serviceId).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
