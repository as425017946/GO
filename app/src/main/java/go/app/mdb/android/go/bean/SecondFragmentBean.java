package go.app.mdb.android.go.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-12-20.
 */

public class SecondFragmentBean {

    /**
     * person : [{"Status":1,"CreatTime":"2017-12-14 15:13:45","IsUpdate":0,"UpdateTime":"2017-12-14 15:13:45","SalesmanName":"asd","ID":2,"BillCode":"2121212121","ParterID":"传清"},{"Status":1,"CreatTime":"2017-12-15 15:22:17","IsUpdate":0,"UpdateTime":"2017-12-15 15:22:17","SalesmanName":"张","ID":7,"BillCode":"555"},{"Status":1,"CreatTime":"2017-12-15 15:49:12","IsUpdate":0,"UpdateTime":"2017-12-15 15:49:12","SalesmanName":"张","ID":8,"BillCode":"17"},{"Status":0,"CreatTime":"2017-12-15 15:50:47","IsUpdate":1,"UpdateTime":"2017-12-28 10:42:30","SalesmanName":"张","ID":9,"BillCode":"18"},{"Status":1,"CreatTime":"2017-12-28 15:40:46","IsUpdate":1,"UpdateTime":"2017-12-28 15:40:46","SalesmanName":"li","ID":15,"BillCode":"123123321321"},{"Status":1,"CreatTime":"2017-12-28 15:40:49","IsUpdate":1,"UpdateTime":"2017-12-28 15:40:49","SalesmanName":"li","ID":16,"BillCode":"123123321321"},{"Status":1,"CreatTime":"2017-12-28 15:42:05","IsUpdate":1,"UpdateTime":"2017-12-28 15:42:05","SalesmanName":"li","ID":17,"BillCode":"123123321321"},{"Status":1,"CreatTime":"2017-12-29 10:18:21","UpdateTime":"2017-12-29 10:18:21","ID":28},{"Status":1,"CreatTime":"2017-12-29 10:19:23","UpdateTime":"2017-12-29 10:19:23","ID":29},{"Status":1,"CreatTime":"2017-12-29 10:19:33","UpdateTime":"2017-12-29 10:19:33","ID":30},{"Status":1,"CreatTime":"2017-12-29 10:22:27","UpdateTime":"2017-12-29 10:22:27","ID":31},{"Status":1,"CreatTime":"2017-12-29 10:29:32","UpdateTime":"2017-12-29 10:29:32","ID":32},{"Status":1,"CreatTime":"2017-12-29 10:29:45","UpdateTime":"2017-12-29 10:29:45","ID":33},{"Status":1,"CreatTime":"2017-12-29 10:30:14","UpdateTime":"2017-12-29 10:30:14","ID":34},{"Status":1,"CreatTime":"2017-12-29 11:28:26","UpdateTime":"2017-12-29 11:28:26","ID":37},{"Status":0,"CreatTime":"2017-12-29 14:29:01","IsUpdate":1,"UpdateTime":"2017-12-29 18:47:52","SalesmanName":"王","ID":53,"BillCode":"8472025170","ParterID":"传清"},{"Status":1,"CreatTime":"2017-12-29 16:08:24","IsUpdate":1,"UpdateTime":"2017-12-29 16:08:24","SalesmanName":"王","ID":54,"BillCode":"3220009457","ParterID":"传清"}]
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
         * Status : 1
         * CreatTime : 2017-12-14 15:13:45
         * IsUpdate : 0
         * UpdateTime : 2017-12-14 15:13:45
         * SalesmanName : asd
         * ID : 2
         * BillCode : 2121212121
         * ParterID : 传清
         */

        private int Status;
        private String CreatTime;
        private int IsUpdate;
        private String UpdateTime;
        private String SalesmanName;
        private int ID;
        private String BillCode;
        private String ParterID;

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreatTime() {
            return CreatTime;
        }

        public void setCreatTime(String CreatTime) {
            this.CreatTime = CreatTime;
        }

        public int getIsUpdate() {
            return IsUpdate;
        }

        public void setIsUpdate(int IsUpdate) {
            this.IsUpdate = IsUpdate;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getSalesmanName() {
            return SalesmanName;
        }

        public void setSalesmanName(String SalesmanName) {
            this.SalesmanName = SalesmanName;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getBillCode() {
            return BillCode;
        }

        public void setBillCode(String BillCode) {
            this.BillCode = BillCode;
        }

        public String getParterID() {
            return ParterID;
        }

        public void setParterID(String ParterID) {
            this.ParterID = ParterID;
        }
    }
}
