package com.miracle.sport.community.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michael on 2018/10/30 16:08 (星期二)
 */
public class CircleBean {


    /**
     * id : 1
     * name : NBA社区
     * pid : 0
     * child : [{"id":5,"name":"火箭","pid":1,"follow":0},{"id":6,"name":"湖人","pid":1,"follow":1},{"id":7,"name":"勇士","pid":1,"follow":1},{"id":8,"name":"马刺","pid":1,"follow":1}]
     */

    private int id;
    private String name;
    private int pid;
    private List<ChildBean> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean  implements Serializable {
        /**
         * id : 5
         * name : 火箭
         * pid : 1
         * follow : 0
         */

        private int id;
        private String name;
        private int pid;
        private int follow;
        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }
    }
}
