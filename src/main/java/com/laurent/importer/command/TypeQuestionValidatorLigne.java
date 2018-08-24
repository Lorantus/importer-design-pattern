package com.laurent.importer.command;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.error.ImportError;
import com.laurent.importer.command.question.NoCheckReponseLigne;
import com.laurent.importer.command.question.TypeNumericValidatorLigne;
import com.laurent.importer.command.question.TypeQCMValidatorLigne;
import com.laurent.importer.command.question.TypeQCUValidatorLigne;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;
import com.laurent.importer.model.TypeQuestion;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.laurent.importer.model.TypeQuestion.*;

public class TypeQuestionValidatorLigne extends ImportError {
    private static final HashMap<TypeQuestion, Supplier<ImporterLigne>> MAPPER =
            new HashMap<TypeQuestion, Supplier<ImporterLigne>>() {{
                put(BINAIRE, TypeQCUValidatorLigne::new);
                put(QCU, TypeQCUValidatorLigne::new);
                put(QCM, TypeQCMValidatorLigne::new);
                put(NUMERIC, TypeNumericValidatorLigne::new);
                put(TEXTE, NoCheckReponseLigne::new);
            }};

    private Consumer<ErrorMessage> errorConsumer;

    public TypeQuestionValidatorLigne onError(Consumer<ErrorMessage> consumer) {
        this.errorConsumer = consumer;
        return this;
    }

    public Optional<String> validator(QuestionReponseImport ligneImport, Question question) {
        return MAPPER.get(question.getTypeQuestion())
                .get()
                .onError(errorConsumer)
                .validator(ligneImport, question)
                .get();
    }
}
