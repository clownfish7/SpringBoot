//package com.clownfish7.springbootwebflux.dao;
//
//import com.clownfish7.springbootwebflux.pojo.User;
//import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//import reactor.core.publisher.Flux;
//
///**
// * @author yzy
// * @classname UserRepository
// * @description TODO
// * @create 2019-12-02 11:21 AM
// */
//public interface UserRepository extends ReactiveMongoRepository<User,Long> {
//    public Flux<User> findByUserNameLikeAndNoteLike(String userName, String note);
//}
