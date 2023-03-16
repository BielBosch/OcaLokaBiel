package cat.dam.biel.ocaloka;

import android.widget.TextView;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Box {
    private String title;
    private String description;
    private String imageUrl;
    private boolean filled;

    public Box(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.filled = false;
    }

    // Getter and setter methods for the imageUrl property
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    // Getter and setter methods for the title and description properties
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}