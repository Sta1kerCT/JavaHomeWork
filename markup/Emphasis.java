package markup;

import java.util.List;


public class Emphasis extends AbstractText implements PhElements {
    public Emphasis(List<PhElements> cs) {
        super(List.copyOf(cs));
        md = "*";
        htmlOpen = "<em>";
        htmlClose = "</em>";
    }
}
