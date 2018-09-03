package com.laurent.importer.command.question;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.domain.Choix;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.laurent.importer.model.TypeQuestion.QCU;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeQCMValidatorLigneTest {
    private TypeQCMValidatorLigne validator;
    private List<ErrorMessage> errors;
    private Question question;
    private Choix choix1;
    private Choix choix2;

    @Before
    public void setUp() {
        errors = new ArrayList<>();

        validator = new TypeQCMValidatorLigne();
        validator.onError(errors::add);

        question = new Question(UUID.randomUUID(), QCU, "Question");
        choix1 = new Choix(UUID.randomUUID(), "Choix 1");
        choix2 = new Choix(UUID.randomUUID(), "Choix 2");
        List<Choix> choixes = asList(choix1, choix2);
        question.setChoixes(choixes);
    }

    @Test
    public void doitValiderUnChoixUnique() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Choix 1", "Commentaire");

        // WHEN
        validator.validator(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
    }

    @Test
    public void doitAutoriserUnChoixMalgreLaCasse() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "CHOIX 1", "Commentaire");

        // WHEN
        Validator<String, ErrorMessage> validator = this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(validator.get()).get().isEqualTo(choix1.getId().toString());
    }

    @Test
    public void doitAutoriserPlusieursChoix() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Choix 1\nChoix 2", "Commentaire");

        // WHEN
        Validator<String, ErrorMessage> validator = this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(validator.get()).get().isEqualTo(choix1.getId().toString() + "," + choix2.getId().toString());
    }

    @Test
    public void neDoitPasValiderUnChoixQuiNEstPasAutorise() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Peut-être", "Commentaire");

        // WHEN
        this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).extracting(Objects::toString).containsOnly("1, \"Question\", Cette réponse n'est pas valide.");
    }
}