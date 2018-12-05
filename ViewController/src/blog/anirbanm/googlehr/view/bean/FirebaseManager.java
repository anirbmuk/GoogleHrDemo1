package blog.anirbanm.googlehr.view.bean;

import blog.anirbanm.googlehr.view.ADFUtils;

import blog.anirbanm.googlehr.view.JSFUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class FirebaseManager implements Serializable {
    
    @SuppressWarnings("compatibility:3508941060135145219")
    private static final long serialVersionUID = -1114549707690938377L;

    public FirebaseManager() {
    }

    public void initFirebase() throws FileNotFoundException, IOException {
        FileInputStream serviceAccount = new FileInputStream("C:\\JDeveloper\\122130\\hrstore-test-firebase-adminsdk-v4li2-09770ed145.json");

        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
                                                               .setDatabaseUrl("https://hrstore-test.firebaseio.com/")
                                                               .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options, "hrstore");
        }
        ADFUtils.findOperation("initDepartments").execute();
    }

    public void onDepartmentSelection(final SelectionEvent selectionEvent) {
        final String _SELECTION = "#{bindings.DepartmentsPVO1.collectionModel.makeCurrent}";
        JSFUtils.invokeMethodExpression(_SELECTION, Object.class, SelectionEvent.class, selectionEvent);
        ADFUtils.findOperation("onDepartmentSelection").execute();
    }
}
