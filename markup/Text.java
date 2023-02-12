package markup;

public class Text implements PhElements {
    private final String text;

    public Text(String str) {
        text = str;
    }

    public void toHtml(StringBuilder sb) {
        sb.append(text);
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }
}
