package markup;

import java.util.List;


public class Strikeout extends AbstractText implements PhElements {
    public Strikeout(List<PhElements> cs) {
        super(List.copyOf(cs));
        md = "~";
        htmlOpen = "<s>";
        htmlClose = "</s>";
    }
}
