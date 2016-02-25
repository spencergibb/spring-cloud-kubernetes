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

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import lombok.Data;

import static io.fabric8.kubernetes.client.Config.DEFAULT_LOGGING_INTERVAL;
import static io.fabric8.kubernetes.client.Config.DEFAULT_ROLLING_TIMEOUT;

/**
 * Defines configuration for service discovery and registration.
 *
 * @author Spencer Gibb
 */
@ConfigurationProperties("kubernetes")
@Data
public class KubernetesProperties {

	/** Is service discovery enabled? */
	private boolean enabled = true;

	private boolean trustCerts;
	private String masterUrl = "https://kubernetes.default.svc";
	private String apiVersion = "v1";
	private String namespace;
	private String caCertFile;
	private String caCertData;
	private String clientCertFile;
	private String clientCertData;
	private String clientKeyFile;
	private String clientKeyData;
	private String clientKeyAlgo = "RSA";
	private String clientKeyPassphrase = "changeit";
	private String username;
	private String password;
	private String oauthToken;
	private int watchReconnectInterval = 1000;
	private int watchReconnectLimit = -1;
	private int connectionTimeout = 10 * 1000;
	private int requestTimeout = 10 * 1000;
	private long rollingTimeout = DEFAULT_ROLLING_TIMEOUT;
	private int loggingInterval = DEFAULT_LOGGING_INTERVAL;
	private String httpProxy;
	private String httpsProxy;
	private String[] noProxy;

	public Config toConfig() {
		return new ConfigBuilder()
				.withApiVersion(this.apiVersion)
				.withCaCertData(this.caCertData)
				.withCaCertFile(this.caCertFile)
				.withClientCertData(this.clientCertData)
				.withClientCertFile(this.clientCertFile)
				.withClientKeyAlgo(this.clientKeyAlgo)
				.withClientKeyData(this.clientKeyData)
				.withClientKeyFile(this.clientKeyFile)
				.withClientKeyPassphrase(this.clientKeyPassphrase)
				.withConnectionTimeout(this.connectionTimeout)
				.withHttpProxy(this.httpProxy)
				.withHttpsProxy(this.httpsProxy)
				.withLoggingInterval(this.loggingInterval)
				.withMasterUrl(this.masterUrl)
				.withNamespace(this.namespace)
				.withNoProxy(this.noProxy)
				.withOauthToken(this.oauthToken)
				.withPassword(this.password)
				.withRequestTimeout(this.requestTimeout)
				.withRollingTimeout(this.rollingTimeout)
				.withTrustCerts(this.trustCerts)
				.withUsername(this.username)
				.withWatchReconnectInterval(this.watchReconnectInterval)
				.withWatchReconnectLimit(this.watchReconnectLimit)
				.build();
	}
}
