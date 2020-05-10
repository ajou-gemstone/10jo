package com.example.capstonedesignandroid.DTO;

public class DummyReservationDetailGuard {

    String leaderId;
    String score;
    String scoreReason;
    String guardId;

    public DummyReservationDetailGuard(String leaderId, String score, String scoreReason, String guardId) {
        this.leaderId = leaderId;
        this.score = score;
        this.scoreReason = scoreReason;
        this.guardId = guardId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public String getScore() {
        return score;
    }

    public String getScoreReason() {
        return scoreReason;
    }

    public String getGuardId() {
        return guardId;
    }
}
