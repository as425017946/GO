package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-25.
 */

public class Fenzu {
    /**
     * person : [{"ID":1,"Name":"百货"},{"ID":3,"Name":"厨具"}]
     * state : 1
     */

    private String state;
    private List<PersonBean> person;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PersonBean> getPerson() {
        return person;
    }

    public void setPerson(List<PersonBean> person) {
        this.person = person;
    }

    public static class PersonBean {
        /**
         * ID : 1
         * Name : 百货
         */

        private int ID;
        private String Name;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
