package apps.webscare.firbasecloudmessaging.Models;

import androidx.annotation.NonNull;

public class UserID {
    public String userId;
    public <T extends UserID> T withId(@NonNull final String id){
        this.userId = id;
        return (T) this;
    }
}
