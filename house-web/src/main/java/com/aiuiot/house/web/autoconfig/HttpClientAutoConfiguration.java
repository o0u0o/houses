package com.aiuiot.house.web.autoconfig;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //声明为Java Config
@ConditionalOnClass({HttpClient.class})
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

	private final HttpClientProperties properties;

	public HttpClientAutoConfiguration(HttpClientProperties properties) {
		this.properties = properties;
	}

	/**
	 * HttpClient bean定义
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(HttpClient.class)
	public CloseableHttpClient httpClient() {
		// 1. 连接池管理器（替代 4.x 中 builder 直接 setMaxConnPerRoute）
		PoolingHttpClientConnectionManager connectionManager =
				PoolingHttpClientConnectionManagerBuilder.create()
						.setMaxConnPerRoute(properties.getMaxConnPerRoute())
						.build();

		// 2. 请求超时配置（Timeout 对象替代 int 毫秒）
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(Timeout.ofMilliseconds(properties.getConnectTimeOut()))
				.setResponseTimeout(Timeout.ofMilliseconds(properties.getSocketTimeOut()))
				.build();

		// 3. 构建客户端
		return HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
				.setUserAgent(properties.getAgent())
				// 如需禁用连接复用（替代 NoConnectionReuseStrategy）：
				// .setConnectionReuseStrategy((req, res, ctx) -> false)
				.build();
	}

}
