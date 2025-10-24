public abstract class Event {
    protected String description;
    public Event() {
        this.description="fara descriere";  //bug la SudokuBookEvent(singura rezolvare pana acum)
    }
    public Event(String description){this.description = description;}
    public abstract void trigger(Player p);
}
