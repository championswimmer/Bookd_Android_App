package in.tosc.bookd.bookapi;

/**
 * Created by championswimmer on 4/4/15.
 */
public class BookObject {

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

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
