package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-04.
 *
 */

public class GoodsinfoBean {
    /**
     * person : [{"BarCode":"1234567890123","ID":1,"Name":"心心相印"},{"BarCode":"123","ID":2,"Name":"天下名楼"},{"BarCode":"456","ID":3,"Name":"紫云"},{"BarCode":"678","ID":4,"Name":"煊赫门"}]
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
         * BarCode : 1234567890123
         * ID : 1
         * Name : 心心相印
         */

        private String BarCode;
        private int ID;
        private String Name;

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }

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
