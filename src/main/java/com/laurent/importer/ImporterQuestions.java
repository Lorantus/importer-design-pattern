package com.laurent.importer;

import com.laurent.importer.api.DataSource;
import com.laurent.importer.command.ImporterQuestionsCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;

import java.util.List;

public class ImporterQuestions {
    private final ImportContext importContext;

    public ImporterQuestions(ImportContext importContext) {
        this.importContext = importContext;
    }

    public void importer(DataSource datas, List<QuestionReponse> questionReponses) {
        ImporterQuestionsCommand importerQuestionsCommand = ImporterQuestionsCommand.createWithContext(importContext);
        new LecteurImportReponses(datas).parse()
                .forEach(ligneImport -> importerQuestionsCommand.execute(ligneImport, questionReponses));
    }
}
