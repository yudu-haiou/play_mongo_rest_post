package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by friend on 5/26/17.
 */
public class HealthCheckSets {
    public static PlayJongo playJongo = Play.current().injector().instanceOf(PlayJongo.class);

    public static MongoCollection healthCheckSets() {
        return playJongo.getCollection("healthchecksets");
    }

    public HealthCheckSets() {}

    public HealthCheckSets(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("_id")
    public long id;

    public String name;

    public HealthCheckSets insert() {
        healthCheckSets().save(this);
        return this;
    }

    public void remove() {
        healthCheckSets().remove(this.name);
    }

    public static HealthCheckSets findByName(String name) {
        return healthCheckSets().findOne("{name: #}", name).as(HealthCheckSets
                .class);
    }

    public static HealthCheckSets findById(long id) {
        return healthCheckSets().findOne("{_id: #}", id).as(HealthCheckSets
                .class);
    }

    @Override
    public String toString() {
        return "HealthCheckSets id: " + this.id + ", name: " + this.name;
    }
}
