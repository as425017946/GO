package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-28.
 */

public class ChuanjianBean {
    /**
     * person : [{"lastId":9}]
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
         * lastId : 9
         */

        private int lastId;

        public int getLastId() {
            return lastId;
        }

        public void setLastId(int lastId) {
            this.lastId = lastId;
        }
    }
}
