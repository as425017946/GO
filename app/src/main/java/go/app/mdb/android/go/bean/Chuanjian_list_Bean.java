package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-03.
 */

public class Chuanjian_list_Bean {
    /**
     * person : [{"StepPrice":1990,"ID":1,"Name":"心心相印"},{"StepPrice":2900,"ID":4,"Name":"煊赫门"}]
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
         * StepPrice : 1990
         * ID : 1
         * Name : 心心相印
         */

        private String StepPrice;
        private int ID;
        private String Name;

        public String getStepPrice() {
            return StepPrice;
        }

        public void setStepPrice(String StepPrice) {
            this.StepPrice = StepPrice;
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
