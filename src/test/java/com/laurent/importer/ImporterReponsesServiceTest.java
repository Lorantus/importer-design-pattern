package com.laurent.importer;

import com.laurent.importer.api.DataSource;
import com.laurent.importer.domain.Choix;
import com.laurent.importer.domain.Question;
import com.laurent.importer.domain.Questionnaire;
import com.laurent.importer.domain.Reponse;
import org.junit.Test;

import java.util.*;

import static com.laurent.importer.model.TypeQuestion.QCU;
import static com.laurent.importer.model.TypeQuestion.TEXTE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImporterReponsesServiceTest {

    @Test
    public void doitImporterLesReponses() {
        // GIVEN
        ImporterReponsesService importerReponsesService = new ImporterReponsesService();

        DataSource datas = new RowDataSource(
                data(new String[][]{
                        {"Question 1", "Réponse 1", "Commentaire 1"},
                        {" Question 2 ", "Non concerné", "Commentaire 2"},
                        {"Question 3", "Oui", "Commentaire 3"}
                })
        );

        Map<UUID, Question> questions = new HashMap<>();
        Question question1 = new Question(UUID.randomUUID(), TEXTE, "Question 1");
        Question question2 = new Question(UUID.randomUUID(), TEXTE, "Question 2");
        Question question3 = new Question(UUID.randomUUID(), QCU, "  Question 3");
        questions.put(question1.getId(), question1);
        questions.put(question2.getId(), question2);
        questions.put(question3.getId(), question3);

        Choix choixOui = new Choix(UUID.randomUUID(), "Oui");
        Choix choixNon = new Choix(UUID.randomUUID(), "Non");
        List<Choix> choixes = asList(choixOui, choixNon);
        question3.setChoixes(choixes);

        Map<UUID, Reponse> reponses = new HashMap<>();
        Reponse reponse = new Reponse(UUID.randomUUID(), question1.getId());
        reponses.put(question1.getId(), reponse);

        Questionnaire questionnaire = new Questionnaire("node-id", questions, reponses);

        // WHEN
        importerReponsesService.execute(datas, questionnaire);

        // THEN
        assertThat(importerReponsesService.hasError()).isFalse();

        assertThat(questionnaire.getReponse(question1).get())
                .extracting(Reponse::isNonConcerne, Reponse::getReponse, Reponse::getCommentaire)
                .containsOnly(false, "Réponse 1", "Commentaire 1");

        assertThat(questionnaire.getReponse(question2).get())
                .extracting(Reponse::isNonConcerne)
                .containsOnly(true);

        assertThat(questionnaire.getReponse(question3).get())
                .extracting(Reponse::isNonConcerne, Reponse::getReponse, Reponse::getCommentaire)
                .containsOnly(false, choixOui.getId().toString(), "Commentaire 3");
    }

    @Test
    public void doitDeclencherUneExceptionSiLaReponseNEstPasValidee() {
        // GIVEN
        ImporterReponsesService importerReponsesService = new ImporterReponsesService();

        DataSource datas = new RowDataSource(
                data(new String[][]{
                        {"Question 1", "Non Valide", "Commentaire 3"}
                })
        );

        Map<UUID, Question> questions = new HashMap<>();
        Question question = new Question(UUID.randomUUID(), QCU, "Question 1");
        questions.put(question.getId(), question);

        Choix choixOui = new Choix(UUID.randomUUID(), "Oui");
        Choix choixNon = new Choix(UUID.randomUUID(), "Non");
        List<Choix> choixes = asList(choixOui, choixNon);
        question.setChoixes(choixes);

        Map<UUID, Reponse> reponses = new HashMap<>();
        Reponse reponse = new Reponse(UUID.randomUUID(), question.getId());
        reponses.put(question.getId(), reponse);

        Questionnaire questionnaire = new Questionnaire("node-id", questions, reponses);

        // EXPECT
        assertThatThrownBy(() -> importerReponsesService.execute(datas, questionnaire))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2, \"Question 1\", Cette réponse n'est pas valide.");
    }

    @Test
    public void doitDeclencherUneExceptionSiLaQuestionNExistePas() {
        // GIVEN
        ImporterReponsesService importerReponsesService = new ImporterReponsesService();

        DataSource datas = new RowDataSource(
                data(new String[][]{
                        {"Question 1", "Réponse 1", "Commentaire 1"}
                })
        );

        Questionnaire questionnaire = new Questionnaire("node-id", emptyMap(), emptyMap());

        // EXPECT
        assertThatThrownBy(() -> importerReponsesService.execute(datas, questionnaire))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2, \"Question 1\", Cette question n'existe pas.");
    }

    @Test
    public void doitDeclencherUneExceptionSiDeuxReponsesDifferentes() {
        // GIVEN
        ImporterReponsesService importerReponsesService = new ImporterReponsesService();

        DataSource datas = new RowDataSource(
                data(new String[][]{
                        {"Question 1", "Réponse 1", "Commentaire 1"},
                        {"Question 1", "Non concerné", "Commentaire 2"},
                        {"Question 1", "Réponse 3", "Commentaire 2"}
                })
        );

        Map<UUID, Question> questions = new HashMap<>();
        Question question1 = new Question(UUID.randomUUID(), TEXTE, "Question 1");
        questions.put(question1.getId(), question1);

        Questionnaire questionnaire = new Questionnaire("node-id", questions, new HashMap<>());

        // EXPECT
        assertThatThrownBy(() -> importerReponsesService.execute(datas, questionnaire))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("3, \"Question 1\", Cette question est déjà répondue avec une valeur différente ligne 2\n" +
                        "4, \"Question 1\", Cette question est déjà répondue avec une valeur différente ligne 2");
    }


    private List<String[]> data(String[][] strings) {
        return Arrays.stream(strings)
                .collect(toList());
    }
}