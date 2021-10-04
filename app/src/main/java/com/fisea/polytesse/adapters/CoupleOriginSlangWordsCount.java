package com.fisea.polytesse.adapters;

public class CoupleOriginSlangWordsCount {
    private final String origin;
    private final int slangWordsCount;

    public CoupleOriginSlangWordsCount(String origin, int slangWordsCount) {
        this.origin = origin;
        this.slangWordsCount = slangWordsCount;
    }

    public String getOrigin() {
        return origin;
    }

    public int getSlangWordsCount() {
        return slangWordsCount;
    }
}
