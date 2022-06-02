package com.yang.concurrent.service.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CompletionServiceTest {

    public static void main(String[] args) {

        //1、默认创建线程数:corePoolSize；最大创建线程数:maximumPoolSize
        //2、待执行任务submit()后，将存入队列等待执行
        //3、当待执行任务数超过队列大小,无法存入，则会根据maximumPoolSize参数继续创建新的线程，至只线程池中存活线程达到最大值：maximumPoolSize
        //4、当前配置的是有界队列，当队列达到最大值，将抛出异常：java.util.concurrent.RejectedExecutionException
        LinkedBlockingDeque queue = new LinkedBlockingDeque<>(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,10,5, TimeUnit.SECONDS,queue);
        CompletionService completionService = new ExecutorCompletionService(threadPoolExecutor);

        List<Callable> callableList = new ArrayList<Callable>();
        int size =10;
        for(int i=0;i<size;i++){
            MyCallable myCallable = new MyCallable("call"+i);
            callableList.add(myCallable);
            System.out.println("添加任务"+"call"+i);
            completionService.submit(myCallable);
        }

        for(int i=0;i<size;i++){
            try {
                System.out.println("获取执行结果："+completionService.take().get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        threadPoolExecutor.shutdown();

    }
}
