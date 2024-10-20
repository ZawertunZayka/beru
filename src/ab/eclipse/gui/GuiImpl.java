package ab.eclipse.gui;

public interface GuiImpl {
    void render(int mx, int my);

    void mouse(double mx, double my, int button, int action);
    void key(int key, int action);

    void close();

    static boolean isHover(double x, double y, double w, double h, double mx, double my) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }
}
