package com.stgsporting.piehmecup.enums;

public enum QuestionType {
    Choice,
    Reorder,
    Written;

    public static QuestionType fromId(int type) {
        return switch (type) {
            case 1 -> Choice;
            case 2 -> Reorder;
            case 3 -> Written;
            default -> null;
        };
    }
}
