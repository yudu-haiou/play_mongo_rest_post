package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.HealthCheckSets;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by friend on 5/27/17.
 */
public class HealthCheckSetsController extends Controller {

    private HttpExecutionContext ec;

    @Inject
    public HealthCheckSetsController(HttpExecutionContext ec) {
        this.ec =ec;
    }

    public CompletionStage<Result> saveHealthCheckSets() {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode json = request().body().asJson();
            final HealthCheckSets healthCheckSets = Json.fromJson(json, HealthCheckSets.class);
            return healthCheckSets.insert().toString();
        }, ec.current()).thenApply(i -> ok("Got result: " + i));
    }

    public CompletionStage<Result> getHealthCheckSetsById(Long id) {
        return CompletableFuture.supplyAsync(() -> HealthCheckSets.findById(id),
                ec.current()).thenApply(i -> ok("Got result: " + i));
    }
}

