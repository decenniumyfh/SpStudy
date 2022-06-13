import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.MultiTermLookup;
import co.elastic.clients.elasticsearch._types.aggregations.MultiTermsAggregation;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.elasticsearch.indices.*;
import com.yang.es.EsApplication;
import com.yang.es.bean.Student;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
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



    @SneakyThrows
    @Test
    public void delIndex(){
        String index = "student";
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index(index).build();
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
        assertEquals(deleteIndexResponse.acknowledged(),true);

    }


    @SneakyThrows
    @Test
    public void crtStuIndex(){
        String index = "student";
        Map<String, Property> fields = Collections.singletonMap("keyword", Property.of(p -> p.keyword(k -> k.ignoreAbove(256))));
        Property text = Property.of(p -> p.text(t -> t.fields(fields)));
        Property integer = Property.of(p->p.integer(new IntegerNumberProperty.Builder().index(true).build()));
        Property boolean_ = Property.of(p->p.boolean_(new BooleanProperty.Builder().index(true).build()));
        //es支持的时间类型有限，必须指定格式
        Property date_ = Property.of(p->p.date(new DateProperty.Builder().format("yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis").index(true).build()));

        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index(index)
                .mappings(m->m.properties("num",integer)
                        .properties("sex",boolean_)
                        .properties("schoolName",text)
                        .properties("grade",integer)
                        .properties("userName",p-> p
                                .object(o->o
                                        .properties("first",text)
                                        .properties("last",text)
                                )
                        )
                        .properties("joinDate",date_)
                )
                .settings(s->s.numberOfReplicas("1").numberOfShards("1")).build();
        log.info(createIndexRequest.toString());

        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest);

        assertEquals(createIndexResponse.acknowledged(),true);
        GetMappingResponse mr = client.indices().getMapping(mrb -> mrb.index(index));
        assertNotNull(mr.result());


    }


    @SneakyThrows
    @Test
    public void getIndex(){
        String index = "student";

        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index(index).build();

        GetIndexResponse  getIndexResponse= client.indices().get(getIndexRequest);
        log.info(getIndexResponse.toString());


    }

    private Student crtStu(int num,boolean sex,String schoolName,String first,String last,int grade,Date date){
        Student student1 = new Student();
        student1.setNum(num);
        student1.setSex(sex);
        student1.setSchoolName(schoolName);
        Student.UserName userName1 = student1.new UserName();
        userName1.setFirst(first);
        userName1.setLast(last);
        student1.setUserName(userName1);
        student1.setGrade(grade);
        student1.setJoinDate(date);

        return student1;
    }
    @SneakyThrows
    @Test
    public void addStudent(){
        String index = "student";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Student> list = new ArrayList<>();
        list.add(crtStu(1,true,"七中","林","蛋大",66,sdf.parse("2020-01-02 15:30:45")));
        list.add(crtStu(2,true,"七中","楚","中天",50,sdf.parse("2019-09-02 15:30:04")));
        list.add(crtStu(3,false,"一中","张","美丽",70,sdf.parse("2021-10-12 12:40:15")));
        list.add(crtStu(4,true,"一中","李","翠花",100,sdf.parse("2008-12-22 17:20:32")));
        list.add(crtStu(5,true,"尚德","赵","日天",90,sdf.parse("2003-02-16 09:37:59")));
        list.add(crtStu(6,true,"尚德","杨","顶天",66,sdf.parse("2012-03-17 08:59:55")));
        list.add(crtStu(7,true,"树德","叶","良辰",66,sdf.parse("2022-04-19 20:00:40")));
        list.add(crtStu(8,false,"树德","林","美丽",89,sdf.parse("2022-06-01 18:00:40")));
        list.add(crtStu(9,true,"七中","张","全蛋",75,sdf.parse("2021-07-25 11:22:35")));
        list.add(crtStu(10,true,"一中","欧阳","狗蛋",60,sdf.parse("2019-11-13 05:59:00")));


        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Student student : list) {
            br.operations(op -> op           //<1>
                    .index(idx -> idx            //<2>
                            .index(index)       //<3>
                            .id(student.getNum()+"")
                            .document(student)
                    )
            );
        }

        BulkResponse result = client.bulk(br.build());

        // Log errors, if any
        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
        //end::bulk-objects

    }

    @SneakyThrows
    @Test
    public void delIndexStu(){
//https://blog.csdn.net/u010454030/article/details/71271324
        String index = "student";
        //es根据条件删除文档的功能官网默认关闭，如需要需手动安装

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index(index).build();
        client.indices().delete(deleteIndexRequest);

    }
    @SneakyThrows
    @Test
    public void delIndexStuByCOnd(){
        //https://blog.csdn.net/u010454030/article/details/71271324
        //https://codeantenna.com/a/s6RI6qvywQ
        String index = "student";
        //es根据条件删除文档的功能官网默认关闭，如需要需手动安装
        DeleteRequest deleteRequest = new DeleteRequest.Builder().index(index).id("1").build();
        DeleteResponse deleteResponse = client.delete(deleteRequest);
        log.info(deleteResponse.toString());



    }




    @Test
    public void searchMatch() throws Exception {

        //tag::search-simple

        String index = "student";

        Query bySex = MatchQuery.of(m -> m
                .field("sex")
                .query(true)
        )._toQuery();
        Query bySchoolName = MatchQuery.of(m -> m
                .field("schoolName.keyword")
                .query("一中")
        )._toQuery();
        //obj类型对象查询
        Query byFirstName = MatchQuery.of(m -> m
                .field("userName.first")
                .query("张")
        )._toQuery();
        //时间范围查询
        Query date_ = RangeQuery.of(m->m
                .field("joinDate")
                .format("yyyy-MM-dd HH:mm:ss")
                .from("2000-08-02 15:30:45")
                .to("2023-12-02 15:30:45")

        )._toQuery();




        Aggregation schoolNameAgg = Aggregation.of(m->m
                .terms(t->t.field("schoolName.keyword").size(100))
                );
        Aggregation sexAgg = Aggregation.of(m->m
                .terms(t->t.field("sex").size(100))
        );

        //es多条件分组聚合MultiTermsAggregation支持排序,如果不用排序可 using nested terms aggregation or composite aggregations
        List<MultiTermLookup> list = new ArrayList<>();
        MultiTermLookup multi_schoolName = MultiTermLookup.of(t->t.field("schoolName.keyword"));
        MultiTermLookup multi_sex = MultiTermLookup.of(t->t.field("sex"));
        list.add(multi_schoolName);
        list.add(multi_sex);
        MultiTermsAggregation multiTermsAggregation = MultiTermsAggregation.of(m->m.terms(list));


        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(index)
                .query(q -> q.bool(b->b
                        //.must(bySex)
                        //.must(bySchoolName)
                        //.must(byFirstName)
                        .filter(date_)
                ))
                //.aggregations("schoolNameAgg",schoolNameAgg)
                //.aggregations("sexAgg",sexAgg)
                .aggregations("schoolAndSexAgg",multiTermsAggregation._toAggregation())
                .build();



        log.info(searchRequest.toString());
        SearchResponse<Student> response = client.search(searchRequest,Student.class);

        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

        if (isExactResult) {
            log.info("There are " + total.value() + " results");
        } else {
            log.info("There are more than " + total.value() + " results");
        }
        log.info(response.toString());
        /*List<Hit<Student>> hits = response.hits().hits();
        for (Hit<Student> hit: hits) {
            Student student = hit.source();
            log.info("Found Student " + student.getNum() + ","+student);
        }*/


    }








}
