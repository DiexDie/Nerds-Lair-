import java.util.Set;
import java.util.Collections;
import java.io.Serializable;

public abstract class Item implements Serializable {
    protected String name;
    protected final Set<PlayerClass> compatibleClasses;

    public Item(String name, Set<PlayerClass> compatibleClasses) {
        this.name = name;
        this.compatibleClasses = Collections.unmodifiableSet(compatibleClasses);
    }


    public String getName() {
        return name;
    }


    public Set<PlayerClass> getCompatibleClasses() {
        return compatibleClasses;
    }

    public abstract void use(Player p);
}