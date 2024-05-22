package com.sbm.mc.reviewprohandler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;

public class SurveyAnswer implements Serializable {

    private String id;
    private String question;
    private String label;
    private String answer;
    private int outOf;

    @JsonIgnore
    private String linked;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SurveyAnswer that = (SurveyAnswer) object;
        return (
            outOf == that.outOf &&
            Objects.equals(id, that.id) &&
            Objects.equals(question, that.question) &&
            Objects.equals(label, that.label) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(linked, that.linked)
        );
    }

    @Override
    public String toString() {
        return (
            "SurveyAnswer{" +
            "id='" +
            id +
            '\'' +
            ", question='" +
            question +
            '\'' +
            ", label='" +
            label +
            '\'' +
            ", answer='" +
            answer +
            '\'' +
            ", outOf=" +
            outOf +
            ", linked='" +
            linked +
            '\'' +
            '}'
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, label, answer, outOf, linked);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getOutOf() {
        return outOf;
    }

    public void setOutOf(int outOf) {
        this.outOf = outOf;
    }

    public String getLinked() {
        return linked;
    }

    public void setLinked(String linked) {
        this.linked = linked;
    }

    public SurveyAnswer(String id, String question, String label, String answer, int outOf, String linked) {
        this.id = id;
        this.question = question;
        this.label = label;
        this.answer = answer;
        this.outOf = outOf;
        this.linked = linked;
    }
}
