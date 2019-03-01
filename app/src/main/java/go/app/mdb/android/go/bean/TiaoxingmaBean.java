package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-04.
 */

public class TiaoxingmaBean {
    /**
     * person : [{"BarCode":"1234567890123"}]
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
         */

        private String BarCode;

        public String getBarCode() {
            return BarCode;
        }

        public void setBarCode(String BarCode) {
            this.BarCode = BarCode;
        }
    }
}
