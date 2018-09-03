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

public class TypeQCUValidatorLigneTest {
    private TypeQCUValidatorLigne validator;
    private List<ErrorMessage> errors;
    private Question question;
    private Choix choixOui;
    private Choix choixNon;

    @Before
    public void setUp() {
        errors = new ArrayList<>();

        validator = new TypeQCUValidatorLigne();
        validator.onError(errors::add);

        question = new Question(UUID.randomUUID(), QCU, "Question");
        choixOui = new Choix(UUID.randomUUID(), "Oui");
        choixNon = new Choix(UUID.randomUUID(), "Non");
        List<Choix> choixes = asList(choixOui, choixNon);
        question.setChoixes(choixes);
    }

    @Test
    public void doitValiderUnChoixUnique() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Oui", "Commentaire");

        // WHEN
        Validator<String, ErrorMessage> validator = this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(validator.get()).get().isEqualTo(choixOui.getId().toString());
    }

    @Test
    public void doitAutoriserUnChoixMalgreLaCasse() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "OUI", "Commentaire");

        // WHEN
        Validator<String, ErrorMessage> validator = this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(validator.get()).get().isEqualTo(choixOui.getId().toString());
    }

    @Test
    public void neDoitPasAutoriserPlusieursChoix() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Oui\nNon", "Commentaire");

        // WHEN
        this.validator.validator(ligne, question);

        // THEN
        assertThat(errors).extracting(Objects::toString).containsOnly("1, \"Question\", Cette réponse n'est pas valide.");
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