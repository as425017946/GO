package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-20.
 */

public class GaijiActivityBean {

    /**
     * person : [{"StepCode":"一级客户","ID":2,"Name":"传清"}]
     * state : 1
     */
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
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
         * StepCode : 一级客户
         * ID : 2
         * Name : 传清
         */

        private String StepCode;
        private int ID;
        private String Name;

        public String getStepCode() {
            return StepCode;
        }

        public void setStepCode(String StepCode) {
            this.StepCode = StepCode;
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
