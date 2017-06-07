package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
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

    public HealthCheckSets(long setId, String name) {
        this.setId = setId;
        this.name = name;
    }

    public String name;

    public long setId;

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

    public static HealthCheckSets findBySetId(long setId) {
        return healthCheckSets().findOne("{setId: #}", setId).as(HealthCheckSets
                .class);
    }

    public static void updateHealthCheckSetId(long oldId, long newId) {
        healthCheckSets().update("{setId: #}", oldId).with("{$set:{setId: #}}", newId);
    }

    @Override
    public String toString() {
        return "HealthCheckSets setId: " + this.setId + ", name: " + this.name;
    }
}
