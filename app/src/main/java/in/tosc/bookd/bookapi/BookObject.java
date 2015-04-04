package in.tosc.bookd.bookapi;

/**
 * Created by championswimmer on 4/4/15.
 */
public class BookObject {

    String title;
    String author;
    String publisher;
    String summary;
    String category;
    String rating;
    String image;
    String isbn;

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getRating() {
        return rating;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSummary() {
        return summary;
    }
}
