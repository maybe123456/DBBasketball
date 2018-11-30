package com.miracle.sport.community.bean;

import java.util.List;

/**
 * Created by Michael on 2018/10/30 21:29 (星期二)
 */
public class PostDetailBean {


    /**
     * id : 2
     * title : invoker
     * content : 今天我打了三个小时的球，出了两斤汗水
     * thumb : ["http://8.988lhkj.com/uploads/20181101/2448f8f0edb86f87eff3f5f7ed03a193.jpg","http://8.988lhkj.com/uploads/20181101/593aedf3cea226246bf8a792882e441b.jpg","http://8.988lhkj.com/uploads/20181101/156eb8f93ee0879c14b8737b79d0ae60.jpg"]
     * click_num : 5
     * comment_num : 11
     * circle_id : 14
     * name : 王者争逐
     * click : 1
     * comment : [{"comment_id":2,"content":"hhhh","add_time":"2018-11-08 11:51:46","nickname":"Michael","user_id":12,"img":"http://9.988lhkj.com/app/imgcom.miracle.michael.mdgame/11//P4rDbfIF2V5tWUpff8G0kh0X57VxAfXFSAQ7tVem.jpeg","createid":2,"comment_click_num":2,"click":0,"child":[{"content":"hbuub","add_time":"2018-11-08 11:50:54","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null},{"content":"hhhh","add_time":"2018-11-08 11:51:46","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null}],"child_count":2},{"comment_id":1,"content":"hbuub","add_time":"2018-11-08 11:50:54","nickname":"Michael","user_id":12,"img":"http://9.988lhkj.com/app/imgcom.miracle.michael.mdgame/11//P4rDbfIF2V5tWUpff8G0kh0X57VxAfXFSAQ7tVem.jpeg","createid":2,"comment_click_num":0,"click":0,"child":[],"child_count":0}]
     */

    private int id;
    private String title;
    private String content;
    private int click_num;
    private int comment_num;
    private int circle_id;
    private String name;
    private int click;
    private List<String> thumb;
    private List<CommentBean> comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClick_num() {
        return click_num;
    }

    public void setClick_num(int click_num) {
        this.click_num = click_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * comment_id : 2
         * content : hhhh
         * add_time : 2018-11-08 11:51:46
         * nickname : Michael
         * user_id : 12
         * img : http://9.988lhkj.com/app/imgcom.miracle.michael.mdgame/11//P4rDbfIF2V5tWUpff8G0kh0X57VxAfXFSAQ7tVem.jpeg
         * createid : 2
         * comment_click_num : 2
         * click : 0
         * child : [{"content":"hbuub","add_time":"2018-11-08 11:50:54","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null},{"content":"hhhh","add_time":"2018-11-08 11:51:46","nickname":"Michael","user_id":12,"to_name":null,"to_user_id":null}]
         * child_count : 2
         */

        private int comment_id;
        private String content;
        private String add_time;
        private String nickname;
        private int user_id;
        private String img;
        private int createid;
        private int comment_click_num;
        private int click;
        private int child_count;
        private List<ChildBean> child;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getCreateid() {
            return createid;
        }

        public void setCreateid(int createid) {
            this.createid = createid;
        }

        public int getComment_click_num() {
            return comment_click_num;
        }

        public void setComment_click_num(int comment_click_num) {
            this.comment_click_num = comment_click_num;
        }

        public int getClick() {
            return click;
        }

        public void setClick(int click) {
            this.click = click;
        }

        public int getChild_count() {
            return child_count;
        }

        public void setChild_count(int child_count) {
            this.child_count = child_count;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * content : hbuub
             * add_time : 2018-11-08 11:50:54
             * nickname : Michael
             * user_id : 12
             * to_name : null
             * to_user_id : null
             */

            private String content;
            private String add_time;
            private String nickname;
            private int user_id;
            private Object to_name;
            private Object to_user_id;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public Object getTo_name() {
                return to_name;
            }

            public void setTo_name(Object to_name) {
                this.to_name = to_name;
            }

            public Object getTo_user_id() {
                return to_user_id;
            }

            public void setTo_user_id(Object to_user_id) {
                this.to_user_id = to_user_id;
            }
        }
    }
}
