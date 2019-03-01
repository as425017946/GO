package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */

public class Pinpai {
    /**
     * person : [{"Brand":"心心相印"},{"Brand":"烟"}]
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
         * Brand : 心心相印
         */

        private String Brand;

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String Brand) {
            this.Brand = Brand;
        }
    }
}
