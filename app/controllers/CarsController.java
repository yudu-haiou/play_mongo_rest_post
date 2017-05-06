package controllers;

import models.Car;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by friend on 5/4/17.
 */
@Singleton
public class CarsController extends Controller {

    private HttpExecutionContext ec;

    @Inject
    public CarsController(HttpExecutionContext ec) {
        this.ec = ec;
    }

    public Result save() {
        Car car = new Car("test");
        return ok(car.insert().toString());
    }

    public CompletionStage<Result> saveAsync() {
        return CompletableFuture.supplyAsync(() -> {
            Car car = new Car("testAsync");
            return car.insert().toString();
        }, ec.current()).thenApply(i -> ok("Got result: " + i));
    }

}
