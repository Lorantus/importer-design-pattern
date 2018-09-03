package com.laurent.importer.command;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.domain.Choix;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;
import com.laurent.importer.model.ReponseValidee;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.laurent.importer.model.TypeQuestion.QCU;
import static com.laurent.importer.model.TypeQuestion.TEXTE;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ReponseValideeLigneTest {
    private ReponseValideeLigne validator;
    private List<ErrorMessage> errors;
    private Question question;
    private Choix choix1;
    private Choix choix2;

    @Before
    public void setUp() {
        errors = new ArrayList<>();

        validator = new ReponseValideeLigne(errors::add);

        question = new Question(UUID.randomUUID(), TEXTE, "Question");
    }

    @Test
    public void doitRetournerUneReponseNonConcernee() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Non concerné", "Commentaire");

        // WHEN
        Optional<ReponseValidee> execute = validator.execute(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(execute.get()).extracting(ReponseValidee::isNonConcernee).containsOnly(true);
    }

    @Test
    public void doitRetournerUneReponseNonConcerneeMalgreLaCasse() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Non Concerné", "Commentaire");

        // WHEN
        Optional<ReponseValidee> execute = validator.execute(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(execute.get()).extracting(ReponseValidee::isNonConcernee).containsOnly(true);
    }

    @Test
    public void doitRetournerUneReponseConcernee() {
        // GIVEN
        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Réponse", "Commentaire");

        // WHEN
        Optional<ReponseValidee> execute = validator.execute(ligne, question);

        // THEN
        assertThat(errors).isEmpty();
        assertThat(execute.get()).extracting(ReponseValidee::isNonConcernee).containsOnly(false);
    }

    @Test
    public void neDoitPasRetournerUneReponseNonConcerneePourUneQuestionQuiNeLAutorisePas() {
        // GIVEN
        question = new Question(UUID.randomUUID(), QCU, "Question");
        question.setChoixes(singletonList(new Choix(UUID.randomUUID(), "Choix 1")));

        QuestionReponseImport ligne = new QuestionReponseImport(1, "Question", "Non Concerné", "Commentaire");

        // WHEN
        validator.execute(ligne, question);

        // THEN
        assertThat(errors).extracting(Objects::toString).containsOnly("1, \"Question\", Cette réponse n'est pas valide.");
    }
}