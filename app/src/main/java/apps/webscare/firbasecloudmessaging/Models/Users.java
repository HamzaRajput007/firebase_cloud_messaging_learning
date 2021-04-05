package apps.webscare.firbasecloudmessaging.Models;

import com.google.firebase.firestore.auth.User;

public class Users extends UserID{
    String name , image;
    public Users(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
