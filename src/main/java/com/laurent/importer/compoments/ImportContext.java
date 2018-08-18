package com.laurent.importer.compoments;

import com.laurent.importer.ImportQuestionErrorMessage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ImportContext {
    private List<ImportQuestionErrorMessage> importQuestionErrorMessages = new ArrayList<>();

    public void addErrorMessage(ImportQuestionErrorMessage importQuestionErrorMessage) {
        importQuestionErrorMessages.add(importQuestionErrorMessage);
    }
}
