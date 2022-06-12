package com.yang.es.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.yang.es.service.EsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("esService")
public class EsServiceImpl implements EsService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @SneakyThrows
    @Override
    public void test() {
        System.out.println(elasticsearchClient.info());
    }
}
