import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import com.yang.es.EsApplication;
import com.yang.es.bean.Product;
import com.yang.es.config.RestClientConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EsApplication.class)
//@TestPropertySource(locations = "classpath:application-dev.properties")
@Slf4j
public class TestRestClientConfig {

    @Autowired
    private ElasticsearchClient client;
    String indexName = "es_index_name";
    String aliasIndexName = "es_alias_index_name";

    @Test
    public void createIndex() throws IOException {
        RestClientConfig config = new RestClientConfig();


        Map<String, Property> propertyMap = new HashMap<>();
        propertyMap.put("name",new Property(new TextProperty.Builder().index(true).store(true).build()));
        propertyMap.put("age",new Property(new IntegerNumberProperty.Builder().index(false).build()));
        propertyMap.put("sex",new Property(new BooleanProperty.Builder().index(false).build()));

        TypeMapping typeMapping = new TypeMapping.Builder().properties(propertyMap).build();
        IndexSettings indexSettings = new IndexSettings.Builder().numberOfShards(String.valueOf(1)).numberOfReplicas(String.valueOf(0)).build();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                .index(indexName)
                .aliases(aliasIndexName, new Alias.Builder().isWriteIndex(true).build())
                .mappings(typeMapping)
                .settings(indexSettings)
                .build();

        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest);
        System.out.println(createIndexResponse.acknowledged());
    }

    @SneakyThrows
    @Test
    public void search(){
        SearchResponse<Product> search1 = client.search(s -> s
                        .index("products")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value(v -> v.stringValue("bag"))
                                )),
                Product.class);

        for (Hit<Product> hit: search1.hits().hits()) {
            Product pd = hit.source();
            System.out.println(pd);
        }
    }

    @Test
    public void testGetMapping() throws Exception {
        // See also VariantsTest.testNestedTaggedUnionWithDefaultTag()
        // and https://github.com/elastic/elasticsearch-java/issues/45
        String index = "testindex";

        Map<String, Property> fields = Collections.singletonMap("keyword", Property.of(p -> p.keyword(k -> k.ignoreAbove(256))));
        Property text = Property.of(p -> p.text(t -> t.fields(fields)));

        client.indices().create(c -> c
                .index(index)
                .mappings(m -> m
                        .properties("id", text)
                        .properties("name", p -> p
                                .object(o -> o
                                        .properties("first", text)
                                        .properties("last", text)
                                )
                        )
                )
        );

        GetMappingResponse mr = client.indices().getMapping(mrb -> mrb.index(index));

        assertNotNull(mr.result().get(index));
        assertNotNull(mr.result().get(index).mappings().properties().get("name").object());
    }



}
