package post;

import models.Hospital;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Created by friend on 5/5/17.
 */
public interface HospitalRepository {

    CompletionStage<Hospital> create(Hospital hospital);

    CompletionStage<Optional<Hospital>> get(Long id);
}
