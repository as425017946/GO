package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class Hezuodanwei {
    /**
     * person : [{"ID":2,"Name":"传清"},{"ID":3,"Name":"传至"},{"ID":4,"Name":"传科"}]
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
         * ID : 2
         * Name : 传清
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
