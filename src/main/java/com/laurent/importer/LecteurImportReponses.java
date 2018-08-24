package com.laurent.importer;

import com.laurent.importer.api.DataSource;
import com.laurent.importer.api.LecteurImport;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;
import java.util.function.BiFunction;

public class LecteurImportReponses extends LecteurImport<QuestionReponseImport> {
    private static final String NON_APPLICABLE_REPONSE = "/";
    private static final int QUESTION_INDEX = 0;
    private static final int REPONSE_INDEX = 1;
    private static final int COMMENTAIRE_INDEX = 2;

    private LecteurImportReponses(DataSource datas) {
        super(datas);
    }

    public static LecteurImportReponses createLecteur(DataSource datas) {
        return new LecteurImportReponses(datas);
    }

    private boolean isRowEmpty(String[] data) {
        return (data[REPONSE_INDEX].trim().isEmpty() && data[COMMENTAIRE_INDEX].trim().isEmpty())
                || data[REPONSE_INDEX].equals(NON_APPLICABLE_REPONSE);
    }

    @Override
    protected BiFunction<String[], Integer, Optional<QuestionReponseImport>> importData() {
        return (data, ligne) ->
                isRowEmpty(data) ?
                        Optional.empty() :
                        Optional.of(new QuestionReponseImport(
                                ligne + 1,
                                data[QUESTION_INDEX].trim(),
                                data[REPONSE_INDEX],
                                data[COMMENTAIRE_INDEX])
                        );
    }
}
