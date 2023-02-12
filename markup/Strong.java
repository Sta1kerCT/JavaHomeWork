package markup;

import java.util.List;


public class Strong extends AbstractText implements PhElements{
    public Strong(List<PhElements> cs) {
        super(List.copyOf(cs));
        md = "__";
        htmlOpen = "<strong>";
        htmlClose = "</strong>";
    }
}
