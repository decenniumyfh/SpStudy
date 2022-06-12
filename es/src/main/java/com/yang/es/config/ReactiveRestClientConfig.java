/*
package com.yang.es.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class ReactiveRestClientConfig extends AbstractReactiveElasticsearchConfiguration {
    @Override
    @Bean
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("some-header", "on every request");

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200"*/
/*, "localhost:9291"*//*
)
                //.useSsl()
                //.withProxy("localhost:8888")
                //.withPathPrefix("ela")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                //.withDefaultHeaders(defaultHeaders)
                //.withBasicAuth(username, password)
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    return headers;
                })
                .build();
        return ReactiveRestClients.create(clientConfiguration);
    }
}
*/
