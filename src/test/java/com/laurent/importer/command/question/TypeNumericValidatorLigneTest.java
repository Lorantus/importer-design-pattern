package com.laurent.importer.command.question;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.laurent.importer.model.TypeQuestion.NUMERIC;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeNumericValidatorLigneTest {
    private TypeNumericValidatorLigne validator;
    private List<ErrorMessage> errors;
    private Question question;

    @Before
    public void setUp() {
        errors = new ArrayList<>();

        validator = new TypeNumericValidatorLigne();
        validator.onError(errors::add);

        question = new Question(UUID.randomUUID(), NUMERIC, "Question");
    }

    @Test
    public void doitValiderUneValeurNumerique() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "1", "Commentaire");

        // WHEN
        Validator<String, ErrorMessage> validator = this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(validator.get()).get().isEqualTo("1");
    }

    @Test
    public void neDoitPasValiderUneValeurNonNumerique() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "a", "Commentaire");

        // WHEN
        this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).extracting(Objects::toString).containsOnly("1, \"Question\", Cette question nécessite un entier en réponse.");
    }
}