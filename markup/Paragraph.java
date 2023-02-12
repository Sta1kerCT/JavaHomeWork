package markup;

import java.util.List;

public class Paragraph extends AbstractText implements LstElements {
    public Paragraph(List<PhElements> elements) {
        super(List.copyOf(elements));
    }
    public void toHtml(StringBuilder sb){
        for (Convertor convertor : convertors) {
            convertor.toHtml(sb);
        }
    }
    public void toMarkdown(StringBuilder sb){
        for (Convertor convertor : convertors) {
            convertor.toMarkdown(sb);
        }
    }
}
