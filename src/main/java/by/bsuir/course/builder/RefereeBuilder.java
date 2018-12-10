package by.bsuir.course.builder;

import by.bsuir.course.entities.Referee;

public abstract class RefereeBuilder {
    protected Referee referee;

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public abstract void buildLogin();
    public abstract void buildPassword();
    public abstract void buildSport();
    public abstract void buildName();
    public abstract void buildSurname();
    public abstract void buildAge();
    public abstract void buildCountry();
    public abstract void buildCity();
}
