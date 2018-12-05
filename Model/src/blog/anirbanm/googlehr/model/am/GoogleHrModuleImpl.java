package blog.anirbanm.googlehr.model.am;

import blog.anirbanm.googlehr.model.am.common.GoogleHrModule;
import blog.anirbanm.googlehr.model.vo.DepartmentsPVOImpl;
import blog.anirbanm.googlehr.model.vo.DepartmentsPVORowImpl;
import blog.anirbanm.googlehr.model.vo.EmployeesPVOImpl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;

import oracle.jbo.server.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Nov 28 10:51:03 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GoogleHrModuleImpl extends ApplicationModuleImpl implements GoogleHrModule {
    /**
     * This is the default constructor (do not remove).
     */
    public GoogleHrModuleImpl() {
    }

    public void initDepartments() {
        final DepartmentsPVOImpl departmentsView = getDepartmentsPVO1();
        try {
            departmentsView.setDepartments(getAllDepartments());
        } catch (IOException e) {
            departmentsView.setDepartments(null);
        }
        departmentsView.executeQuery();
        
        DepartmentsPVORowImpl department = (DepartmentsPVORowImpl) departmentsView.first();
        try {
            if (department != null) {
                refreshEmployees(department.getDepartmentId());
            } else {
                refreshEmployees(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void onDepartmentSelection() {
        final DepartmentsPVOImpl departmentsView = getDepartmentsPVO1();
        final DepartmentsPVORowImpl department = (DepartmentsPVORowImpl) departmentsView.getCurrentRow();
        try {
            if (department != null) {
                refreshEmployees(department.getDepartmentId());
            } else {
                refreshEmployees(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void refreshEmployees(final Integer departmentId) throws IOException {
        final EmployeesPVOImpl employeesView = getEmployeesPVO1();
        employeesView.setEmployees(departmentId == null ? null : getEmployeesByDepartment(departmentId));
        employeesView.executeQuery();
    }

    private HashMap<String, Object> getAllDepartments() throws IOException {
        return getData(getAllDepartmentsUri());
    }
    
    private HashMap<String, Object> getEmployeesByDepartment(Integer departmentId) throws IOException {
        return getData(filterEmployeesByDepartmentUri(departmentId));
    }
    
    private HashMap<String, Object> getData(final String uri) throws IOException, JsonParseException, JsonMappingException {
        HashMap<String, Object> hashmap = null;
        Client client = Client.create();
        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String value = response.getEntity(String.class);
        final ObjectMapper mapper = new ObjectMapper();
        hashmap = mapper.readValue(value, HashMap.class);
        return hashmap;
    }

    public String getFirebaseName() {
        return getFirebaseApp("hrstore").getName();
    }

    private FirebaseApp getFirebaseApp(final String ref) {
        return FirebaseApp.getInstance(ref);
    }

    private DatabaseReference getFirebaseDatabase(final String ref) {
        return FirebaseDatabase.getInstance(getFirebaseApp(ref)).getReference();
    }

    private String getFirebaseRootReference(final String ref) {
        return getFirebaseDatabase(ref).toString();
    }

    private String getAllDepartmentsUri() {
        return getFirebaseDatabase("hrstore").child("Departments") + ".json";
    }

    private String filterEmployeesByDepartmentUri(Integer departmentId) throws UnsupportedEncodingException {
        return getFirebaseDatabase("hrstore").child("Employees")
               + ".json?"
               + URLEncoder.encode("orderBy=\"DepartmentId\"&equalTo=" + departmentId, "UTF-8");
    }

    /**
     * Container's getter for DepartmentsPVO1.
     * @return DepartmentsPVO1
     */
    public DepartmentsPVOImpl getDepartmentsPVO1() {
        return (DepartmentsPVOImpl) findViewObject("DepartmentsPVO1");
    }

    /**
     * Container's getter for EmployeesPVO1.
     * @return EmployeesPVO1
     */
    public EmployeesPVOImpl getEmployeesPVO1() {
        return (EmployeesPVOImpl) findViewObject("EmployeesPVO1");
    }
}

