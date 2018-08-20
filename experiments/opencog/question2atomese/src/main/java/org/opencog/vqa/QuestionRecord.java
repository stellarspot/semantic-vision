package org.opencog.vqa;

import lombok.Builder;
import lombok.ToString;
import org.opencog.vqa.relex.RelexFormula;

/**
 * Record class keeps information about one question and able to serialize/deserialize it as 
 * delimited list of fields. It is as simple as POJO with builder and serialization logic.
 */
@ToString
@Builder(toBuilder = true)
class QuestionRecord {

    private static final String FIELD_DELIMITER = "::";

    private final String questionId;
    private final String questionType;
    private final String question;
    private final String imageId;
    private final String answer;
    private final String shortFormula;
    private final String fullFormula;
    private final String formulaInvalidKeys;

    public String getQuestionType() {
        return questionType;
    }

    public String getQuestion() {
        return question;
    }
    
    public String getAnswer() {
        return answer;
    }

    public static QuestionRecord load(String string) {
        String[] fields = string.split(FIELD_DELIMITER);

        boolean isValid = isFormulaValid(fields);

        QuestionRecordBuilder builder = QuestionRecord.builder();
        
        builder
            .questionId(fields[0])
            .questionType(fields[1])
            .question(fields[2])
            .imageId(fields[3])
            .answer(fields[4])
            .shortFormula(isValid && fields.length > 5 ? fields[5] : "")
            .fullFormula(isValid && fields.length > 6 ? fields[6] : "")
            .formulaInvalidKeys(isValid ? "" : fields[5]);
            
        return builder.build();
    }

    public String save()
    {

        return questionId
                + FIELD_DELIMITER + questionType
                + FIELD_DELIMITER + question
                + FIELD_DELIMITER + imageId
                + FIELD_DELIMITER + answer
                + (formulaInvalidKeys.isEmpty()
                        ? FIELD_DELIMITER + shortFormula
                        + FIELD_DELIMITER + fullFormula
                        : FIELD_DELIMITER + formulaInvalidKeys);
    }

    private static boolean isFormulaValid(String[] fields) {

        return fields.length <= 5 ||
                !fields[5].contains(RelexFormula.INVALID_KEY_SKIPPED)
                        && !fields[5].contains(RelexFormula.INVALID_KEY_VIOLATION);
    }

}
