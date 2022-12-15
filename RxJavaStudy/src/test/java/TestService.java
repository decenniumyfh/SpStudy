import com.yang.RxJavaStudy.RxJavaStudyApplication;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RxJavaStudyApplication.class)
//@TestPropertySource(locations = "classpath:application-dev.properties")
@Slf4j
public class TestService {

    @Test
    public void hello() {
        String[] args = {"1","2","3"};
        Flowable.fromArray(args).subscribe(s -> System.out.println("Flowable " + s + "!"));
        Observable.fromArray(args).subscribe(s -> System.out.println("Observable " + s + "!"));
    }
}
