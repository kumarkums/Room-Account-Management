package firebasePackage;

import com.google.firebase.database.DataSnapshot;

public interface AdminCheck {

    void CheckAdimin(boolean chack, DataSnapshot User);

    void LoginError();
}
