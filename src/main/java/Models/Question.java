/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Prabhat
 */
public class Question {
    public String problemStatement;
    public String problemDescription;
    public String testCase;
    public String maxScore;
    public String userScore;
    public String puzzleStatement;
    public String puzzleDescription;
    public String answer;
    public String status;
    public String teamName;
    public String questionType;
    public String questionInputFormat;
    public String questionOutputFormat;
    public String example;
    public String expectedOp;

    public String getExpectedOp() {
        return expectedOp;
    }

    public void setExpectedOp(String expectedOp) {
        this.expectedOp = expectedOp;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getQuestionInputFormat() {
        return questionInputFormat;
    }

    public void setQuestionInputFormat(String questionInputFormat) {
        this.questionInputFormat = questionInputFormat;
    }

    public String getQuestionOutputFormat() {
        return questionOutputFormat;
    }

    public void setQuestionOutputFormat(String questionOutputFormat) {
        this.questionOutputFormat = questionOutputFormat;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPuzzleStatement() {
        return puzzleStatement;
    }

    public void setPuzzleStatement(String puzzleStatement) {
        this.puzzleStatement = puzzleStatement;
    }

    public String getPuzzleDescription() {
        return puzzleDescription;
    }

    public void setPuzzleDescription(String puzzleDescription) {
        this.puzzleDescription = puzzleDescription;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }
}
