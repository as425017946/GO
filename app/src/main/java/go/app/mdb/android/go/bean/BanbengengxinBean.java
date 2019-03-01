package go.app.mdb.android.go.bean;

import java.util.List;

public class BanbengengxinBean {

    /**
     * person : [{"ID":2,"version":"2.0","url":"chuanqing.s29.csome.cn:20200/MyWeb/kanmengo.apk"}]
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
         * ID : 2
         * version : 2.0
         * url : chuanqing.s29.csome.cn:20200/MyWeb/kanmengo.apk
         */

        private int ID;
        private String version;
        private String url;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
