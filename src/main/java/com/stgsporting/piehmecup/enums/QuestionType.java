package com.stgsporting.piehmecup.enums;

public enum QuestionType {
    Choice,
    MultipleCorrectChoices, // 6
    Reorder,
    Written;

    public static QuestionType fromId(int type) {
        return switch (type) {
            case 1, 6 -> Choice;
            case 2 -> Written;
            case 3 -> Reorder;
            default -> null;
        };
    }

    public static QuestionType fromName(String type) {
        return switch (type) {
            case "Choice" -> Choice;
            case "Written" -> Written;
            case "Reorder" -> Reorder;
            case "MultipleCorrectChoices" -> MultipleCorrectChoices;
            default -> null;
        };
    }

    public int toId() {
        return switch (this) {
            case Choice -> 1;
            case MultipleCorrectChoices -> 6;
            case Written -> 2;
            case Reorder -> 3;
        };
    }
}
