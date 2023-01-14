package com.example.springbootreactor3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Exceptions;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootTest
class SpringbootReactor3ApplicationTests {

    static void sp() {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println();
    }

    @Test
    void contextLoads() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
//                .elapsed()
                .subscribe(i -> {
                    if(i == 5){
                        throw new RuntimeException("eeee");
                    }
                    System.out.println("i=" + i);
                });
        Thread.currentThread().join();
    }

    @Test
    void createFluxMono() {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.from(seq1);

        Mono<Object> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");
        Flux<Integer> numbersFromFive2Seven = Flux.range(5, 3);
    }

    @Test
    void subscribe() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();

        sp();

        ints.subscribe(System.out::println);

        sp();

        Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                })
                .subscribe(System.out::println,
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Done"));

    }

    @Test
    void test4_4_1_generate() {
        Flux.generate(new Consumer<SynchronousSink<Integer>>() {
            @Override
            public void accept(SynchronousSink<Integer> synchronousSink) {
            }
        });

        Flux.generate(() -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                }).subscribe(System.out::println);

        sp();

        Flux.generate(AtomicInteger::new,
                (atomic, sink) -> {
                    sink.next("3 x " + atomic.get() + " = " + 3 * atomic.get());
                    if (atomic.get() == 10) sink.complete();
                    atomic.incrementAndGet();
                    return atomic;
                },
                state -> {
                    System.out.println("state: " + state);
                }).subscribe(System.out::println);
    }

    @Test
    void test4_4_2_create() {
        Flux.create(sink -> {
            sink.onRequest(null)
                    .onCancel(null)
                    .onDispose(null);
        });
    }

    @Test
    void test4_4_2_handle() {
        Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
                .handle((i, sink) -> {
                    String letter = alphabet(i);
                    if (letter != null)
                        sink.next(letter);
                });

        alphabet.subscribe(System.out::println);
    }

    public static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

    @Test
    void test4_6_publistOn_subscribeOn() {
        // current thread
        Schedulers.immediate();
        // 可重用的单线程
        Schedulers.single();
        Schedulers.newSingle("");
        // 弹性线程池
        Schedulers.boundedElastic();
//        Schedulers.elastic();
        // 固定大小 = cpuCount  min=4
        Schedulers.parallel();

        Flux.range(0, 10000)
                .publishOn(Schedulers.single())
                .subscribe(n -> System.out.println(Thread.currentThread().getName() + ":" + n));
    }

    @Test
    void test4_7_error() {
        Flux<String> converted = Flux
                .range(1, 10)
                .map(i -> {
                    try {
                        return convert(i);
                    } catch (IOException e) {
                        throw Exceptions.propagate(e);
                    }
                });

        converted.subscribe(
                v -> System.out.println("RECEIVED: " + v),
                e -> {
                    if (Exceptions.unwrap(e) instanceof IOException) {
                        System.out.println("Something bad happened with I/O");
                    } else {
                        System.out.println("Something bad happened");
                    }
                }
        );
    }

    public String convert(int i) throws IOException {
        if (i > 3) {
            throw new IOException("boom " + i);
        }
        return "OK " + i;
    }

    @Test
    void test8_1_1_transform() {
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: " + d));
    }

    @Test
    void test8_1_2_compose() {
        AtomicInteger ai = new AtomicInteger();
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> {
                    if (ai.incrementAndGet() == 1) {
                        return f.filter(color -> !color.equals("orange"))
                                .map(String::toUpperCase);
                    }
                    return f.filter(color -> !color.equals("purple"))
                            .map(String::toUpperCase);
                };

        Flux<String> composeFlux = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .transformDeferred(filterAndMap)
                /*.compose(filterAndMap)*/;

        composeFlux.subscribe(d -> System.out.println("Subscriber 1 to Transformed MapAndFilter: " + d));
        sp();
        composeFlux.subscribe(d -> System.out.println("Subscriber 2 to Transformed MapAndFilter: " + d));
    }

    @Test
    void test8_3_connect() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("subscribed to source"));

        ConnectableFlux<Integer> co = source.publish();

        co.subscribe(System.out::println, e -> {}, () -> {});
        co.subscribe(System.out::println, e -> {}, () -> {});

        System.out.println("done subscribing");
        Thread.sleep(500);
        System.out.println("will now connect");

        co.connect();

        sp();

         source = Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("subscribed to source"));

        Flux<Integer> autoCo = source.publish().autoConnect(2);

        autoCo.subscribe(System.out::println, null, null);
        System.out.println("subscribed first");
        Thread.sleep(500);
        System.out.println("subscribing second");
        autoCo.subscribe(System.out::println, null, null);
    }

    @Test
    void test8_4_1_groupBy() throws InterruptedException {
        StepVerifier.create(
                        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                                .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                                .concatMap(g -> g.defaultIfEmpty(-1) // 如果组为空，显示为 -1
                                        .map(String::valueOf) // 转换为字符串
                                        .startWith(g.key()) // 以该组的 key 开头
                                )
                )
                .expectNext("odd", "1", "3", "5", "11", "13")
                .expectNext("even", "2", "4", "6", "12")
                .verifyComplete();
    }

    @Test
    void test8_4_2_window() throws InterruptedException {
        StepVerifier.create(
                        Flux.range(1, 10)
                                .window(5, 3) //overlapping windows
                                .concatMap(g -> g.defaultIfEmpty(-1)) //将 windows 显示为 -1
                )
                .expectNext(1, 2, 3, 4, 5)
                .expectNext(4, 5, 6, 7, 8)
                .expectNext(7, 8, 9, 10)
                .expectNext(10)
                .verifyComplete();

        StepVerifier.create(
                        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                                .log()
                                .windowWhile(i -> i % 2 == 0)
                                .concatMap(g -> g.defaultIfEmpty(-1))
                )
                .expectNext(-1, -1, -1) //分别被奇数 1 3 5 触发
                .expectNext(2, 4, 6) // 被 11 触发
                .expectNext(12) // 被 13 触发
                .expectNext(-1) // 空的 completion window，如果 onComplete 前的元素能够匹配上的话就没有这个了
                .verifyComplete();

    }

    @Test
    void test8_4_3_buffer() throws InterruptedException {
        StepVerifier.create(
                        Flux.range(1, 10)
                                .buffer(5, 3) // 缓存重叠
                )
                .expectNext(Arrays.asList(1, 2, 3, 4, 5))
                .expectNext(Arrays.asList(4, 5, 6, 7, 8))
                .expectNext(Arrays.asList(7, 8, 9, 10))
                .expectNext(Collections.singletonList(10))
                .verifyComplete();

        StepVerifier.create(
                        Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                                .bufferWhile(i -> i % 2 == 0)
                )
                .expectNext(Arrays.asList(2, 4, 6)) // 被 11 触发
                .expectNext(Collections.singletonList(12)) // 被 13 触发
                .verifyComplete();

    }
}
