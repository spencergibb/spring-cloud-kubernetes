/*
 * Copyright 2013-2016 the original author or authors.
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

import com.netflix.client.config.DefaultClientConfigImpl;

import org.junit.Test;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Spencer Gibb
 */
public class KubernetesServerListTests {

	@Test
	public void serverListWorks() {
		KubernetesProperties properties = new KubernetesProperties();
		properties.setMasterUrl("http://192.168.64.2:8080");
		KubernetesServerList serverList = new KubernetesServerList(new DefaultKubernetesClient(properties.toConfig()), new KubernetesDiscoveryProperties());

		DefaultClientConfigImpl config = new DefaultClientConfigImpl();
		config.setClientName("kubernetes");

		serverList.initWithNiwsConfig(config);

		List<KubernetesServer> servers = serverList.getInitialListOfServers();

		assertThat("servers was wrong size", servers, hasSize(1));

		KubernetesServer server = servers.get(0);
		assertThat("server was wrong", server.getPort(), is(6443));
	}
}
