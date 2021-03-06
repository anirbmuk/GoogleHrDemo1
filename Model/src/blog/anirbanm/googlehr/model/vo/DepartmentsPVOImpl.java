package blog.anirbanm.googlehr.model.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import oracle.jbo.Key;
import oracle.jbo.RangePagingDataFilter;
import oracle.jbo.ScrollableDataFilter;
import oracle.jbo.server.ProgrammaticViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Dec 04 22:27:31 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DepartmentsPVOImpl extends ProgrammaticViewObjectImpl {
    
    private HashMap<String, Object> departments;
    
    /**
     * This is the default constructor (do not remove).
     */
    public DepartmentsPVOImpl() {
    }

    /**
     * getRangePagingData - for custom java data source support.
     */
    public Collection<Object> getRangePagingData(RangePagingDataFilter filter) {
        Collection<Object> value = super.getRangePagingData(filter);
        return value;
    }

    /**
     * retrieveDataByKey - for custom java data source support.
     */
    public Collection<Object> retrieveDataByKey(Key key, int size) {
        Collection<Object> value = super.retrieveDataByKey(key, size);
        return value;
    }

    /**
     * getScrollableData - for custom java data source support.
     */
    public Collection<Object> getScrollableData(ScrollableDataFilter filter) {
        ArrayList<Object> rows = new ArrayList<Object>();
        final TreeMap<String, Object> departments = new TreeMap<String, Object>(getDepartments());
        if (departments != null) {
            for (Map.Entry department : departments.entrySet()) {
                rows.add(department.getValue());
            }
        }
        return rows;
    }

    public void setDepartments(HashMap<String, Object> departments) {
        this.departments = departments;
    }

    public HashMap<String, Object> getDepartments() {
        return departments;
    }
}

