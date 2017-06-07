package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.HealthCheckSetItems;
import models.HealthCheckSets;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import util.OldNewIds;

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
        return CompletableFuture.supplyAsync(() -> HealthCheckSets.findBySetId(id),
                ec.current()).thenApply(i -> ok("Got result: " + i));
    }

    public CompletionStage<Result> updateHealthCheckSetsId() {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode json = request().body().asJson();
            final OldNewIds ids = Json.fromJson(json, OldNewIds.class);
            HealthCheckSets.updateHealthCheckSetId(ids.getOldId(), ids.getNewId());
            HealthCheckSetItems.updateHealthCheckItemSetIds(ids.getOldId(), ids.getNewId());
            return HealthCheckSetItems.findBySetsId(ids.getNewId());
        }, ec.current()).thenApply(i -> ok("Got result: " + i));
    }
}

