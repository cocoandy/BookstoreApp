package com.gxwz.wzxy.bookstoreapp.modle;

import cn.bmob.v3.BmobObject;

/**
 * Created by crucy on 2017/10/28.
 */

public class BookInfo extends BmobObject {

    String bookId;//编号
    String cover;//封面
    String name;//书名
    String author;//作者
    String press;//出版社
    Double score;//评分
    String price;//价格
    BookTypeInfo type;//类型
    Integer comment = 0; //评论数
    Integer status = 0; //状态 0： 上架 1： 下架
    String reume;//作者简介
    String introduction;//作者简介
    String directory;//m目录

    public BookInfo(String bookId, String cover, String name, String author, String press, Double score, String price, BookTypeInfo type, Integer comment, Integer status, String reume, String introduction, String directory) {
        this.bookId = bookId;
        this.cover = cover;
        this.name = name;
        this.author = author;
        this.press = press;
        this.score = score;
        this.price = price;
        this.type = type;
        this.comment = comment;
        this.status = status;
        this.reume = reume;
        this.introduction = introduction;
        this.directory = directory;
    }

    public BookInfo(String cover, String name) {
        this.cover = cover;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookId='" + bookId + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", score=" + score +
                ", price='" + price + '\'' +
                ", type=" + type +
                ", comment=" + comment +
                ", status=" + status +
                ", reume='" + reume + '\'' +
                ", introduction='" + introduction + '\'' +
                ", directory='" + directory + '\'' +
                '}';
    }

    public String getReume() {
        return reume;
    }

    public void setReume(String reume) {
        this.reume = reume;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    //public BookInfo(bookId, cover, name,  author,  press,  score,  price,  type,  comment,  status)
//    BookInfo("bookId",  "cover",  "name",  "author",
//                     "press",  "score",  "price",  "type",  "comment",  "status");
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BookTypeInfo getType() {
        return type;
    }

    public void setType(BookTypeInfo type) {
        this.type = type;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
