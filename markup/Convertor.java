package markup;

public interface Convertor {
    void toHtml(StringBuilder sb);
    void toMarkdown(StringBuilder sb);
}
