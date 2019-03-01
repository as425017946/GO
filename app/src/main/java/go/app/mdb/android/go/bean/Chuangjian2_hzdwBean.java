package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-23.
 */

public class Chuangjian2_hzdwBean {
    /**
     * person : [{"StepCode":1,"Address":"大梁子菜早市","ID":7,"Name":"九龙百货"}]
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
         * StepCode : 1
         * Address : 大梁子菜早市
         * ID : 7
         * Name : 九龙百货
         */

        private int StepCode;
        private String Address;
        private int ID;
        private String Name;

        public int getStepCode() {
            return StepCode;
        }

        public void setStepCode(int StepCode) {
            this.StepCode = StepCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
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
