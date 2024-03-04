package Entities;

public class HorseActivity {
    private int horseId;
    private int activityId;

    public HorseActivity(int horseId, int activityId) {
        this.horseId = horseId;
        this.activityId = activityId;
    }

    // Getters and setters
    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}
