package com.zhailiw.app.server.response;

import java.util.List;

public class ProductAttributeResponse {

    /**
     * state : 1
     * msg : 成功
     * totalPages : 1
     * data : [{"ProductId":35,"ProductName":"沙发","ProductInfo":"豪华版","ProductNo":"ZL10-F-35","CompanyId":10,"ProductAttributeId":17,"Price":250,"ProductAttributeName":null,"FactoryPrice":100,"GPR":null,"Type":"长约500cm","Color":"蓝间红","Name":null,"CreateDate":"/Date(1524648217233)/","PriceNow":900,"PhotoSmall":"/Images/ZL_Product/2018/05/18/2018051811534849_s.png","ProductStyleId":235,"MatchTypeId":244,"ProductTypeId":248,"MaterialTypeId":261,"FactoryProductNo":null,"ProductTypeName":"沙发","MatchTypeName":"卧室","ProductStyleName":"欧美风格","MaterialTypeName":"板材类"},{"ProductId":35,"ProductName":"沙发","ProductInfo":"豪华版","ProductNo":"ZL10-F-35","CompanyId":10,"ProductAttributeId":16,"Price":250,"ProductAttributeName":null,"FactoryPrice":100,"GPR":null,"Type":"三件套","Color":"红色","Name":null,"CreateDate":"/Date(1523846502887)/","PriceNow":143,"PhotoSmall":"/Images/ZL_Product/2018/05/18/2018051811534849_s.png","ProductStyleId":235,"MatchTypeId":244,"ProductTypeId":248,"MaterialTypeId":261,"FactoryProductNo":null,"ProductTypeName":"沙发","MatchTypeName":"卧室","ProductStyleName":"欧美风格","MaterialTypeName":"板材类"}]
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
         * ProductId : 35
         * ProductName : 沙发
         * ProductInfo : 豪华版
         * ProductNo : ZL10-F-35
         * CompanyId : 10
         * ProductAttributeId : 17
         * Price : 250
         * ProductAttributeName : null
         * FactoryPrice : 100
         * GPR : null
         * Type : 长约500cm
         * Color : 蓝间红
         * Name : null
         * CreateDate : /Date(1524648217233)/
         * PriceNow : 900
         * PhotoSmall : /Images/ZL_Product/2018/05/18/2018051811534849_s.png
         * ProductStyleId : 235
         * MatchTypeId : 244
         * ProductTypeId : 248
         * MaterialTypeId : 261
         * FactoryProductNo : null
         * ProductTypeName : 沙发
         * MatchTypeName : 卧室
         * ProductStyleName : 欧美风格
         * MaterialTypeName : 板材类
         */

        private int ProductId;
        private String ProductName;
        private String ProductInfo;
        private String ProductNo;
        private int CompanyId;
        private int ProductAttributeId;
        private int Price;
        private Object ProductAttributeName;
        private int FactoryPrice;
        private Object GPR;
        private String Type;
        private String Color;
        private Object Name;
        private String CreateDate;
        private int PriceNow;
        private String PhotoSmall;
        private int ProductStyleId;
        private int MatchTypeId;
        private int ProductTypeId;
        private int MaterialTypeId;
        private Object FactoryProductNo;
        private String ProductTypeName;
        private String MatchTypeName;
        private String ProductStyleName;
        private String MaterialTypeName;

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int ProductId) {
            this.ProductId = ProductId;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getProductInfo() {
            return ProductInfo;
        }

        public void setProductInfo(String ProductInfo) {
            this.ProductInfo = ProductInfo;
        }

        public String getProductNo() {
            return ProductNo;
        }

        public void setProductNo(String ProductNo) {
            this.ProductNo = ProductNo;
        }

        public int getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(int CompanyId) {
            this.CompanyId = CompanyId;
        }

        public int getProductAttributeId() {
            return ProductAttributeId;
        }

        public void setProductAttributeId(int ProductAttributeId) {
            this.ProductAttributeId = ProductAttributeId;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }

        public Object getProductAttributeName() {
            return ProductAttributeName;
        }

        public void setProductAttributeName(Object ProductAttributeName) {
            this.ProductAttributeName = ProductAttributeName;
        }

        public int getFactoryPrice() {
            return FactoryPrice;
        }

        public void setFactoryPrice(int FactoryPrice) {
            this.FactoryPrice = FactoryPrice;
        }

        public Object getGPR() {
            return GPR;
        }

        public void setGPR(Object GPR) {
            this.GPR = GPR;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getColor() {
            return Color;
        }

        public void setColor(String Color) {
            this.Color = Color;
        }

        public Object getName() {
            return Name;
        }

        public void setName(Object Name) {
            this.Name = Name;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public int getPriceNow() {
            return PriceNow;
        }

        public void setPriceNow(int PriceNow) {
            this.PriceNow = PriceNow;
        }

        public String getPhotoSmall() {
            return PhotoSmall;
        }

        public void setPhotoSmall(String PhotoSmall) {
            this.PhotoSmall = PhotoSmall;
        }

        public int getProductStyleId() {
            return ProductStyleId;
        }

        public void setProductStyleId(int ProductStyleId) {
            this.ProductStyleId = ProductStyleId;
        }

        public int getMatchTypeId() {
            return MatchTypeId;
        }

        public void setMatchTypeId(int MatchTypeId) {
            this.MatchTypeId = MatchTypeId;
        }

        public int getProductTypeId() {
            return ProductTypeId;
        }

        public void setProductTypeId(int ProductTypeId) {
            this.ProductTypeId = ProductTypeId;
        }

        public int getMaterialTypeId() {
            return MaterialTypeId;
        }

        public void setMaterialTypeId(int MaterialTypeId) {
            this.MaterialTypeId = MaterialTypeId;
        }

        public Object getFactoryProductNo() {
            return FactoryProductNo;
        }

        public void setFactoryProductNo(Object FactoryProductNo) {
            this.FactoryProductNo = FactoryProductNo;
        }

        public String getProductTypeName() {
            return ProductTypeName;
        }

        public void setProductTypeName(String ProductTypeName) {
            this.ProductTypeName = ProductTypeName;
        }

        public String getMatchTypeName() {
            return MatchTypeName;
        }

        public void setMatchTypeName(String MatchTypeName) {
            this.MatchTypeName = MatchTypeName;
        }

        public String getProductStyleName() {
            return ProductStyleName;
        }

        public void setProductStyleName(String ProductStyleName) {
            this.ProductStyleName = ProductStyleName;
        }

        public String getMaterialTypeName() {
            return MaterialTypeName;
        }

        public void setMaterialTypeName(String MaterialTypeName) {
            this.MaterialTypeName = MaterialTypeName;
        }
    }
}
