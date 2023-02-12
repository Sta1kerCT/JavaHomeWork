package markup;

import java.util.List;

public abstract class AbstractText implements Convertor {
    protected List<Convertor> convertors;
    protected String md;
    protected String htmlOpen;
    protected String htmlClose;

    public AbstractText(List<Convertor> items) {
        convertors = items;
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(md);
        for (Convertor convertor : convertors) {
            convertor.toHtml(sb);
        }
        sb.append(md);
    }

    public void toHtml(StringBuilder sb) {
        sb.append(htmlOpen);
        for (Convertor convertor : convertors) {
            convertor.toHtml(sb);
        }
        sb.append(htmlClose);
    }
}
