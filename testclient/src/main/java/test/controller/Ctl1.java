package test.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;

import feign.Feign;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@RestController
public class Ctl1 {

    private RestTemplate rt;

    public Ctl1(RestTemplateBuilder builder) {
        this.rt = builder.rootUri("http://localhost:9001").build();

    }

    // rest template
    @RequestMapping("/t1")
    @ResponseBody
    public String sayhi() {
        String s = rt.getForObject("/t1", String.class);
        return "get " + s;
    }

    // feign
    @RequestMapping("/t2")
    @ResponseBody
    public String sayhi2() {
        RemoteService service = Feign.builder().target(RemoteService.class,
                "http://localhost:9001");
        String s = service.sayhi();
        return "get2 " + s;
    }

    // retrofit + rxjava2
    @RequestMapping("/t3")
    @ResponseBody
    public String sayhi3() {
        RemoteService2 service2 = new Retrofit.Builder()
                .baseUrl("http://localhost:9001")
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RemoteService2.class);
        String s = service2.sayhi().blockingFirst();
        return "get3 " + s;
    }
}
