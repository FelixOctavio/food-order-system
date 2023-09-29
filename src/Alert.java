import javax.swing.*;

public class Alert<A> {
    A obj;
    Alert(A obj) { this.obj = obj; } // constructor
    public <A> void getObject() {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                obj);
    }
}
