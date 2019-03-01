package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/25.
 */

public class Guige {
    /**
     * person : [{"Specification":"1*10"},{"Specification":"1*5"}]
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
         * Specification : 1*10
         */

        private String Specification;

        public String getSpecification() {
            return Specification;
        }

        public void setSpecification(String Specification) {
            this.Specification = Specification;
        }
    }
}
