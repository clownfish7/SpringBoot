package com.clownfish7.springbootwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzy
 * @classname HaloController
 * @description TODO
 * @create 2019-12-02 2:12 PM
 */
@RestController
public class HaloController {

    @GetMapping("/halo/{name}")
    public Mono<String> halo(@PathVariable String name) {
        return Mono.just("halo " + name);
    }

    @GetMapping("/halo/empty")
    public Mono<String> empty() {
        return Mono.empty();
    }

    @GetMapping("/halo/flux")
    public Flux<String> flux() {
        return Flux.just("123","321");
    }

    @GetMapping("/halo/map")
    public Flux<Map<String,Object>> map() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("k1", "v1");
        map1.put("k2", "v2");
        map1.put("k3", "v3");
        return Flux.just(map1);
    }
}
