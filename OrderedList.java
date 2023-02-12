package markup;

import java.util.List;

public class OrderedList extends AbstractText implements LstElements {
    public OrderedList(List<ListItem> items) {
        super(List.copyOf(items));
        htmlOpen = "<ol>";
        htmlClose = "</ol>";
    }
}
