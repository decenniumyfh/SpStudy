package com.yang.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.indices.Alias;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Slf4j
public class RestClientConfig extends AbstractReactiveElasticsearchConfiguration {


    private String esname = "elastic";
    private String espwd = "elasticdev";

    private CertsEnum certs = CertsEnum.P12;

    private String hostname = "120.46.142.51";
    private String schema = "https";

    private Integer port =9200;

    enum CertsEnum{
        P12("iL1f6g72QVGlTA_Ov2m6pA","D:\\certs\\http.p12"),
        CA(null,"D:\\certs\\http_ca.crt");
        private String keyStorePass;
        private String path;

        CertsEnum(String keyStorePass,String path){
            this.keyStorePass = keyStorePass;
            this.path = path;
        }

    }


    public SSLContext initSSLContext(CertsEnum certsEnum ){
        Path trustStorePath = null;
        SSLContext sslContext = null;
        KeyStore trustStore = null;
        try{
            trustStorePath = Paths.get(certsEnum.path);
            switch (certsEnum){
                case P12:
                    String keyStorePass=certsEnum.keyStorePass;
                    trustStore = KeyStore.getInstance("pkcs12");
                    try (InputStream is = Files.newInputStream(trustStorePath)) {
                        trustStore.load(is, keyStorePass.toCharArray());
                    }

                    break;
                case CA:
                    CertificateFactory factory = CertificateFactory.getInstance("X.509");
                    trustStore = KeyStore.getInstance("pkcs12");
                    try (InputStream is = Files.newInputStream(trustStorePath)) {
                        Certificate trustedCa = factory.generateCertificate(is);
                        trustStore.load(null,null );
                        trustStore.setCertificateEntry("ca", trustedCa);
                    }

                    break;
            }
            SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, null);
            sslContext = sslBuilder.build();

        }catch (Exception e){
            log.error("证书信息错误",e);
            throw new RuntimeException("证书信息错误");
        }

        return sslContext;
    }
    @SneakyThrows
    @Bean
    @ConditionalOnMissingBean(ElasticsearchClient.class)
    public ElasticsearchClient elasticsearchClient(){

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(esname, espwd));

        //tag::create-client
        // Create the low-level client
        final SSLContext finalSslContext = initSSLContext(certs);
        RestClient restClient = RestClient.builder(
                new HttpHost(hostname, port,schema)).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setSSLContext(finalSslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultCredentialsProvider(credentialsProvider)
        ).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }


    @Override
    @Bean
    @ConditionalOnMissingBean(ReactiveElasticsearchClient.class)
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("some-header", "on every request");
        final SSLContext finalSslContext = initSSLContext(certs);
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(hostname+":"+port)
                .usingSsl(finalSslContext,NoopHostnameVerifier.INSTANCE)

                //.withProxy("localhost:8888")
                //.withPathPrefix("ela")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                //.withDefaultHeaders(defaultHeaders)
                .withBasicAuth(esname, espwd)

                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    return headers;
                })

                .build();
        return ReactiveRestClients.create(clientConfiguration);
    }

    /*@Bean(name = {
            "elasticsearchOperations", "elasticsearchTemplate" })
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {

        return new ElasticsearchTemplate(elasticsearchClient());
    }*/






}
