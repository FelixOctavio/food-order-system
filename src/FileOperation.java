import java.util.List;

public abstract class FileOperation {
    public abstract List<String[]> read();
    public abstract void write(List<String[]> file, int mode);
}