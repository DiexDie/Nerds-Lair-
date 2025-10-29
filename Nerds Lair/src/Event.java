public abstract class Event {
    protected String description;
    public Event() {
        this.description="NO DESCRIPTION";  //bug with SudokuBookEvent
    }
    public Event(String description){this.description = description;}
    public abstract void trigger(Player p);
}
