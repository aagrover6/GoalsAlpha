package edu.calpoly.aagrover.goal;

/**
 * Created by ashleygrover on 5/13/16.
 */
public class Goal {

    /* Contains the text of this goal. */
    private String strGoal;

    /* Contains the date this goal is to be completed by. */
    private String strDate;
    private String curDate;

    /* Contains the unique ID of this goal. */
    private long goalID;

    /**
     * Initializes a goal with the text of the goal, the date to be completed by,
     * and the goal ID.
     *
     * @param strGoal - text of the goal.
     * @param strDate - date to be completed by.
     */
    public Goal(String strGoal, String strDate, String currentDate) {
        this.strGoal = strGoal;
        this.strDate = strDate;
        this.curDate = currentDate;
        this.goalID = 0;
    }

    /**
     * Initializes a goal with the text of the goal, the date to be completed by,
     * and the goal ID.
     *
     * @param strGoal - text of the goal.
     * @param strDate - date to be completed by.
     * @param goalID - unique ID for this goal.
     */
    public Goal(String strGoal, String strDate, String currentDate, long goalID) {
        this.strGoal = strGoal;
        this.strDate = strDate;
        this.curDate = currentDate;
        this.goalID = goalID;
    }

    /**
     * Accessor for the ext of this joke.
     *
     * @return A String value containing the text of this goal.
     */
    public String getGoal() {
        return this.strGoal;
    }

    /**
     * Mutator that changes the text of this goal.
     *
     * @param strGoal - the text of this goal.
     */
    public void setGoal(String strGoal) {
        this.strGoal = strGoal;
    }

    /**
     * Accessor for the date of this goal.
     *
     * @return the date to be completed by.
     */
    public String getDate() {
        return this.strDate;
    }

    /**
     * Mutator that changes the date of the goal.
     *
     * @param strDate - the date to be changed to.
     */
    public void setDate(String strDate) {
        this.strDate = strDate;
    }

    public String getCurDate() {
        return this.curDate;
    }
    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    /**
     * Accessor for the unique ID of this goal.
     *
     * @return the unique id of the goals.
     */
    public long getID() {
        return this.goalID;
    }

    /**
     * Mutator that changes the unique id of this goal.
     *
     * @param id - the unique id for this goal.
     */
    public void setID(long id) {
        this.goalID = id;
    }

    /**
     * Returns on the text of the goal. Mimics getGoal().
     *
     * @return A string containing the text of the goal.
     */
    @Override
    public String toString() {
        return this.strGoal;
    }

}
