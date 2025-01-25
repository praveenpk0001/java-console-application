package Note;

public class Notes implements Cloneable {
    protected int noteCount;  // to store the count of a note
    protected int noteValue;  // to store the value of the note

    protected Notes(int noteCount, int noteValue) {  // Constructor
        this.noteCount = noteCount;
        this.noteValue = noteValue;
    }

    // Getter and Setter methods
    public int getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }

    public int getNoteValue() {
        return noteValue;
    }

    @Override
    public Notes clone() throws CloneNotSupportedException {  // Clone method to create a copy of the object
        return (Notes) super.clone();  // Return a copy of the object
    }
}
