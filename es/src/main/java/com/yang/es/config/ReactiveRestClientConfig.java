package com.yang.es.config;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import reactor.core.publisher.Mono;

public class ReactiveRestClientConfig extends AbstractReactiveElasticsearchConfiguration {
    @Override
    @Bean
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200") //
                .build();
        return ReactiveRestClients.create(clientConfiguration);

    }

}
