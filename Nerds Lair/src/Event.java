public abstract class Event {
    protected String description;

    public Event() {
        this.description = "NO DESCRIPTION";
    }

    public Event(String description) {
        this.description = description;
    }


    public abstract void trigger(Player p, GameScreenForm screen);


}