package markup;

import java.util.List;

public class ListItem extends AbstractText {
    public ListItem(List<LstElements> items) {
        super(List.copyOf(items));
        htmlOpen = "<li>";
        htmlClose = "</li>";
    }
}
