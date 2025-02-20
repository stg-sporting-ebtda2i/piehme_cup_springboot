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
}
