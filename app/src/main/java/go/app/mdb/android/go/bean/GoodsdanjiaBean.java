package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-04.
 */

public class GoodsdanjiaBean {

    /**
     * person : [{"StepPrice5":2.6,"StepPrice2":2.9,"StepPrice1":3,"StepPrice4":2.7,"ID":1,"StepPrice3":2.8,"InventoryID":5},{"StepPrice5":3,"StepPrice2":3,"StepPrice1":3,"StepPrice4":3,"ID":2,"StepPrice3":3,"InventoryID":6},{"StepPrice5":0,"StepPrice2":0,"StepPrice1":0,"StepPrice4":0,"ID":3,"StepPrice3":0,"InventoryID":7},{"StepPrice5":0,"StepPrice2":0,"StepPrice1":0,"StepPrice4":0,"ID":4,"StepPrice3":0,"InventoryID":8}]
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
         * StepPrice5 : 2.6
         * StepPrice2 : 2.9
         * StepPrice1 : 3.0
         * StepPrice4 : 2.7
         * ID : 1
         * StepPrice3 : 2.8
         * InventoryID : 5
         */

        private double StepPrice5;
        private double StepPrice2;
        private double StepPrice1;
        private double StepPrice4;
        private int ID;
        private double StepPrice3;
        private int InventoryID;

        public double getStepPrice5() {
            return StepPrice5;
        }

        public void setStepPrice5(double StepPrice5) {
            this.StepPrice5 = StepPrice5;
        }

        public double getStepPrice2() {
            return StepPrice2;
        }

        public void setStepPrice2(double StepPrice2) {
            this.StepPrice2 = StepPrice2;
        }

        public double getStepPrice1() {
            return StepPrice1;
        }

        public void setStepPrice1(double StepPrice1) {
            this.StepPrice1 = StepPrice1;
        }

        public double getStepPrice4() {
            return StepPrice4;
        }

        public void setStepPrice4(double StepPrice4) {
            this.StepPrice4 = StepPrice4;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public double getStepPrice3() {
            return StepPrice3;
        }

        public void setStepPrice3(double StepPrice3) {
            this.StepPrice3 = StepPrice3;
        }

        public int getInventoryID() {
            return InventoryID;
        }

        public void setInventoryID(int InventoryID) {
            this.InventoryID = InventoryID;
        }
    }
}
