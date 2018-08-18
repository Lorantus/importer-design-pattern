package com.laurent.importer;

import com.laurent.importer.api.DataSource;
import com.laurent.importer.api.LecteurImport;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.function.BiFunction;

public class LecteurImportReponses extends LecteurImport<QuestionReponseImport> {
    private static final int QUESTION_INDEX = 1;
    private static final int REPONSE_INDEX = 2;
    private static final int COMMENTAIRE_INDEX = 3;

    public LecteurImportReponses(DataSource datas) {
        super(datas);
    }

    protected BiFunction<String[], Integer, QuestionReponseImport> importData() {
        return (data, ligne) -> new QuestionReponseImport(
                ligne,
                data[QUESTION_INDEX],
                data[REPONSE_INDEX],
                data[COMMENTAIRE_INDEX]);
    }
}
