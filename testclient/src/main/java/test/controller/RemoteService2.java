package test.controller;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RemoteService2 {
    @GET("/t1")
    Observable<String> sayhi();
}
