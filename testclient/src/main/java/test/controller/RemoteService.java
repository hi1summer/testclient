package test.controller;

import feign.RequestLine;

public interface RemoteService {
    @RequestLine("GET /t1")
    String sayhi();
}
