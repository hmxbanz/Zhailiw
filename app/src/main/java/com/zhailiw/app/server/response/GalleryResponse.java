package com.zhailiw.app.server.response;

import java.util.List;

public class GalleryResponse {

    /**
     * state : 1
     * msg : 成功
     * totalPages : 1
     * data : [{"PhotoSmall":"/Images/ZL_Gallery/2018/04/19/2018041917372113_s.jpg","PhotoBig":"/Images/ZL_Gallery/2018/04/19/2018041917372113_b.jpg","PhotoInfo":null,"PhotoType":276,"GalleryID":6,"GalleryName":"00022","CreateDate":"/Date(1524125341347)/"},{"PhotoSmall":"/Images/ZL_Gallery/2018/04/19/2018041917372113_s.jpg","PhotoBig":"/Images/ZL_Gallery/2018/04/19/2018041917372113_b.jpg","PhotoInfo":null,"PhotoType":276,"GalleryID":6,"GalleryName":"00022","CreateDate":"/Date(1524125341347)/"},{"PhotoSmall":"/Images/ZL_Gallery/2018/04/19/2018041917372113_s.jpg","PhotoBig":"/Images/ZL_Gallery/2018/04/19/2018041917372113_b.jpg","PhotoInfo":null,"PhotoType":276,"GalleryID":6,"GalleryName":"00022","CreateDate":"/Date(1524125341347)/"},{"PhotoSmall":"/Images/ZL_Gallery/2018/04/19/2018041917372113_s.jpg","PhotoBig":"/Images/ZL_Gallery/2018/04/19/2018041917372113_b.jpg","PhotoInfo":null,"PhotoType":276,"GalleryID":6,"GalleryName":"00022","CreateDate":"/Date(1524125341347)/"},{"PhotoSmall":null,"PhotoBig":null,"PhotoInfo":null,"PhotoType":null,"GalleryID":2,"GalleryName":"dd","CreateDate":"/Date(1524108440337)/"},{"PhotoSmall":null,"PhotoBig":null,"PhotoInfo":null,"PhotoType":null,"GalleryID":1,"GalleryName":"图库一","CreateDate":"/Date(1524108393420)/"}]
     */

    private int state;
    private String msg;
    private int totalPages;
    private List<DataBean> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * PhotoSmall : /Images/ZL_Gallery/2018/04/19/2018041917372113_s.jpg
         * PhotoBig : /Images/ZL_Gallery/2018/04/19/2018041917372113_b.jpg
         * PhotoInfo : null
         * PhotoType : 276
         * GalleryID : 6
         * GalleryName : 00022
         * CreateDate : /Date(1524125341347)/
         */

        private String PhotoSmall;
        private String PhotoBig;
        private Object PhotoInfo;
        private int PhotoType;
        private int GalleryID;
        private String GalleryName;
        private String CreateDate;

        public String getPhotoSmall() {
            return PhotoSmall;
        }

        public void setPhotoSmall(String PhotoSmall) {
            this.PhotoSmall = PhotoSmall;
        }

        public String getPhotoBig() {
            return PhotoBig;
        }

        public void setPhotoBig(String PhotoBig) {
            this.PhotoBig = PhotoBig;
        }

        public Object getPhotoInfo() {
            return PhotoInfo;
        }

        public void setPhotoInfo(Object PhotoInfo) {
            this.PhotoInfo = PhotoInfo;
        }

        public int getPhotoType() {
            return PhotoType;
        }

        public void setPhotoType(int PhotoType) {
            this.PhotoType = PhotoType;
        }

        public int getGalleryID() {
            return GalleryID;
        }

        public void setGalleryID(int GalleryID) {
            this.GalleryID = GalleryID;
        }

        public String getGalleryName() {
            return GalleryName;
        }

        public void setGalleryName(String GalleryName) {
            this.GalleryName = GalleryName;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }
    }
}
