package com.laurent.importer.command;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.command.question.NoCheckReponseCommand;
import com.laurent.importer.command.question.TypeNumericValidatorCommand;
import com.laurent.importer.command.question.TypeQCMValidatorCommand;
import com.laurent.importer.command.question.TypeQCUValidatorCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;
import com.laurent.importer.model.TypeQuestion;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import static com.laurent.importer.model.TypeQuestion.*;

public class TypeQuestionValidatorCommand extends ImporterQuestionCommand {
    private static final HashMap<TypeQuestion, Function<ImportContext, ImporterQuestionCommand>> MAPPER =
            new HashMap<TypeQuestion, Function<ImportContext, ImporterQuestionCommand>>() {{
                put(BINAIRE, TypeQCUValidatorCommand::new);
                put(NUMERIC, TypeNumericValidatorCommand::new);
                put(QCM, TypeQCMValidatorCommand::new);
                put(QCU, TypeQCUValidatorCommand::new);
                put(TEXTE, NoCheckReponseCommand::new);
            }};

    public TypeQuestionValidatorCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        return MAPPER.get(question.getTypeQuestion())
                .apply(getContext())
                .execute(ligneImport, question);
    }
}
