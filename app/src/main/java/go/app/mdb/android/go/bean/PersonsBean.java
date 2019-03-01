package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-01-15.
 */

public class PersonsBean {
    /**
     * person : [{"UserName":"王","ID":2},{"UserName":"张","ID":3},{"UserName":"刘","ID":4},{"UserName":"邓","ID":5},{"UserName":"周","ID":6}]
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
         * UserName : 王
         * ID : 2
         */

        private String UserName;
        private int ID;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }
    }
}
