package markup;

import java.util.List;

public class UnorderedList extends AbstractText implements LstElements {
    public UnorderedList(List<ListItem> items) {
        super(List.copyOf(items));
        htmlOpen = "<ul>";
        htmlClose = "</ul>";
    }

}
